package university.model.support;

import java.util.Date;

public class ActionLog implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private int logId;
    private String action;
    private Date timestamp;
    private int userId;

    public ActionLog(int logId, int userId, String action) {
        this.logId = logId;
        this.userId = userId;
        this.action = action;
        this.timestamp = new Date();
    }

    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "ActionLog{" +
                "logId=" + logId +
                ", userId=" + userId +
                ", action='" + action + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
