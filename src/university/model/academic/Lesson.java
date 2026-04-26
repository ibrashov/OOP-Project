package university.model.academic;
import university.enums.LessonType;

import java.time.LocalDateTime;
import java.util.*;
public class Lesson {
    private String lessonID;
    private String topic;
    private LocalDateTime dataTime;
    private String room;
    private LessonType type;
    public Lesson(String lessonID, String topic, LocalDateTime dataTime, String room, LessonType type){
        this.lessonID = lessonID;
        this.topic = topic;
        this.dataTime = dataTime;
        this.room = room;
        this.type = type;
    }
}
