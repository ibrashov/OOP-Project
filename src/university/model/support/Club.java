package university.model.support;

import java.util.ArrayList;
import java.util.List;

import university.model.users.Employee;
import university.model.users.Student;

public abstract class Club {
    private int clubId;
    private String name;
    private String description;
    private Employee advisor;
    private List<Student> members = new ArrayList<>();

    public Club(int clubId, String name, String description, Employee advisor) {
        this.clubId = clubId;
        this.name = name;
        this.description = description;
        this.advisor = advisor;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Employee advisor) {
        this.advisor = advisor;
    }

    public List<Student> getMembers() {
        return members;
    }

    public void setMembers(List<Student> members) {
        this.members = members;
    }

    public void addMember(Student student) {
        // Club member add logic goes here.
    }

    public void removeMember(Student student) {
        // Club member remove logic goes here.
    }
}
