package university.model.academic;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import university.enums.LessonType;

public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Lesson lesson = (Lesson) obj;
        return Objects.equals(lessonId, lesson.lessonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId);
    }

    @Override
    public String toString() {
        return type + " " + topic + " in " + room + " at " + dateTime;
    }
}
