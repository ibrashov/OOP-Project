package university.model.users;

public class Admin extends Employee {
    public Admin(int id, String fullname, String email, String passwordHash, boolean isActive, double salary, String employeeId) {
        super(id, fullname, email, passwordHash, isActive, salary, employeeId);
    }

    public void manageUsers() {
        // Implementation for managing users (students and teachers)
    }

    public void manageCourses() {
        // Implementation for managing courses
    }

    public void viewReports() {
        // Implementation for viewing reports and analytics
    }

    public void addUser() {
        // Implementation for adding a new user (student or teacher)
    }

    public void removeUser() {
        // Implementation for removing a user (student or teacher)
    }

    public void updateUser() {
        // Implementation for updating user information
    }

    public void viewLogs() {
        // Implementation for viewing system logs and activities
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
