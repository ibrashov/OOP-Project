package university.model.support;

import java.util.Date;

import university.enums.CouncilRole;
import university.model.users.Student;

public class CouncilMembership {
    private Date startDate;
    private Date endDate;
    private Student student;
    private StudentCouncil council;
    private CouncilRole role;

    public CouncilMembership(Date startDate, Date endDate, Student student, StudentCouncil council, CouncilRole role) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.student = student;
        this.council = council;
        this.role = role;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentCouncil getCouncil() {
        return council;
    }

    public void setCouncil(StudentCouncil council) {
        this.council = council;
    }

    public CouncilRole getRole() {
        return role;
    }

    public void setRole(CouncilRole role) {
        this.role = role;
    }
}
