package university.model.research;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResearchPaper {
    private int paperId;
    private String title;
    private String journal;
    private String doi;
    private Date publicationDate;
    private int startPage;
    private int endPage;
    private int citations;
    private String abstractText;
    private String keywords;
    private List<Researcher> authors = new ArrayList<>();

    public ResearchPaper(int paperId, String title, String journal, String doi) {
        this.paperId = paperId;
        this.title = title;
        this.journal = journal;
        this.doi = doi;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getCitations() {
        return citations;
    }

    public void setCitations(int citations) {
        this.citations = citations;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<Researcher> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Researcher> authors) {
        this.authors = authors;
    }

    public void addAuthor(Researcher author) {
        // Author add logic goes here.
    }

    public int getLength() {
        if (endPage < startPage) {
            return 0;
        }
        return endPage - startPage + 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ResearchPaper that = (ResearchPaper) obj;
        return paperId == that.paperId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(paperId);
    }

    @Override
    public String toString() {
        return "ResearchPaper{" +
                "paperId=" + paperId +
                ", title='" + title + '\'' +
                ", journal='" + journal + '\'' +
                ", doi='" + doi + '\'' +
                '}';
    }
}
