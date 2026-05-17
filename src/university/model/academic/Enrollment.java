package university.model.academic;
import university.enums.RegistrationStatus;
import university.exceptions.CreditLimitExceededException;
import university.model.users.Student;

import java.io.*;
import java.time.*;
import java.util.*;

public class Enrollment implements Serializable {
    private int enrollmentId;
    private LocalDateTime registeredAt;
    private String semester;
    private RegistrationStatus status;
    private Student student;
    private Course course;
    private Mark mark;
    private boolean creditsRegisteredByEnrollment;

    public Enrollment(int enrollmentId, Student student, Course course)
            throws CreditLimitExceededException {
        initialize(enrollmentId, student, course, "N/A");
        course.reserveSeat();
        this.status = RegistrationStatus.PENDING;
        this.creditsRegisteredByEnrollment = false;
    }

    public Enrollment(int enrollmentId, Student student, Course course, String semester)
            throws CreditLimitExceededException {
        initialize(enrollmentId, student, course, semester);
        registerStudentCredits(student, course);
        try {
            course.reserveSeat();
        } catch (RuntimeException exception) {
            rollbackCredits();
            throw exception;
        }
        this.status = RegistrationStatus.PENDING;
        this.creditsRegisteredByEnrollment = true;
    }

    private void initialize(int enrollmentId, Student student, Course course, String semester) {
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student and course are required");
        }
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.registeredAt = LocalDateTime.now();
    }

    private void registerStudentCredits(Student student, Course course) throws CreditLimitExceededException {
        if (student.getTotalCredits() + course.getCredits() > 21) {
            throw new CreditLimitExceededException("Student cannot exceed 21 credits");
        }
        student.setTotalCredits(student.getTotalCredits() + course.getCredits());
    }

    private void rollbackCredits() {
        if (creditsRegisteredByEnrollment) {
            student.setTotalCredits(student.getTotalCredits() - course.getCredits());
            creditsRegisteredByEnrollment = false;
        }
    }

    public void approve() {
        if (status != RegistrationStatus.PENDING) {
            throw new IllegalStateException("Only PENDING enrollment can be approved");
        }
        status = RegistrationStatus.APPROVED;
    }
    public void confirm() {
        approve();
    }
    public void reject() {
        if (status != RegistrationStatus.PENDING) {
            throw new IllegalStateException("Only PENDING enrollment can be rejected");
        }
        status = RegistrationStatus.REJECTED;
        rollbackCredits();
        course.releaseSeat();
    }
    public void cancel() {
        drop();
    }

    public void drop() {
        if (status != RegistrationStatus.PENDING && status != RegistrationStatus.APPROVED) {
            throw new IllegalStateException("Only PENDING or APPROVED enrollment can be dropped");
        }
        status = RegistrationStatus.DROPPED;
        rollbackCredits();
        course.releaseSeat();
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public String getSemester() {
        return semester;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public String getMarkInfo() {
        if (mark == null) {
            return "No mark yet";
        }
        return mark.getLetterGrade() + " (" + mark.getTotal() + ")";
    }

    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", registeredAt=" + registeredAt +
                ", semester='" + semester + '\'' +
                ", status=" + status +
                ", student=" + student.getFullname() +
                ", course=" + course.getTitle() +
                '}';
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrollment)) return false;
        Enrollment that = (Enrollment) o;
        return enrollmentId == that.enrollmentId;
    }
    public int hashCode() {
        return Objects.hash(enrollmentId);
    }
}
