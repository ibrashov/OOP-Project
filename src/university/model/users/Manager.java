package university.model.users;
import university.enums.ManagerType;
import university.model.academic.Course;
import university.model.academic.Enrollment;
import university.model.support.News;
import university.model.support.Request;
import university.system.UniversitySystem;

import java.util.List;

public class Manager extends Employee {
    private static final long serialVersionUID = 1L;

    private ManagerType managerType;

    public Manager(int id, String fullname, String email, String passwordHash, boolean isActive, double salary, String employeeId, ManagerType managerType) {
        super(id, fullname, email, passwordHash, isActive, salary, employeeId);
        this.managerType = managerType;
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    public void setManagerType(ManagerType managerType) {
        this.managerType = managerType;
    }

    public void approveRegistrations() {
        for (Enrollment enrollment : UniversitySystem.getInstance().getEnrollments()) {
            if (enrollment.getStatus() == university.enums.RegistrationStatus.PENDING) {
                approveRegistration(enrollment);
            }
        }
    }

    public void approveRegistration(Enrollment enrollment) {
        UniversitySystem.getInstance().approveEnrollment(this, enrollment);
    }

    public void assignTeacher() {
        System.out.println("Use assignTeacher(course, teacher) to assign a concrete teacher.");
    }

    public void assignTeacher(Course course, Teacher teacher) {
        UniversitySystem.getInstance().assignTeacherToCourse(this, course, teacher);
    }

    public void generateStatisticalReport() {
        System.out.println(UniversitySystem.getInstance().generateAcademicReport());
    }

    public void manageNews() {
        for (News news : UniversitySystem.getInstance().getNews()) {
            System.out.println(news);
        }
    }

    public void manageNews(News newsItem) {
        UniversitySystem.getInstance().addNews(newsItem);
    }

    public void viewStudentsSortedByGpa() {
        for (Student student : UniversitySystem.getInstance().getStudentsSortedByGpa()) {
            System.out.println(student);
        }
    }

    public void viewTeachersAlphabetically() {
        for (Teacher teacher : UniversitySystem.getInstance().getTeachersSortedByName()) {
            System.out.println(teacher);
        }
    }

    public void viewRequests(List<Request> requests) {
        for (Request request : requests) {
            System.out.println(request);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Manager manager = (Manager) obj;
        return getId() == manager.getId();
    }   

    @Override
    public int hashCode() {
        return Integer.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + getId() +
                ", fullname='" + getFullname() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", isActive=" + isActive() +
                ", salary=" + getSalary() +
                ", employeeId='" + getEmployeeId() + '\'' +
                ", managerType=" + managerType +
                '}';
    }

}
