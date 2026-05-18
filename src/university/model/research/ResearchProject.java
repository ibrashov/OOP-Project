package university.model.research;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import university.exceptions.NonResearcherJoinException;

public class ResearchProject implements Serializable {
    private static final long serialVersionUID = 1L;

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
        return Collections.unmodifiableList(participants);
    }

    public void setParticipants(List<Researcher> participants) {
        this.participants = participants == null ? new ArrayList<>() : new ArrayList<>(participants);
    }

    public List<ResearchPaper> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    public void setPapers(List<ResearchPaper> papers) {
        this.papers = papers == null ? new ArrayList<>() : new ArrayList<>(papers);
    }

    public void addParticipant(Researcher researcher) throws NonResearcherJoinException {
        addParticipant((Object) researcher);
    }

    public void addParticipant(Object participant) throws NonResearcherJoinException {
        if (!(participant instanceof Researcher)) {
            throw new NonResearcherJoinException("Only researchers can join a research project");
        }
        Researcher researcher = (Researcher) participant;
        if (!participants.contains(researcher)) {
            participants.add(researcher);
            researcher.joinProject(this);
        }
    }

    public void removeParticipant(Researcher researcher) {
        participants.remove(researcher);
    }

    public void addPaper(ResearchPaper paper) {
        if (paper != null && !papers.contains(paper)) {
            papers.add(paper);
        }
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
