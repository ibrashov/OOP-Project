package university.service;
import university.enums.RegistrationStatus;
import university.model.academic.Enrollment;
import university.model.academic.Mark;
import java.util.*;
public class TranscriptService {

    public void printTranscript(List<Enrollment> enrollments) {
        double totalGradePoints = 0.0;
        int totalCredits = 0;
        System.out.println("===== TRANSCRIPT =====");
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStatus() != RegistrationStatus.APPROVED) {
                continue;
            }
            Mark mark = enrollment.getMark();
            String grade = mark == null ? "N/A" : mark.getLetterGrade();
            double total = mark == null ? 0 : mark.getTotal();
            int credits = enrollment.getCourse().getCredits();
            System.out.println("Course: " + enrollment.getCourse().getTitle());
            System.out.println("Credits: " + credits);
            System.out.println("Status: " + enrollment.getStatus());
            System.out.println("Grade: " + grade + " (" + total + ")");
            System.out.println("----------------------");
            if (mark != null) {
                totalGradePoints += convertLetterToPoints(grade) * credits;
                totalCredits += credits;
            }
        }
        double gpa = totalCredits == 0 ? 0.0 : totalGradePoints / totalCredits;
        System.out.println("Total registered credits in transcript: " + totalCredits);
        System.out.printf("GPA: %.2f%n", gpa);
        System.out.println("======================");
    }
    private double convertLetterToPoints(String grade) {
        switch (grade) {
            case "A":
                return 4.0;
            case "A-":
                return 3.7;
            case "B+":
                return 3.3;
            case "B":
                return 3.0;
            case "B-":
                return 2.7;
            case "C+":
                return 2.3;
            case "C":
                return 2.0;
            case "C-":
                return 1.7;
            case "D+":
                return 1.3;
            case "D":
                return 1.0;
            default:
                return 0.0;
        }
    }
}
