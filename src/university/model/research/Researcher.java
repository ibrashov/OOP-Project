package university.model.research;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import university.exceptions.InvalidSupervisorException;
import university.model.users.User;

public class Researcher implements Serializable {
    private static final long serialVersionUID = 1L;

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
        if (supervisor != null && supervisor.getHIndex() < 3) {
            throw new InvalidSupervisorException("Supervisor must have h-index of at least 3");
        }
        if (supervisor == this) {
            throw new InvalidSupervisorException("Researcher cannot supervise themselves");
        }
        this.supervisor = supervisor;
    }

    public List<ResearchProject> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    public void setProjects(List<ResearchProject> projects) {
        this.projects = projects == null ? new ArrayList<>() : new ArrayList<>(projects);
    }

    public List<ResearchPaper> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    public void setPapers(List<ResearchPaper> papers) {
        this.papers = papers == null ? new ArrayList<>() : new ArrayList<>(papers);
    }

    public void joinProject(ResearchProject project) {
        if (project != null && !projects.contains(project)) {
            projects.add(project);
        }
    }

    public void publishPaper(ResearchPaper paper) {
        if (paper != null && !papers.contains(paper)) {
            papers.add(paper);
            paper.addAuthor(this);
        }
    }

    public void printPapers() {
        printPapers(null);
    }

    public void printPapers(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> sortedPapers = new ArrayList<>(papers);
        if (comparator != null) {
            sortedPapers.sort(comparator);
        }
        for (ResearchPaper paper : sortedPapers) {
            System.out.println(paper);
        }
    }

    public int calculateHIndex() {
        List<Integer> citations = new ArrayList<>();
        for (ResearchPaper paper : papers) {
            citations.add(paper.getCitations());
        }
        citations.sort(Collections.reverseOrder());
        int calculated = 0;
        for (int i = 0; i < citations.size(); i++) {
            if (citations.get(i) >= i + 1) {
                calculated = i + 1;
            }
        }
        hIndex = Math.max(hIndex, calculated);
        return hIndex;
    }

    public int getTotalCitations() {
        int total = 0;
        for (ResearchPaper paper : papers) {
            total += paper.getCitations();
        }
        return total;
    }

    public int getCitationsInYear(int year) {
        int total = 0;
        for (ResearchPaper paper : papers) {
            if (paper.wasPublishedInYear(year)) {
                total += paper.getCitations();
            }
        }
        return total;
    }

    public int getCitationsForYear(int year) {
        return getCitationsInYear(year);
    }

    public static Researcher getTopCitedResearcherBySchool(List<Researcher> researchers, String school) {
        if (researchers == null) {
            return null;
        }
        Researcher topResearcher = null;
        for (Researcher researcher : researchers) {
            if (researcher == null || !Objects.equals(researcher.getSchool(), school)) {
                continue;
            }
            if (topResearcher == null || researcher.getTotalCitations() > topResearcher.getTotalCitations()) {
                topResearcher = researcher;
            }
        }
        return topResearcher;
    }

    public static Researcher getTopCitedResearcherByYear(List<Researcher> researchers, int year) {
        if (researchers == null) {
            return null;
        }
        Researcher topResearcher = null;
        for (Researcher researcher : researchers) {
            if (researcher == null) {
                continue;
            }
            if (topResearcher == null || researcher.getCitationsInYear(year) > topResearcher.getCitationsInYear(year)) {
                topResearcher = researcher;
            }
        }
        return topResearcher;
    }

    public static void printAllPapers(List<Researcher> researchers, Comparator<ResearchPaper> comparator) {
        if (researchers == null) {
            return;
        }
        Set<ResearchPaper> uniquePapers = new LinkedHashSet<>();
        for (Researcher researcher : researchers) {
            if (researcher != null) {
                uniquePapers.addAll(researcher.getPapers());
            }
        }
        List<ResearchPaper> allPapers = new ArrayList<>(uniquePapers);
        if (comparator != null) {
            allPapers.sort(comparator);
        }
        for (ResearchPaper paper : allPapers) {
            System.out.println(paper);
        }
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
