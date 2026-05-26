package university.service;

import java.util.Locale;

import university.model.academic.Enrollment;
import university.model.academic.Mark;

public class AcademicPerformanceReportStrategy implements ReportStrategy {
    private static final long serialVersionUID = 1L;

    @Override
    public String generate(UniversitySystem system) {
        int graded = 0;
        int passed = 0;
        double total = 0.0;

        for (Enrollment enrollment : system.getEnrollments()) {
            Mark mark = enrollment.getMark();
            if (mark == null) {
                continue;
            }
            graded++;
            total += mark.getTotal();
            if (mark.isPassed()) {
                passed++;
            }
        }

        double average = graded == 0 ? 0.0 : total / graded;
        int failed = graded - passed;
        return String.format(Locale.US,
                "Academic report:%nStudents: %d%nCourses: %d%nEnrollments: %d%nGraded enrollments: %d%nPassed: %d%nFailed: %d%nAverage total score: %.2f",
                system.getAllStudents().size(),
                system.getCourses().size(),
                system.getEnrollments().size(),
                graded,
                passed,
                failed,
                average);
    }
}
