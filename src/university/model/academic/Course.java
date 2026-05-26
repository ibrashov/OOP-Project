package university.model.academic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import university.model.users.Teacher;

public class Course implements Comparable<Course>, Serializable {
    private static final long serialVersionUID = 1L;

    private String courseCode;
    private String title;
    private int credits;
    private int yearOfStudy;
    private String major;
    private int maxStudents;
    private int reservedStudentsCount;
    private List<Lesson> lessons = new ArrayList<>();
    private Set<Teacher> instructors = new LinkedHashSet<>();

    public Course(String courseCode, String title, int credits, int yearOfStudy, String major, int maxStudents) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.yearOfStudy = yearOfStudy;
        this.major = major;
        this.maxStudents = maxStudents;
    }

    public void addLesson(Lesson lesson) {
        if (lesson != null && !lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    public void addInstructor(Teacher teacher) {
        if (teacher != null) {
            instructors.add(teacher);
        }
    }

    public void removeInstructor(Teacher teacher) {
        instructors.remove(teacher);
    }

    public boolean hasAvailableSeat() {
        return reservedStudentsCount < maxStudents;
    }

    public void reserveSeat() {
        if (!hasAvailableSeat()) {
            throw new IllegalStateException("No available seats for course " + courseCode);
        }
        reservedStudentsCount++;
    }

    public void releaseSeat() {
        if (reservedStudentsCount > 0) {
            reservedStudentsCount--;
        }
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public int getYearsOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public int getReservedStudentsCount() {
        return reservedStudentsCount;
    }

    public List<Lesson> getLessons() {
        return Collections.unmodifiableList(lessons);
    }

    public Set<Teacher> getInstructors() {
        return Collections.unmodifiableSet(instructors);
    }

    @Override
    public int compareTo(Course other) {
        if (other == null) {
            return 1;
        }
        return courseCode.compareToIgnoreCase(other.courseCode);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return Objects.equals(courseCode, course.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }

    @Override
    public String toString() {
        return "[" + courseCode + "] " + title + " (" + credits + " credits, year " + yearOfStudy
                + ", major " + major + ", seats " + reservedStudentsCount + "/" + maxStudents + ")";
    }
}
