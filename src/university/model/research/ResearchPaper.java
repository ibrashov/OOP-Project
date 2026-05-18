package university.model.research;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ResearchPaper implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public ResearchPaper(int paperId, String title, String journal, String doi,
                         Date publicationDate, int startPage, int endPage, int citations) {
        this(paperId, title, journal, doi);
        this.publicationDate = publicationDate;
        this.startPage = startPage;
        this.endPage = endPage;
        this.citations = citations;
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
        this.authors = authors == null ? new ArrayList<>() : new ArrayList<>(authors);
    }

    public void addAuthor(Researcher author) {
        if (author != null && !authors.contains(author)) {
            authors.add(author);
        }
    }

    public int getLength() {
        if (endPage < startPage) {
            return 0;
        }
        return endPage - startPage + 1;
    }

    public boolean wasPublishedInYear(int year) {
        if (publicationDate == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(publicationDate);
        return calendar.get(Calendar.YEAR) == year;
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
        return Objects.hash(paperId);
    }

    @Override
    public String toString() {
        return "ResearchPaper{" +
                "paperId=" + paperId +
                ", title='" + title + '\'' +
                ", journal='" + journal + '\'' +
                ", doi='" + doi + '\'' +
                ", citations=" + citations +
                ", length=" + getLength() +
                '}';
    }
}
