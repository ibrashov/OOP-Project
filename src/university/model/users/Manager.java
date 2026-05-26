package university.model.users;

import java.util.List;
import university.enums.ManagerType;
import university.model.academic.Course;
import university.model.academic.Enrollment;
import university.model.support.News;
import university.model.support.Request;
import university.service.UniversitySystem;

public class Manager extends Employee {
    private static final long serialVersionUID = 1L;

    private ManagerType managerType;

    public Manager(String id, String fullname, String email, String passwordHash, boolean isActive, double salary, String employeeId, ManagerType managerType) {
        super(id, fullname, email, passwordHash, isActive, salary, employeeId);
        this.managerType = managerType;
    }

    @Override
    public String getRole() { return "MANAGER"; }

    public ManagerType getManagerType() { return managerType; }
    public void setManagerType(ManagerType managerType) { this.managerType = managerType; }

    public void approveRegistrations() {
        UniversitySystem.getInstance().approveAllPendingEnrollments(this);
    }

    public void approveRegistration(Enrollment enrollment) {
        UniversitySystem.getInstance().approveEnrollment(this, enrollment);
    }

    public void assignTeacher() {
        System.out.println("Use assignTeacher(Course, Teacher) to assign a teacher.");
    }

    public void assignTeacher(Course course, Teacher teacher) {
        UniversitySystem.getInstance().assignTeacherToCourse(this, course, teacher);
    }

    public void generateStatisticalReport() {
        System.out.println(UniversitySystem.getInstance().generateAcademicReport());
    }

    public void manageNews() {
        UniversitySystem.getInstance().getNews().forEach(System.out::println);
    }

    public void manageNews(News newsItem) {
        UniversitySystem.getInstance().addNews(newsItem);
    }

    public void viewStudentsSortedByGpa() {
        UniversitySystem.getInstance().getStudentsSortedByGpa().forEach(System.out::println);
    }

    public void viewTeachersAlphabetically() {
        UniversitySystem.getInstance().getTeachersAlphabetically().forEach(System.out::println);
    }

    public void viewRequests(List<Request> requests) {
        if (requests != null) {
            requests.forEach(System.out::println);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Manager manager = (Manager) obj;
        return getId() != null ? getId().equals(manager.getId()) : manager.getId() == null;
    }   

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
