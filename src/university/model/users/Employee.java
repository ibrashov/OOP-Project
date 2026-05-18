package university.model.users;

import university.model.support.Complaint;
import university.model.support.Message;

public abstract class Employee extends User {
    private static final long serialVersionUID = 1L;

    private double salary;
    private String employeeId;

    public Employee(int id, String fullname, String email, String passwordHash, boolean isActive, double salary, String employeeId) {
        super(id, fullname, email, passwordHash, isActive);
        this.salary = salary;
        this.employeeId = employeeId;
    }

    public double getSalary() {
        return salary;
    }   

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void sendMessage(String message) {
        System.out.println("Message draft: " + message);
    }

    public Message sendMessage(int messageId, String content, Employee receiver) {
        if (receiver == null) {
            throw new IllegalArgumentException("Receiver is required");
        }
        return new Message(messageId, content, this, receiver);
    }

    public void sendComplaint(String complaint) {
        System.out.println("Complaint draft: " + complaint);
    }

    public Complaint sendComplaint(int complaintId, String content) {
        return new Complaint(complaintId, content, this);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return getId() == employee.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + getId() +
                ", fullname='" + getFullname() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", isActive=" + isActive() +
                ", salary=" + salary +
                ", employeeId='" + employeeId + '\'' +
                '}';

    }
}
