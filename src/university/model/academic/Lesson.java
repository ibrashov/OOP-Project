package university.model.academic;
import university.enums.LessonType;
import java.io.*;
import java.time.*;
import java.util.*;

public class Lesson implements Serializable {
    private String lessonId;
    private String topic;
    private LocalDateTime dateTime;
    private String room;
    private LessonType type;

    public Lesson(String lessonId, String topic, LocalDateTime dateTime, String room, LessonType type) {
        this.lessonId = lessonId;
        this.topic = topic;
        this.dateTime = dateTime;
        this.room = room;
        this.type = type;
    }

    public String getLessonId() {
        return lessonId;
    }

    public String getLessonID() {
        return lessonId;
    }

    public String getTopic() {
        return topic;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getRoom() {
        return room;
    }

    public LessonType getType() {
        return type;
    }
    public String toString() {
        return "Lesson{" +
                "lessonId='" + lessonId + '\'' +
                ", topic='" + topic + '\'' +
                ", dateTime=" + dateTime +
                ", room='" + room + '\'' +
                ", type=" + type +
                '}';
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(lessonId, lesson.lessonId);
    }
    public int hashCode() {
        return Objects.hash(lessonId);
    }
}
