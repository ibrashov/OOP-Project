package university.model.support;

import java.util.Date;

public class News {
    private int newsId;
    private String title;
    private String content;
    private Date createdAt;

    public News(int newsId, String title, String content) {
        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.createdAt = new Date();
    }

    public int getNewsId() { return newsId; }
    public void setNewsId(int newsId) { this.newsId = newsId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "News{" +
                "newsId=" + newsId +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}