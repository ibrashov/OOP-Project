package university.model.research;

import java.util.ArrayList;
import java.util.List;

import university.exceptions.NonResearcherJoinException;

public class ResearchProject {
    private int projectId;
    private String topic;
    private String status;
    private Researcher supervisor;
    private List<Researcher> participants = new ArrayList<>();
    private List<ResearchPaper> papers = new ArrayList<>();

    public ResearchProject(int projectId, String topic, String status) {
        this.projectId = projectId;
        this.topic = topic;
        this.status = status;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Researcher getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Researcher supervisor) {
        this.supervisor = supervisor;
    }

    public List<Researcher> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Researcher> participants) {
        this.participants = participants;
    }

    public List<ResearchPaper> getPapers() {
        return papers;
    }

    public void setPapers(List<ResearchPaper> papers) {
        this.papers = papers;
    }

    public void addParticipant(Researcher researcher) throws NonResearcherJoinException {
        // Participant validation and add logic goes here.
    }

    public void removeParticipant(Researcher researcher) {
        // Participant remove logic goes here.
    }

    public void addPaper(ResearchPaper paper) {
        // Project paper attach logic goes here.
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ResearchProject that = (ResearchProject) obj;
        return projectId == that.projectId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(projectId);
    }

    @Override
    public String toString() {
        return "ResearchProject{" +
                "projectId=" + projectId +
                ", topic='" + topic + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
