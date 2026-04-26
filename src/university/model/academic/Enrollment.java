package university.model.academic;
import university.enums.RegistrationStatus;
import university.model.users.Student;
public class Enrollment {
    private int enrollmentID;
    private Student student;
    private Course course;
    private RegistrationStatus status;
    private Mark mark;
    public Enrollment(int enrollmentID, Student student, Course course){
        this.enrollmentID = enrollmentID;
        this.student = student;
        this.course = course;
        this.status = RegistrationStatus.PENDING;
    }
    public void confirm(){
        status = RegistrationStatus.APPROVED;
    }
    public void cancel(){
        status = RegistrationStatus.DROPPED;    
    }
    public void setMark(Mark mark){
        this.mark = mark;
    }
    public Course getCourse(){
        return course;
    }
    public Student getStudent(){
        return student;
    }
}
