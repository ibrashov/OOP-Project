package university.model.users;
import university.enums.ManagerType;

public class Manager extends Employee {
    private ManagerType managerType;

    public Manager(int id, String fullname, String email, String passwordHash, boolean isActive, double salary, String employeeId, ManagerType managerType) {
        super(id, fullname, email, passwordHash, isActive, salary, employeeId);
        this.managerType = managerType;
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    public void setManagerType(ManagerType managerType) {
        this.managerType = managerType;
    }

    public void approveRegistrations() {
        // Implementation for approving student registrations
    }

    public void assignTeacher() {
        // Implementation for managing departments and faculties
    }

    public void generateStatisticalReport() {
        // Implementation for managing departments and faculties
    }

    public void manageNews() {
        // Implementation for managing departments and faculties
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Manager manager = (Manager) obj;
        return getId() == manager.getId();
    }   

    @Override
    public int hashCode() {
        return Integer.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + getId() +
                ", fullname='" + getFullname() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", isActive=" + isActive() +
                ", salary=" + getSalary() +
                ", employeeId='" + getEmployeeId() + '\'' +
                ", managerType=" + managerType +
                '}';
    }

}
