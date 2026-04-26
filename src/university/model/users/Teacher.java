package university.model.users;

import university.enums.TeacherTitle;

public class Teacher extends Employee {
    private String teacherId;
    private TeacherTitle title;

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
        // Implementation for viewing courses taught by the teacher
    }

    public void manageCourses() {
        // Implementation for managing courses taught by the teacher
    }

    public void gradeStudents() {
        // Implementation for grading students
    }

    public void viewStudentInfo(){
        // Implementation for viewing student information
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
