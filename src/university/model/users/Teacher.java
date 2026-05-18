package university.model.users;

import university.enums.TeacherTitle;
import university.model.academic.Course;
import university.model.academic.Enrollment;
import university.model.academic.Mark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Teacher extends Employee {
    private static final long serialVersionUID = 1L;

    private String teacherId;
    private TeacherTitle title;
    private List<Course> courses = new ArrayList<>();

    public Teacher(int id, String fullname, String email, String passwordHash, boolean isActive, String teacherId, TeacherTitle title, double salary, String employeeId) {
        super(id, fullname, email, passwordHash, isActive, salary, employeeId);
        this.teacherId = teacherId;
        this.title = title;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }  

    public TeacherTitle getTitle() {
        return title;
    }

    public void setTitle(TeacherTitle title) {
        this.title = title;
    }

    public void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println("No assigned courses.");
            return;
        }
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public void manageCourses() {
        viewCourses();
    }

    public void assignCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
        }
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public void gradeStudents() {
        System.out.println("Use gradeStudent(enrollment, mark) to put marks.");
    }

    public void gradeStudent(Enrollment enrollment, Mark mark) {
        if (enrollment == null || mark == null) {
            throw new IllegalArgumentException("Enrollment and mark are required");
        }
        if (!courses.contains(enrollment.getCourse())) {
            throw new IllegalArgumentException("Teacher is not assigned to this course");
        }
        enrollment.setMark(mark);
    }

    public void viewStudentInfo(){
        System.out.println("Use viewStudentInfo(student) to print a specific student.");
    }

    public void viewStudentInfo(Student student) {
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println(student);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Teacher teacher = (Teacher) obj;
        return getId() == teacher.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + getId() +
                ", fullname='" + getFullname() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", isActive=" + isActive() +
                ", salary=" + getSalary() +
                ", employeeId='" + getEmployeeId() + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", title=" + title +
                '}';

    }


    
}
