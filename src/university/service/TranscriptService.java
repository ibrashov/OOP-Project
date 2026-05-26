package university.service;

import java.util.List;
import java.util.Locale;

import university.model.academic.Enrollment;
import university.model.academic.Mark;

public class TranscriptService {
    public void printTranscript(List<Enrollment> enrollments) {
        if (enrollments == null || enrollments.isEmpty()) {
            System.out.println("Transcript is empty.");
            return;
        }

        double totalPoints = 0.0;
        int graded = 0;
        for (Enrollment enrollment : enrollments) {
            Mark mark = enrollment.getMark();
            String grade = mark == null ? "N/A" : mark.getLetterGrade();
            System.out.println(enrollment.getCourse().getCourseCode() + " "
                    + enrollment.getCourse().getTitle() + " | "
                    + enrollment.getStatus() + " | " + enrollment.getMarkInfo());
            if (mark != null) {
                totalPoints += convertLetterToPoints(grade);
                graded++;
            }
        }

        double gpa = graded == 0 ? 0.0 : totalPoints / graded;
        System.out.println(String.format(Locale.US, "Calculated GPA: %.2f", gpa));
    }

    private double convertLetterToPoints(String grade) {
        return switch (grade) {
            case "A" -> 4.0;
            case "A-" -> 3.67;
            case "B+" -> 3.33;
            case "B" -> 3.0;
            case "B-" -> 2.67;
            case "C+" -> 2.33;
            case "C" -> 2.0;
            case "C-" -> 1.67;
            case "D+" -> 1.33;
            case "D" -> 1.0;
            default -> 0.0;
        };
    }
}
