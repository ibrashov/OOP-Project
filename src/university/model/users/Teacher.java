package university.model.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import university.enums.TeacherTitle;
import university.model.academic.Course;
import university.model.academic.Enrollment;
import university.model.academic.Mark;

public class Teacher extends Employee {
    private static final long serialVersionUID = 1L;

    private String teacherId;
    private TeacherTitle title;
    private List<Course> courses = new ArrayList<>();

    public Teacher(String id, String fullname, String email, String passwordHash, boolean isActive, String teacherId, TeacherTitle title, double salary, String employeeId) {
        super(id, fullname, email, passwordHash, isActive, salary, employeeId);
        this.teacherId = teacherId;
        this.title = title;
    }

    @Override
    public String getRole() { return "TEACHER"; }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }  
    public TeacherTitle getTitle() { return title; }
    public void setTitle(TeacherTitle title) { this.title = title; }

    public void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println("No assigned courses.");
            return;
        }
        courses.forEach(System.out::println);
    }

    public void manageCourses() { viewCourses(); }
    public void assignCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
            course.addInstructor(this);
        }
    }

    public void removeCourse(Course course) {
        courses.remove(course);
        if (course != null) {
            course.removeInstructor(this);
        }
    }

    public List<Course> getCourses() { return Collections.unmodifiableList(courses); }
    public void gradeStudents() { courses.forEach(System.out::println); }
    public void gradeStudent(Enrollment enrollment, Mark mark) {
        if (enrollment == null || mark == null) {
            throw new IllegalArgumentException("Enrollment and mark are required");
        }
        enrollment.setMark(mark);
    }
    public void viewStudentInfo() { courses.forEach(System.out::println); }
    public void viewStudentInfo(Student student) { System.out.println(student); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Teacher teacher = (Teacher) obj;
        return Objects.equals(getId(), teacher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + getId() +
                ", fullname='" + getFullname() + '\'' +
                ", title=" + title +
                ", courses=" + courses.size() +
                '}';
    }
}
