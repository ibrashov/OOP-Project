package university.model.support;

import java.util.ArrayList;
import java.util.List;

import university.enums.CouncilRole;
import university.model.users.Employee;
import university.model.users.Student;

public class StudentCouncil extends Club {
    private static final long serialVersionUID = 1L;

    private double budget;
    private List<CouncilMembership> memberships = new ArrayList<>();

    public StudentCouncil(int clubId, String name, String description, Employee advisor, double budget) {
        super(clubId, name, description, advisor);
        this.budget = budget;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public List<CouncilMembership> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<CouncilMembership> memberships) {
        this.memberships = memberships;
    }

    public void assignRole(Student student, CouncilRole role) {
        if (student == null || role == null) {
            return;
        }
        addMember(student);
        memberships.add(new CouncilMembership(new java.util.Date(), null, student, this, role));
    }
}
