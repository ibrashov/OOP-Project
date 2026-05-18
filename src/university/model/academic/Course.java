package university.model.academic;

import university.model.users.Teacher;

import java.io.*;
import java.util.*;

public class Course implements Comparable<Course>, Serializable {
    private String courseCode;
    private String title;
    private int credits;
    private int yearOfStudy;
    private String major;
    private int maxStudents;
    private List<Lesson> lessons;
    private Set<Teacher> instructors;
    private int reservedStudentsCount;

    public Course(String courseCode, String title, int credits, int yearOfStudy, String major, int maxStudents) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.yearOfStudy = yearOfStudy;
        this.major = major;
        this.maxStudents = maxStudents;
        this.lessons = new ArrayList<>();
        this.instructors = new HashSet<>();
        this.reservedStudentsCount = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public int getYearsOfStudy() {
        return yearOfStudy;
    }

    public String getMajor() {
        return major;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public List<Lesson> getLessons() {
        return Collections.unmodifiableList(lessons);
    }

    public Set<Teacher> getInstructors() {
        return Collections.unmodifiableSet(instructors);
    }

    public int getReservedStudentsCount() {
        return reservedStudentsCount;
    }

    public void addLesson(Lesson lesson) {
        if (lesson != null && !lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    public void addInstructor(Teacher teacher) {
        if (teacher != null) {
            instructors.add(teacher);
            teacher.assignCourse(this);
        }
    }

    public boolean hasAvailableSeat() {
        return reservedStudentsCount < maxStudents;
    }

    public void reserveSeat() {
        if (!hasAvailableSeat()) {
            throw new IllegalStateException("Course is full: " + title);
        }
        reservedStudentsCount++;
    }

    public void releaseSeat() {
        if (reservedStudentsCount > 0) {
            reservedStudentsCount--;
        }
    }
    public int compareTo(Course other) {
        return this.courseCode.compareToIgnoreCase(other.courseCode);
    }
    public String toString() {
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", title='" + title + '\'' +
                ", credits=" + credits +
                ", yearOfStudy=" + yearOfStudy +
                ", major='" + major + '\'' +
                ", maxStudents=" + maxStudents +
                ", reservedStudentsCount=" + reservedStudentsCount +
                '}';
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(courseCode, course.courseCode);
    }
    public int hashCode() {
        return Objects.hash(courseCode);
    }
}
