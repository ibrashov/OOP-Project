package university.model.academic;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import university.enums.RegistrationStatus;
import university.model.users.Student;

public class Enrollment implements Serializable {
    private static final long serialVersionUID = 1L;

    private int enrollmentId;
    private LocalDateTime registeredAt;
    private String semester;
    private RegistrationStatus status;
    private Student student;
    private Course course;
    private Mark mark;

    public Enrollment(int enrollmentId, Student student, Course course) {
        this(enrollmentId, student, course, "Fall");
    }

    public Enrollment(int enrollmentId, Student student, Course course, String semester) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.status = RegistrationStatus.PENDING;
        this.registeredAt = LocalDateTime.now();
    }

    public void approve() {
        status = RegistrationStatus.APPROVED;
    }

    public void confirm() {
        approve();
    }

    public void reject() {
        status = RegistrationStatus.REJECTED;
    }

    public void cancel() {
        drop();
    }

    public void drop() {
        status = RegistrationStatus.DROPPED;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public int getEnrollmentID() {
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

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }

    public Mark getMark() {
        return mark;
    }

    public String getMarkInfo() {
        if (mark == null) {
            return "No mark yet";
        }
        return mark.getLetterGrade() + " (" + mark.getTotal() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Enrollment that = (Enrollment) obj;
        return enrollmentId == that.enrollmentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId);
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + enrollmentId +
                ", student=" + (student == null ? "-" : student.getFullname()) +
                ", course=" + (course == null ? "-" : course.getCourseCode()) +
                ", status=" + status +
                ", mark=" + getMarkInfo() +
                '}';
    }
}
