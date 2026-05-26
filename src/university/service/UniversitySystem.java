package university.service;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import university.enums.DegreeType;
import university.enums.LessonType;
import university.enums.ManagerType;
import university.enums.RegistrationStatus;
import university.enums.TeacherTitle;
import university.exceptions.CreditLimitExceededException;
import university.exceptions.FailLimitExceededException;
import university.exceptions.NonResearcherJoinException;
import university.model.academic.Course;
import university.model.academic.Enrollment;
import university.model.academic.Lesson;
import university.model.academic.Mark;
import university.model.research.ResearchPaper;
import university.model.research.ResearchProject;
import university.model.research.Researcher;
import university.model.support.ActionLog;
import university.model.support.News;
import university.model.support.Request;
import university.model.users.Admin;
import university.model.users.Manager;
import university.model.users.Student;
import university.model.users.Teacher;
import university.model.users.User;

public class UniversitySystem implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DB_FILE = "university_system.ser";
    private static transient UniversitySystem instance;

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<Enrollment> enrollments = new ArrayList<>();
    private List<Researcher> researchers = new ArrayList<>();
    private List<ResearchProject> researchProjects = new ArrayList<>();
    private List<News> news = new ArrayList<>();
    private List<ActionLog> actionLogs = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();
    private List<String> clubs = new ArrayList<>();

    private int nextSequenceNumber = 1;
    private int nextResearcherId = 1;
    private int nextProjectId = 1;
    private int nextPaperId = 1;
    private int nextNewsId = 1;
    private int nextLogId = 1;

    private transient DataStorage storage = new DataStorage();

    private UniversitySystem() {
    }

    public static UniversitySystem getInstance() {
        if (instance == null) {
            DataStorage storage = new DataStorage();
            Object loaded = new File(DB_FILE).exists() ? storage.load(DB_FILE) : null;
            if (loaded instanceof UniversitySystem) {
                instance = (UniversitySystem) loaded;
                instance.storage = storage;
                instance.ensureCollections();
                instance.ensureProfessorResearchers();
            } else {
                instance = new UniversitySystem();
                instance.seedDefaultData();
                instance.save();
            }
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    private void ensureCollections() {
        if (users == null) users = new ArrayList<>();
        if (courses == null) courses = new ArrayList<>();
        if (enrollments == null) enrollments = new ArrayList<>();
        if (researchers == null) researchers = new ArrayList<>();
        if (researchProjects == null) researchProjects = new ArrayList<>();
        if (news == null) news = new ArrayList<>();
        if (actionLogs == null) actionLogs = new ArrayList<>();
        if (requests == null) requests = new ArrayList<>();
        if (clubs == null || clubs.isEmpty()) {
            clubs = new ArrayList<>(List.of("Dev Club", "KBTU Kings", "Art House"));
        }
    }

    private void seedDefaultData() {
        ensureCollections();

        Admin admin = new Admin("26A000001", "Admin One", "admin1@kbtu.kz", "admin123", true, 400000.0, "ADM-001");
        Manager manager = new Manager("26M000001", "Manager One", "manager1@kbtu.kz", "manager123", true, 300000.0, "MGR-001", ManagerType.OR);
        Teacher professor = new Teacher("26T000001", "Ada Lovelace", "ada@kbtu.kz", "teach123", true, "TCH-001", TeacherTitle.PROFESSOR, 500000.0, "EMP-001");
        Teacher lecturer = new Teacher("26T000002", "Alan Turing", "alan@kbtu.kz", "teach123", true, "TCH-002", TeacherTitle.LECTURER, 350000.0, "EMP-002");
        Student student = new Student("26B000001", "Grace Hopper", "grace@kbtu.kz", "student123", true,
                "26B000001", "SITE", DegreeType.BACHELOR);
        student.setYearOfStudy(4);
        student.setGpa(3.8);

        Collections.addAll(users, admin, manager, professor, lecturer, student);
        nextSequenceNumber = 2;

        Course oop = new Course("CS101", "Object-Oriented Programming", 5, 4, "SITE", 30);
        oop.addLesson(new Lesson("L-101", "OOP principles", LocalDateTime.now().plusDays(1), "401", LessonType.LECTURE));
        oop.addLesson(new Lesson("P-101", "Inheritance practice", LocalDateTime.now().plusDays(2), "Lab 3", LessonType.PRACTICE));
        Course algorithms = new Course("CS102", "Algorithms and Data Structures", 5, 4, "SITE", 25);
        Collections.addAll(courses, oop, algorithms);
        assignTeacherToCourseInternal(oop, professor);
        assignTeacherToCourseInternal(oop, lecturer);
        assignTeacherToCourseInternal(algorithms, lecturer);

        try {
            student.registerForCourse(oop);
            Enrollment enrollment = student.findEnrollment(oop);
            if (enrollment != null) {
                enrollment.approve();
                enrollment.setMark(new Mark(28, 27, 35));
                enrollments.add(enrollment);
            }
        } catch (CreditLimitExceededException | FailLimitExceededException e) {
            addLog("SYSTEM", "Seed enrollment failed: " + e.getMessage());
        }

        Researcher professorResearcher = createResearcherRole(professor, "SITE", "Software Engineering", 12);
        Researcher studentResearcher = createResearcherRole(student, "SITE", "Education Technology", 4);
        try {
            student.setResearchSupervisor(professorResearcher);
        } catch (Exception e) {
            addLog("SYSTEM", "Seed supervisor assignment failed: " + e.getMessage());
        }

        ResearchPaper paper = new ResearchPaper(nextPaperId++, "Research-Oriented University Information Systems",
                "IEEE Access", "10.1109/ACCESS.2025.000001",
                date(2025, Calendar.APRIL, 10), 11, 24, 42);
        paper.setAbstractText("A console-based university system with academic and research modules.");
        paper.setKeywords("OOP, university, research, Java");
        professorResearcher.publishPaper(paper);
        studentResearcher.publishPaper(paper);

        ResearchProject project = new ResearchProject(nextProjectId++, "Digital Campus Research Platform", "ACTIVE");
        project.setSupervisor(professorResearcher);
        try {
            project.addParticipant(professorResearcher);
            project.addParticipant(studentResearcher);
        } catch (NonResearcherJoinException e) {
            addLog("SYSTEM", "Seed project participant failed: " + e.getMessage());
        }
        project.addPaper(paper);
        researchProjects.add(project);

        news.add(new News(nextNewsId++, "Registration is open", "Students can register for available courses."));
        addLog("SYSTEM", "Default data initialized");
    }

    private Date date(int year, int month, int day) {
        return new GregorianCalendar(year, month, day).getTime();
    }

    public void save() {
        if (storage == null) {
            storage = new DataStorage();
        }
        storage.save(DB_FILE, this);
    }

    public boolean addUser(User user) {
        if (user == null || user.getId() == null || user.getEmail() == null) {
            return false;
        }
        if (findById(user.getId()) != null || findByEmail(user.getEmail()) != null) {
            return false;
        }
        users.add(user);
        if (user instanceof Teacher && ((Teacher) user).getTitle() == TeacherTitle.PROFESSOR) {
            createResearcherRole(user, "General", "Research", 3);
        }
        addLog(user.getId(), "User added: " + user.getRole());
        save();
        return true;
    }

    public boolean removeUser(String userId) {
        User user = findById(userId);
        if (user == null) {
            return false;
        }
        user.deleteAccount();
        addLog(userId, "User deactivated");
        save();
        return true;
    }

    public boolean updateUser(User updatedUser) {
        if (updatedUser == null) {
            return false;
        }
        for (int i = 0; i < users.size(); i++) {
            if (Objects.equals(users.get(i).getId(), updatedUser.getId())) {
                users.set(i, updatedUser);
                ensureProfessorResearchers();
                addLog(updatedUser.getId(), "User updated");
                save();
                return true;
            }
        }
        return false;
    }

    public boolean addCourse(Course course) {
        if (course == null || findCourseByCode(course.getCourseCode()) != null) {
            return false;
        }
        courses.add(course);
        Collections.sort(courses);
        addLog("SYSTEM", "Course added: " + course.getCourseCode());
        save();
        return true;
    }

    public boolean assignTeacherToCourse(Manager manager, Course course, Teacher teacher) {
        if (course == null || teacher == null) {
            return false;
        }
        assignTeacherToCourseInternal(course, teacher);
        addLog(manager == null ? "SYSTEM" : manager.getId(),
                "Assigned " + teacher.getFullname() + " to " + course.getCourseCode());
        save();
        return true;
    }

<<<<<<< HEAD
    public boolean assignTeacherToCourse(Admin admin, Course course, Teacher teacher) {
        if (course == null || teacher == null) {
            return false;
        }
        assignTeacherToCourseInternal(course, teacher);
        addLog(admin == null ? "SYSTEM" : admin.getId(),
                "Assigned " + teacher.getFullname() + " to " + course.getCourseCode());
        save();
        return true;
    }

=======
>>>>>>> 0eb84a614efeb1232b87cc0a5b786b5f24f58c59
    public void assignTeacherToCourse(String teacherId, String courseCode) {
        User user = findById(teacherId);
        Course course = findCourseByCode(courseCode);
        if (user instanceof Teacher && course != null) {
<<<<<<< HEAD
            assignTeacherToCourse((Manager) null, course, (Teacher) user);
=======
            assignTeacherToCourse(null, course, (Teacher) user);
>>>>>>> 0eb84a614efeb1232b87cc0a5b786b5f24f58c59
        }
    }

    private void assignTeacherToCourseInternal(Course course, Teacher teacher) {
        teacher.assignCourse(course);
        course.addInstructor(teacher);
    }

    public Enrollment registerStudentForCourse(Student student, Course course)
            throws CreditLimitExceededException, FailLimitExceededException {
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student and course are required");
        }
        student.registerForCourse(course);
        Enrollment enrollment = student.findEnrollment(course);
        if (enrollment != null && !enrollments.contains(enrollment)) {
            enrollments.add(enrollment);
        }
        addLog(student.getId(), "Registered for " + course.getCourseCode() + " with status PENDING");
        save();
        return enrollment;
    }

    public void approveEnrollment(Manager manager, Enrollment enrollment) {
        if (enrollment == null || enrollment.getStatus() != RegistrationStatus.PENDING) {
            return;
        }
        enrollment.approve();
        addLog(manager == null ? "SYSTEM" : manager.getId(),
                "Approved enrollment " + enrollment.getEnrollmentId());
        save();
    }

    public void rejectEnrollment(Manager manager, Enrollment enrollment) {
        if (enrollment == null || enrollment.getStatus() != RegistrationStatus.PENDING) {
            return;
        }
        enrollment.getStudent().dropCourse(enrollment.getCourse());
        enrollment.reject();
        addLog(manager == null ? "SYSTEM" : manager.getId(),
                "Rejected enrollment " + enrollment.getEnrollmentId());
        save();
    }

    public void approveAllPendingEnrollments(Manager manager) {
        for (Enrollment enrollment : getPendingEnrollments()) {
            enrollment.approve();
        }
        addLog(manager == null ? "SYSTEM" : manager.getId(), "Approved all pending enrollments");
        save();
    }

    public void putMark(Teacher teacher, Enrollment enrollment, Mark mark) {
        if (teacher == null || enrollment == null || mark == null) {
            throw new IllegalArgumentException("Teacher, enrollment and mark are required");
        }
        if (!teacher.getCourses().contains(enrollment.getCourse())) {
            throw new IllegalStateException("Teacher is not assigned to this course.");
        }
        teacher.gradeStudent(enrollment, mark);
        if (!mark.isPassed()) {
            try {
                enrollment.getStudent().incrementFailedCourses();
            } catch (FailLimitExceededException e) {
                addLog(enrollment.getStudent().getId(), e.getMessage());
            }
        }
        addLog(teacher.getId(), "Marked enrollment " + enrollment.getEnrollmentId());
        save();
    }

    public Researcher createResearcherRole(User user, String school, String researchArea, int hIndex) {
        Researcher existing = findResearcherByUser(user);
        if (existing != null) {
            existing.setSchool(school);
            existing.setResearchArea(researchArea);
            existing.setHIndex(Math.max(existing.getHIndex(), hIndex));
            return existing;
        }
        Researcher researcher = new Researcher(nextResearcherId++, user, school, researchArea);
        researcher.setHIndex(hIndex);
        researchers.add(researcher);
        return researcher;
    }

    public void addResearcher(Researcher researcher) {
        if (researcher != null && !researchers.contains(researcher)) {
            researchers.add(researcher);
            save();
        }
    }

    public void publishPaper(Researcher researcher, ResearchPaper paper) {
        if (researcher == null || paper == null) {
            return;
        }
        researcher.publishPaper(paper);
        addLog(researcher.getUser().getId(), "Published paper: " + paper.getTitle());
        save();
    }

    public ResearchProject createResearchProject(String topic, Researcher supervisor) {
        ResearchProject project = new ResearchProject(nextProjectId++, topic, "ACTIVE");
        project.setSupervisor(supervisor);
        if (supervisor != null) {
            try {
                project.addParticipant(supervisor);
            } catch (NonResearcherJoinException e) {
                addLog("SYSTEM", e.getMessage());
            }
        }
        researchProjects.add(project);
        save();
        return project;
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

    public String generateAcademicReport() {
        return generateReport(new AcademicPerformanceReportStrategy());
    }

    public String generateReport(ReportStrategy strategy) {
        return strategy.generate(this);
    }

    public void addNews(News newsItem) {
        if (newsItem != null) {
            news.add(newsItem);
            save();
        }
    }

    public void addRequest(Request request) {
        if (request != null) {
            requests.add(request);
            save();
        }
    }

    public void addLog(String userId, String action) {
        actionLogs.add(new ActionLog(nextLogId++, userId, action));
        LogService.getInstance().logSystem(userId, action);
    }

    public User findById(String id) {
        if (id == null) {
            return null;
        }
        return users.stream().filter(user -> id.equals(user.getId())).findFirst().orElse(null);
    }

    public User findByEmail(String email) {
        if (email == null) {
            return null;
        }
        return users.stream().filter(user -> email.equalsIgnoreCase(user.getEmail())).findFirst().orElse(null);
    }

    public Course findCourseByCode(String courseCode) {
        if (courseCode == null) {
            return null;
        }
        return courses.stream()
                .filter(course -> courseCode.equalsIgnoreCase(course.getCourseCode()))
                .findFirst()
                .orElse(null);
    }

    public Enrollment findEnrollmentById(int enrollmentId) {
        return enrollments.stream()
                .filter(enrollment -> enrollment.getEnrollmentId() == enrollmentId)
                .findFirst()
                .orElse(null);
    }

    public Researcher findResearcherByUser(User user) {
        if (user == null) {
            return null;
        }
        return researchers.stream()
                .filter(researcher -> Objects.equals(researcher.getUser(), user))
                .findFirst()
                .orElse(null);
    }

    public Researcher findResearcherByUserId(String userId) {
        User user = findById(userId);
        return findResearcherByUser(user);
    }

    public List<User> getAllUsers() {
        return Collections.unmodifiableList(users);
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public List<Enrollment> getEnrollments() {
        return Collections.unmodifiableList(enrollments);
    }

    public List<Enrollment> getPendingEnrollments() {
        return enrollments.stream()
                .filter(enrollment -> enrollment.getStatus() == RegistrationStatus.PENDING)
                .collect(Collectors.toList());
    }

    public List<Student> getAllStudents() {
        return users.stream()
                .filter(Student.class::isInstance)
                .map(Student.class::cast)
                .collect(Collectors.toList());
    }

    public List<Teacher> getAllTeachers() {
        return users.stream()
                .filter(Teacher.class::isInstance)
                .map(Teacher.class::cast)
                .collect(Collectors.toList());
    }

    public List<Manager> getAllManagers() {
        return users.stream()
                .filter(Manager.class::isInstance)
                .map(Manager.class::cast)
                .collect(Collectors.toList());
    }

    public List<Student> getStudentsSortedByGpa() {
        return getAllStudents().stream()
                .sorted(Comparator.comparingDouble(Student::getGpa).reversed())
                .collect(Collectors.toList());
    }

    public List<Teacher> getTeachersAlphabetically() {
        return getAllTeachers().stream()
                .sorted(Comparator.comparing(Teacher::getFullname, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public List<Researcher> getResearchers() {
        return Collections.unmodifiableList(researchers);
    }

    public List<ResearchProject> getResearchProjects() {
        return Collections.unmodifiableList(researchProjects);
    }

    public List<ResearchPaper> getAllResearchPapers() {
        Set<ResearchPaper> papers = new LinkedHashSet<>();
        for (Researcher researcher : researchers) {
            papers.addAll(researcher.getPapers());
        }
        return new ArrayList<>(papers);
    }

    public List<News> getNews() {
        return Collections.unmodifiableList(news);
    }

    public List<ActionLog> getActionLogs() {
        return Collections.unmodifiableList(actionLogs);
    }

    public List<Request> getRequests() {
        return Collections.unmodifiableList(requests);
    }

    public List<String> getClubs() {
        return Collections.unmodifiableList(clubs);
    }

    public int getNextSequenceNumber() {
        return nextSequenceNumber++;
    }

    public int getNextPaperId() {
        return nextPaperId++;
    }

    public int getNextNewsId() {
        return nextNewsId++;
    }

    private void ensureProfessorResearchers() {
        for (Teacher teacher : getAllTeachers()) {
            if (teacher.getTitle() == TeacherTitle.PROFESSOR) {
                createResearcherRole(teacher, "General", "Research", 3);
            }
        }
    }

    public Optional<Teacher> firstTeacherForCourse(Course course) {
        if (course == null || course.getInstructors().isEmpty()) {
            return Optional.empty();
        }
        return course.getInstructors().stream().findFirst();
    }
}
