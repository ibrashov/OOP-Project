package university.system;

import university.enums.TeacherTitle;
import university.exceptions.CreditLimitExceededException;
import university.exceptions.FailLimitExceededException;
import university.model.academic.Course;
import university.model.academic.Enrollment;
import university.model.academic.Mark;
import university.model.research.ResearchPaper;
import university.model.research.Researcher;
import university.model.support.ActionLog;
import university.model.support.News;
import university.model.users.Manager;
import university.model.users.Student;
import university.model.users.Teacher;
import university.model.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UniversitySystem implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final UniversitySystem INSTANCE = new UniversitySystem();

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<Enrollment> enrollments = new ArrayList<>();
    private List<Researcher> researchers = new ArrayList<>();
    private List<News> news = new ArrayList<>();
    private List<ActionLog> actionLogs = new ArrayList<>();

    private UniversitySystem() {
    }

    public static UniversitySystem getInstance() {
        return INSTANCE;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public List<Researcher> getResearchers() {
        return researchers;
    }

    public List<News> getNews() {
        return news;
    }

    public List<ActionLog> getActionLogs() {
        return actionLogs;
    }

    public void clear() {
        users.clear();
        courses.clear();
        enrollments.clear();
        researchers.clear();
        news.clear();
        actionLogs.clear();
    }

    public void addUser(User user) {
        if (user == null || users.contains(user)) {
            return;
        }
        users.add(user);
        log(user.getId(), "User added: " + user.getFullname());
        ensureProfessorResearcher(user);
    }

    public void removeUser(int userId) {
        User user = findUserById(userId);
        if (user != null) {
            user.deleteAccount();
            log(userId, "User deactivated");
        }
    }

    public void updateUser(User updatedUser) {
        if (updatedUser == null) {
            return;
        }
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == updatedUser.getId()) {
                users.set(i, updatedUser);
                log(updatedUser.getId(), "User updated: " + updatedUser.getFullname());
                ensureProfessorResearcher(updatedUser);
                return;
            }
        }
        addUser(updatedUser);
    }

    public User findUserById(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
            log(0, "Course added: " + course.getCourseCode());
        }
    }

    public void assignTeacherToCourse(Manager manager, Course course, Teacher teacher) {
        if (course == null || teacher == null) {
            throw new IllegalArgumentException("Course and teacher are required");
        }
        course.addInstructor(teacher);
        log(manager == null ? 0 : manager.getId(), "Teacher " + teacher.getFullname() + " assigned to " + course.getTitle());
    }

    public Enrollment registerStudentForCourse(Student student, Course course)
            throws CreditLimitExceededException, FailLimitExceededException {
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student and course are required");
        }
        student.registerForCourse(course);
        Enrollment enrollment = student.getEnrollments().get(student.getEnrollments().size() - 1);
        enrollments.add(enrollment);
        log(student.getId(), "Registered for course: " + course.getCourseCode());
        return enrollment;
    }

    public void approveEnrollment(Manager manager, Enrollment enrollment) {
        if (enrollment == null) {
            throw new IllegalArgumentException("Enrollment is required");
        }
        enrollment.approve();
        log(manager == null ? 0 : manager.getId(), "Enrollment approved: " + enrollment.getEnrollmentId());
    }

    public void putMark(Teacher teacher, Enrollment enrollment, Mark mark) {
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher is required");
        }
        teacher.gradeStudent(enrollment, mark);
        log(teacher.getId(), "Mark placed for enrollment: " + enrollment.getEnrollmentId());
    }

    public void addResearcher(Researcher researcher) {
        if (researcher != null && !researchers.contains(researcher)) {
            researchers.add(researcher);
            log(researcher.getUser() == null ? 0 : researcher.getUser().getId(), "Researcher added");
        }
    }

    public void printAllResearchPapers(Comparator<ResearchPaper> comparator) {
        Researcher.printAllPapers(researchers, comparator);
    }

    public Researcher getTopCitedResearcherBySchool(String school) {
        return Researcher.getTopCitedResearcherBySchool(researchers, school);
    }

    public Researcher getTopCitedResearcherByYear(int year) {
        return Researcher.getTopCitedResearcherByYear(researchers, year);
    }

    public void addNews(News newsItem) {
        if (newsItem != null && !news.contains(newsItem)) {
            news.add(newsItem);
            log(0, "News added: " + newsItem.getTitle());
        }
    }

    public List<Student> getStudentsSortedByGpa() {
        List<Student> students = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Student) {
                students.add((Student) user);
            }
        }
        students.sort(Comparator.comparingDouble(Student::getGpa).reversed());
        return students;
    }

    public List<Teacher> getTeachersSortedByName() {
        List<Teacher> teachers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Teacher) {
                teachers.add((Teacher) user);
            }
        }
        teachers.sort(Comparator.comparing(User::getFullname, String.CASE_INSENSITIVE_ORDER));
        return teachers;
    }

    public String generateAcademicReport() {
        int approved = 0;
        int failed = 0;
        double totalMarks = 0;
        int marked = 0;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStatus() == university.enums.RegistrationStatus.APPROVED) {
                approved++;
            }
            if (enrollment.getMark() != null) {
                marked++;
                totalMarks += enrollment.getMark().getTotal();
                if (!enrollment.getMark().isPassed()) {
                    failed++;
                }
            }
        }

        double average = marked == 0 ? 0.0 : totalMarks / marked;
        return "Academic report: approved=" + approved
                + ", marked=" + marked
                + ", failed=" + failed
                + ", average=" + String.format("%.2f", average);
    }

    public void printUsers() {
        for (User user : users) {
            System.out.println(user);
        }
    }

    public void printCourses() {
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private void ensureProfessorResearcher(User user) {
        if (!(user instanceof Teacher)) {
            return;
        }
        Teacher teacher = (Teacher) user;
        if (teacher.getTitle() != TeacherTitle.PROFESSOR) {
            return;
        }
        for (Researcher researcher : researchers) {
            if (researcher.getUser() != null && researcher.getUser().equals(user)) {
                return;
            }
        }
        Researcher professorResearcher = new Researcher(1000 + user.getId(), user, "Unknown school", "General research");
        professorResearcher.setHIndex(3);
        researchers.add(professorResearcher);
        log(user.getId(), "Professor automatically registered as researcher");
    }

    private void log(int userId, String action) {
        actionLogs.add(new ActionLog(actionLogs.size() + 1, userId, action));
    }
}
