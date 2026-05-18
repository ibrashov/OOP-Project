package university.model.support;

import java.util.Date;
import university.enums.RequestStatus;
import university.model.users.Employee;

public class Request implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private int requestId;
    private String title;
    private String description;
    private RequestStatus status;
    private Date createdAt;
    private Employee sender;

    public Request(int requestId, String title, String description, Employee sender) {
        this.requestId = requestId;
        this.title = title;
        this.description = description;
        this.sender = sender;
        this.status = RequestStatus.PENDING;
        this.createdAt = new Date();
    }

    public void approve() { this.status = RequestStatus.APPROVED; }
    public void reject() { this.status = RequestStatus.REJECTED; }
    public void sign() { this.status = RequestStatus.APPROVED; }

    public int getRequestId() { return requestId; }
    public void setRequestId(int requestId) { this.requestId = requestId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Employee getSender() { return sender; }
    public void setSender(Employee sender) { this.sender = sender; }

    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", sender=" + sender.getFullname() +
                '}';
    }
}
