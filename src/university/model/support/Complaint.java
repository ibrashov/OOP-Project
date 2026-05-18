package university.model.support;

import java.io.Serializable;
import java.util.Date;

import university.model.users.Employee;

public class Complaint implements Serializable {
    private static final long serialVersionUID = 1L;

    private int complaintId;
    private String content;
    private Date createdAt;
    private Employee sender;

    public Complaint(int complaintId, String content, Employee sender) {
        this.complaintId = complaintId;
        this.content = content;
        this.sender = sender;
        this.createdAt = new Date();
    }

    public int getComplaintId() { return complaintId; }
    public void setComplaintId(int complaintId) { this.complaintId = complaintId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Employee getSender() { return sender; }
    public void setSender(Employee sender) { this.sender = sender; }

    @Override
    public String toString() {
        return "Complaint{" +
                "complaintId=" + complaintId +
                ", sender=" + sender.getFullname() +
                ", createdAt=" + createdAt +
                '}';
    }
}
