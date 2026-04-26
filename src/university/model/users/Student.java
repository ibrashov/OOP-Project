package university.model.users;

public class Student extends User {
    private String studentId;
    private String major;
    private int yearOfStudy;
    private double gpa;
    private int totalCredits;
    private int failedCoursesCnt;

    public Student(int id, String fullname, String email, String passwordHash, boolean isActive, String studentId, String major) {
        super(id, fullname, email, passwordHash, isActive);
        this.studentId = studentId;
        this.major = major;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public int getFailedCoursesCnt() {
        return failedCoursesCnt;
    }

    public void setFailedCoursesCnt(int failedCoursesCnt) {
        this.failedCoursesCnt = failedCoursesCnt;
    }     

    void registerForCourse(String courseId) {
        // Implementation for registering for a course
    }

    void dropCourse(String courseId) {
        // Implementation for dropping a course
    }
    
    void viewGrades() {
        // Implementation for viewing grades
    }

    void getTranscript() {
        // Implementation for getting transcript
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;        
        return getId() == student.getId();
    }   

    @Override
    public int hashCode() {
        return Integer.hashCode(getId());
    }  

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", fullname='" + getFullname() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", isActive=" + isActive() +
                ", studentId='" + studentId + '\'' +
                ", major='" + major + '\'' +
                ", yearOfStudy=" + yearOfStudy +
                ", gpa=" + gpa +
                ", totalCredits=" + totalCredits +
                ", failedCoursesCnt=" + failedCoursesCnt +
                '}';
    }
    
}
