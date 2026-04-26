package university.service;
import university.model.academic.Enrollment;
import java.util.*;
public class TranscriptService{
    public void printTranscript(List<Enrollment> enrollments){
        for(Enrollment e : enrollments){
            System.out.println(e.getCourse().getTitle());
        }
    }
}
