package university.model.support;

import java.util.Date;
import university.model.users.Employee;

public class Message implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private int messageId;
    private String content;
    private Date sentAt;
    private Employee sender;
    private Employee receiver;

    public Message(int messageId, String content, Employee sender, Employee receiver) {
        this.messageId = messageId;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.sentAt = new Date();
    }

    public int getMessageId() { return messageId; }
    public void setMessageId(int messageId) { this.messageId = messageId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getSentAt() { return sentAt; }
    public void setSentAt(Date sentAt) { this.sentAt = sentAt; }

    public Employee getSender() { return sender; }
    public void setSender(Employee sender) { this.sender = sender; }

    public Employee getReceiver() { return receiver; }
    public void setReceiver(Employee receiver) { this.receiver = receiver; }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", sender=" + sender.getFullname() +
                ", receiver=" + receiver.getFullname() +
                ", sentAt=" + sentAt +
                '}';
    }
}
