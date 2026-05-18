package university.model.users;

import java.util.List;
import university.model.academic.Course;
import university.model.support.ActionLog;
import university.service.UniversitySystem;

public class Admin extends Employee {
    private static final long serialVersionUID = 1L;

    public Admin(String id, String fullname, String email, String passwordHash, boolean isActive, double salary, String employeeId) {
        super(id, fullname, email, passwordHash, isActive, salary, employeeId);
    }

    @Override
    public String getRole() { return "ADMIN"; }

    public void manageUsers() { UniversitySystem.getInstance().getAllUsers(); }
    public void manageCourses() { UniversitySystem.getInstance().getCourses(); }
    public void viewReports() { System.out.println(UniversitySystem.getInstance().generateAcademicReport()); }

    public void addUser(User user) {
        UniversitySystem.getInstance().addUser(user);
    }

    public void removeUser(String userId) {
        UniversitySystem.getInstance().removeUser(userId);
    }

    public void updateUser(User user) {
        UniversitySystem.getInstance().updateUser(user);
    }

    public void addCourse(Course course) {
        UniversitySystem.getInstance().addCourse(course);
    }

    public void viewLogs() {
        UniversitySystem.getInstance().getActionLogs().forEach(System.out::println);
    }

    public void viewLogs(List<ActionLog> logs) {
        if (logs != null) {
            logs.forEach(System.out::println);
        }
    }

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Admin admin = (Admin) obj;
        return getId() != null ? getId().equals(admin.getId()) : admin.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
