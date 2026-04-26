package university.model.users;

import university.exceptions.CreditLimitExceededException;
import university.exceptions.FailLimitExceededException;
import university.model.academic.Course;
import university.model.academic.Enrollment;
import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private String studentId;
    private String major;
    private int yearOfStudy;
    private double gpa;
    private int totalCredits;
    private int failedCoursesCnt;
    private List<Enrollment> enrollments = new ArrayList<>();

    public Student(int id, String fullname, String email, String passwordHash, boolean isActive, String studentId, String major) {
        super(id, fullname, email, passwordHash, isActive);
        this.studentId = studentId;
        this.major = major;
    }

    public void registerForCourse(Course course)
            throws CreditLimitExceededException, FailLimitExceededException {
        if (totalCredits + course.getCredits() > 21) {
            throw new CreditLimitExceededException(
                "Credit limit exceeded. Current: " + totalCredits + ", Course: " + course.getCredits()
            );
        }
        if (failedCoursesCnt >= 3) {
            throw new FailLimitExceededException(
                "Failed courses limit exceeded: " + failedCoursesCnt
            );
        }
        Enrollment enrollment = new Enrollment(enrollments.size() + 1, this, course);
        enrollments.add(enrollment);
        totalCredits += course.getCredits();
        System.out.println("Registered for: " + course.getTitle());
    }

    public void dropCourse(Course course) {
        for (Enrollment e : enrollments) {
            if (e.getCourse().equals(course)) {
                e.cancel();
                totalCredits -= course.getCredits();
                System.out.println("Dropped: " + course.getTitle());
                return;
            }
        }
        System.out.println("Enrollment not found.");
    }

    public void viewMarks() {
        for (Enrollment e : enrollments) {
            System.out.println(e.getCourse().getTitle() + " - " + e.getMarkInfo());
        }
    }

    public List<Enrollment> getTranscript() {
        return enrollments;
    }

    public void incrementFailedCourses() {
        failedCoursesCnt++;
    }

    // Getters & Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public int getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }
    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public int getTotalCredits() { return totalCredits; }
    public void setTotalCredits(int totalCredits) { this.totalCredits = totalCredits; }
    public int getFailedCoursesCnt() { return failedCoursesCnt; }
    public void setFailedCoursesCnt(int failedCoursesCnt) { this.failedCoursesCnt = failedCoursesCnt; }
    public List<Enrollment> getEnrollments() { return enrollments; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return getId() == student.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", fullname='" + getFullname() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", isActive=" + isActive() +
                ", studentId='" + studentId + '\'' +
                ", major='" + major + '\'' +
                ", yearOfStudy=" + yearOfStudy +
                ", gpa=" + gpa +
                ", totalCredits=" + totalCredits +
                ", failedCoursesCnt=" + failedCoursesCnt +
                '}';
    }
}