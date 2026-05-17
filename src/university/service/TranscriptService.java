package university.service;

import university.model.academic.Enrollment;
import university.model.academic.Mark;
import university.model.users.Student;
import university.enums.RegistrationStatus;

import java.util.*;

public class TranscriptService {

    public void printTranscript(List<Enrollment> enrollments) {
        for (Enrollment e : enrollments) {
            System.out.println(e.getCourse().getTitle() + " - " + e.getMarkInfo());
        }
    }

    public List<Enrollment> getTranscript(Student student, List<Enrollment> enrollments) {
        List<Enrollment> transcript = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getStudent().equals(student) && e.getStatus() == RegistrationStatus.APPROVED) {
                transcript.add(e);
            }
        }
        return transcript;
    }

    public double calculateGPA(List<Enrollment> enrollments) {
        if (enrollments == null || enrollments.isEmpty()) return 0.0;

        double total = 0.0;
        int count = 0;

        for (Enrollment e : enrollments) {
            String grade = e.getMarkInfo();
            double points = letterToGPA(grade);
            if (points >= 0) {
                total += points;
                count++;
            }
        }

        return count == 0 ? 0.0 : Math.round((total / count) * 100.0) / 100.0;
    }

    private double letterToGPA(String markInfo) {
        if (markInfo == null || markInfo.equals("No mark yet")) return -1;
        if (markInfo.startsWith("A-")) return 3.67;
        if (markInfo.startsWith("A"))  return 4.0;
        if (markInfo.startsWith("B+")) return 3.33;
        if (markInfo.startsWith("B-")) return 2.67;
        if (markInfo.startsWith("B"))  return 3.0;
        if (markInfo.startsWith("C+")) return 2.33;
        if (markInfo.startsWith("C-")) return 1.67;
        if (markInfo.startsWith("C"))  return 2.0;
        if (markInfo.startsWith("D+")) return 1.33;
        if (markInfo.startsWith("D"))  return 1.0;
        if (markInfo.startsWith("F"))  return 0.0;
        return -1;
    }
}