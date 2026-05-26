package university.model.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import university.model.support.Complaint;
import university.model.support.Message;
import university.model.support.Request;

public abstract class Employee extends User {
    private static final long serialVersionUID = 1L;

    private double salary;
    private String employeeId;
    private List<Message> messages = new ArrayList<>();
    private List<Complaint> complaints = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();

    public Employee(String id, String fullname, String email, String passwordHash, boolean isActive, double salary, String employeeId) {
        super(id, fullname, email, passwordHash, isActive);
        this.salary = salary;
        this.employeeId = employeeId;
    }

    public double getSalary() { return salary; }   
    public void setSalary(double salary) { this.salary = salary; }
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public void sendMessage(String message) {
        if (message != null && !message.isBlank()) {
            System.out.println(getFullname() + " sends message: " + message);
        }
    }

    public Message sendMessage(int messageId, String content, Employee receiver) {
        Message message = new Message(messageId, content, this, receiver);
        messages.add(message);
        if (receiver != null) {
            receiver.messages.add(message);
        }
        return message;
    }

    public void sendComplaint(String complaint) {
        if (complaint != null && !complaint.isBlank()) {
            complaints.add(new Complaint(complaints.size() + 1, complaint, this));
        }
    }

    public Complaint sendComplaint(int complaintId, String content) {
        Complaint complaint = new Complaint(complaintId, content, this);
        complaints.add(complaint);
        return complaint;
    }

    public Request createRequest(int requestId, String title, String description) {
        Request request = new Request(requestId, title, description, this);
        requests.add(request);
        return request;
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public List<Complaint> getComplaints() {
        return Collections.unmodifiableList(complaints);
    }

    public List<Request> getRequests() {
        return Collections.unmodifiableList(requests);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return getId() != null ? getId().equals(employee.getId()) : employee.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Employee{"+
            "id=" + getId() + 
            ", fullname='" + getFullname() 
            + "', email='" + getEmail() + 
            "', salary=" + salary + '}';
    }
}
