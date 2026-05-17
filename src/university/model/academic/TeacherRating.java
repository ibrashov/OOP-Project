package university.model.academic;

import java.util.Date;

public class TeacherRating {
    private int ratingId;
    private int score;
    private Date createdAt;
    private boolean isValid;

    public TeacherRating(int ratingId, int score) {
        this.ratingId = ratingId;
        this.score = score;
        this.createdAt = new Date();
        this.isValid = (score >= 1 && score <= 5);
    }

    public int getRatingId() { return ratingId; }
    public void setRatingId(int ratingId) { this.ratingId = ratingId; }

    public int getScore() { return score; }
    public void setScore(int score) {
        this.score = score;
        this.isValid = (score >= 1 && score <= 5);
    }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public boolean isValid() { return isValid; }

    @Override
    public String toString() {
        return "TeacherRating{" +
                "ratingId=" + ratingId +
                ", score=" + score +
                ", createdAt=" + createdAt +
                ", isValid=" + isValid +
                '}';
    }
}