package university.model.research;

import java.util.ArrayList;
import java.util.List;

import university.exceptions.InvalidSupervisorException;
import university.model.users.User;

public class Researcher {
    private int researcherId;
    private User user;
    private int hIndex;
    private String school;
    private String researchArea;
    private Researcher supervisor;
    private List<ResearchProject> projects = new ArrayList<>();
    private List<ResearchPaper> papers = new ArrayList<>();

    public Researcher(int researcherId, User user, String school, String researchArea) {
        this.researcherId = researcherId;
        this.user = user;
        this.school = school;
        this.researchArea = researchArea;
    }

    public int getResearcherId() {
        return researcherId;
    }

    public void setResearcherId(int researcherId) {
        this.researcherId = researcherId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getHIndex() {
        return hIndex;
    }

    public void setHIndex(int hIndex) {
        this.hIndex = hIndex;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }

    public Researcher getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Researcher supervisor) throws InvalidSupervisorException {
        if (supervisor == this) {
            throw new InvalidSupervisorException("Researcher cannot supervise themselves");
        }
        this.supervisor = supervisor;
    }

    public List<ResearchProject> getProjects() {
        return projects;
    }

    public void setProjects(List<ResearchProject> projects) {
        this.projects = projects;
    }

    public List<ResearchPaper> getPapers() {
        return papers;
    }

    public void setPapers(List<ResearchPaper> papers) {
        this.papers = papers;
    }

    public void joinProject(ResearchProject project) {
        // Research project join logic goes here.
    }

    public void publishPaper(ResearchPaper paper) {
        // Paper publication logic goes here.
    }

    public void printPapers() {
        // Paper printing or listing logic goes here.
    }

    public int calculateHIndex() {
        return hIndex;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Researcher that = (Researcher) obj;
        return researcherId == that.researcherId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(researcherId);
    }

    @Override
    public String toString() {
        return "Researcher{" +
                "researcherId=" + researcherId +
                ", user=" + user +
                ", hIndex=" + hIndex +
                ", school='" + school + '\'' +
                ", researchArea='" + researchArea + '\'' +
                '}';
    }
}
