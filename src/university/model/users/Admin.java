package university.model.users;

import university.model.academic.Course;
import university.model.support.ActionLog;
import university.system.UniversitySystem;

import java.util.List;

public class Admin extends Employee {
    private static final long serialVersionUID = 1L;

    public Admin(int id, String fullname, String email, String passwordHash, boolean isActive, double salary, String employeeId) {
        super(id, fullname, email, passwordHash, isActive, salary, employeeId);
    }

    public void manageUsers() {
        UniversitySystem.getInstance().printUsers();
    }

    public void manageCourses() {
        UniversitySystem.getInstance().printCourses();
    }

    public void viewReports() {
        System.out.println(UniversitySystem.getInstance().generateAcademicReport());
    }

    public void addUser() {
        System.out.println("Use addUser(user) to add a concrete user.");
    }

    public void addUser(User user) {
        UniversitySystem.getInstance().addUser(user);
    }

    public void removeUser() {
        System.out.println("Use removeUser(userId) to deactivate a concrete user.");
    }

    public void removeUser(int userId) {
        UniversitySystem.getInstance().removeUser(userId);
    }

    public void updateUser() {
        System.out.println("Use updateUser(user) to update a concrete user.");
    }

    public void updateUser(User user) {
        UniversitySystem.getInstance().updateUser(user);
    }

    public void addCourse(Course course) {
        UniversitySystem.getInstance().addCourse(course);
    }

    public void viewLogs() {
        viewLogs(UniversitySystem.getInstance().getActionLogs());
    }

    public void viewLogs(List<ActionLog> logs) {
        for (ActionLog log : logs) {
            System.out.println(log);
        }
    }

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Admin admin = (Admin) obj;
        return getId() == admin.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + getId() +
                ", fullname='" + getFullname() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", isActive=" + isActive() +
                ", salary=" + getSalary() +
                ", employeeId='" + getEmployeeId() + '\'' +
                '}';
    }   
}
