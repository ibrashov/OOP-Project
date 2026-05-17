package university.model.academic;
import university.model.users.Teacher;
import java.util.*;
public class Course {
    private String courseCode;
    private String title;
    private int credits;
    private int yearsOfStudy;
    private String major;
    private int maxStudents;
    private List<Lesson> lessons = new ArrayList<>();
    private List<Teacher> instructors = new ArrayList<>();
    public Course(String courseCode, String title, int credits, int yearsOfStudy, String major, int maxStudents){
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.yearsOfStudy = yearsOfStudy;
        this.major = major;
        this.maxStudents = maxStudents;
    }
    public void addLesson(Lesson lesson){
        lessons.add(lesson);
    }
    public void addInstructor(Teacher teacher){
        instructors.add(teacher);
    }
    public int getCredits(){
        return credits;
    }
    public String getTitle(){
        return title;
    }
}
