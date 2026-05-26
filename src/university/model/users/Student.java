package university.model.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import university.enums.DegreeType;
import university.enums.RegistrationStatus;
import university.exceptions.CreditLimitExceededException;
import university.exceptions.FailLimitExceededException;
import university.exceptions.InvalidSupervisorException;
import university.model.academic.Course;
import university.model.academic.Enrollment;
import university.model.research.Researcher;

public class Student extends User {
    private static final long serialVersionUID = 1L;
    public static final int MAX_CREDITS = 21;
    public static final int MAX_FAILED_COURSES = 3;

    private String studentId;
    private String major;
    private int yearOfStudy = 1;
    private double gpa = 0.0;
    private int totalCredits = 0;
    private int failedCoursesCnt = 0;
    private Researcher researchSupervisor;
    private DegreeType degreeType;
    private List<Enrollment> enrollments = new ArrayList<>();
    private List<String> userClubs = new ArrayList<>();
    private Map<Teacher, Integer> teacherRatings = new LinkedHashMap<>();

    public Student(String id, String fullname, String email, String passwordHash, boolean isActive,
                   String studentId, String major, DegreeType degreeType) {
        super(id, fullname, email, passwordHash, isActive);
        this.studentId = studentId;
        this.major = major;
        this.degreeType = degreeType;
    }

    @Override
    public String getRole() {
        return "STUDENT";
    }

    public void registerForCourse(Course course) throws CreditLimitExceededException, FailLimitExceededException {
        if (course == null) {
            throw new IllegalArgumentException("Course is required");
        }
        if (failedCoursesCnt > MAX_FAILED_COURSES) {
            throw new FailLimitExceededException("Student cannot register after failing more than 3 times.");
        }
        if (totalCredits + course.getCredits() > MAX_CREDITS) {
            throw new CreditLimitExceededException("Credit limit exceeded. Max 21.");
        }
        if (hasActiveEnrollment(course)) {
            throw new IllegalArgumentException("Student is already registered for " + course.getCourseCode());
        }
        if (!course.hasAvailableSeat()) {
            throw new IllegalStateException("Course has no available seats.");
        }
        course.reserveSeat();
        Enrollment enrollment = new Enrollment(enrollments.size() + 1, this, course);
        enrollments.add(enrollment);
        totalCredits += course.getCredits();
    }

    public void addEnrollment(Enrollment enrollment) {
        if (enrollment != null && !enrollments.contains(enrollment)) {
            enrollments.add(enrollment);
        }
    }

    public void dropCourse(Course course) {
        Enrollment target = findEnrollment(course);
        if (target != null && target.getStatus() != RegistrationStatus.DROPPED) {
            target.drop();
            if (course != null) {
                course.releaseSeat();
                totalCredits = Math.max(0, totalCredits - course.getCredits());
            }
        }
    }

    public boolean hasActiveEnrollment(Course course) {
        Enrollment enrollment = findEnrollment(course);
        return enrollment != null
                && enrollment.getStatus() != RegistrationStatus.DROPPED
                && enrollment.getStatus() != RegistrationStatus.REJECTED;
    }

    public Enrollment findEnrollment(Course course) {
        if (course == null) {
            return null;
        }
        for (Enrollment enrollment : getEnrollments()) {
            if (course.equals(enrollment.getCourse())) {
                return enrollment;
            }
        }
        return null;
    }

    public List<Course> getActiveCourses() {
        List<Course> courses = new ArrayList<>();
        for (Enrollment enrollment : getEnrollments()) {
            if (enrollment.getStatus() == RegistrationStatus.APPROVED
                    || enrollment.getStatus() == RegistrationStatus.PENDING) {
                courses.add(enrollment.getCourse());
            }
        }
        return courses;
    }

    public void viewMarks() {
        getMarksList().forEach(System.out::println);
    }

    public void viewTeacherInfo(Course course) {
        if (course == null || course.getInstructors().isEmpty()) {
            System.out.println("No teachers assigned.");
            return;
        }
        course.getInstructors().forEach(System.out::println);
    }

    public void rateTeacher(Teacher teacher, int rating) {
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher is required");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be from 1 to 5");
        }
        teacherRatings.put(teacher, rating);
    }

    public List<Enrollment> getTranscript() {
        return Collections.unmodifiableList(getEnrollments());
    }

    public void incrementFailedCourses() throws FailLimitExceededException {
        failedCoursesCnt++;
        if (failedCoursesCnt > MAX_FAILED_COURSES) {
            throw new FailLimitExceededException("Student failed more than 3 times.");
        }
    }

    public void setResearchSupervisor(Researcher researchSupervisor) throws InvalidSupervisorException {
        if (degreeType != DegreeType.BACHELOR || yearOfStudy != 4) {
            throw new InvalidSupervisorException("Only 4th year bachelor students can have research supervisors.");
        }
        if (researchSupervisor == null) {
            throw new InvalidSupervisorException("Supervisor is required.");
        }
        if (researchSupervisor.getHIndex() < 3) {
            throw new InvalidSupervisorException("Supervisor must have h-index of at least 3.");
        }
        this.researchSupervisor = researchSupervisor;
    }

    public void validateSupervisorRequirement() throws InvalidSupervisorException {
        if (degreeType == DegreeType.BACHELOR && yearOfStudy == 4 && researchSupervisor == null) {
            throw new InvalidSupervisorException("4th year bachelor student must have a research supervisor.");
        }
    }

    public List<String> getMarksList() {
        List<String> list = new ArrayList<>();
        for (Enrollment enrollment : getEnrollments()) {
            if (enrollment.getCourse() == null) {
                continue;
            }
            list.add("[" + enrollment.getCourse().getCourseCode() + "] "
                    + enrollment.getCourse().getTitle() + " -> " + enrollment.getMarkInfo());
        }
        return list;
    }

    public void addClub(String clubName) {
        if (clubName != null && !userClubs.contains(clubName)) {
            userClubs.add(clubName);
        }
    }

    public void removeClub(String clubName) {
        userClubs.remove(clubName);
    }

    public List<String> getUserClubs() {
        return Collections.unmodifiableList(userClubs);
    }

    public Map<Teacher, Integer> getTeacherRatings() {
        return Collections.unmodifiableMap(teacherRatings);
    }

    public DegreeType getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(DegreeType degreeType) {
        this.degreeType = degreeType;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public int getFailedCoursesCnt() {
        return failedCoursesCnt;
    }

    public Researcher getResearchSupervisor() {
        return researchSupervisor;
    }

    public List<Enrollment> getEnrollments() {
        if (enrollments == null) {
            enrollments = new ArrayList<>();
        }
        return enrollments;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", fullname='" + getFullname() + '\'' +
                ", major='" + major + '\'' +
                ", year=" + yearOfStudy +
                ", gpa=" + gpa +
                ", credits=" + totalCredits +
                '}';
    }
}
