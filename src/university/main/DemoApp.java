package university.main;

import university.comparators.research.ResearchPaperByCitationsComparator;
import university.model.academic.Course;
import university.model.academic.Enrollment;
import university.model.academic.Mark;
import university.model.research.Researcher;
import university.model.users.Manager;
import university.model.users.Student;
import university.model.users.Teacher;
import university.service.ResearchSummaryReportStrategy;
import university.service.TranscriptService;
import university.service.UniversitySystem;

public class DemoApp {
    public static void main(String[] args) {
        UniversitySystem system = UniversitySystem.getInstance();

        Student student = system.getAllStudents().get(0);
        Teacher teacher = system.getAllTeachers().get(0);
        Manager manager = system.getAllManagers().get(0);
        Course course = system.getCourses().get(0);

        try {
            if (!student.hasActiveEnrollment(course)) {
                Enrollment enrollment = system.registerStudentForCourse(student, course);
                system.approveEnrollment(manager, enrollment);
                system.putMark(teacher, enrollment, new Mark(28, 27, 35));
            }
        } catch (Exception e) {
            System.out.println("Academic demo skipped: " + e.getMessage());
        }

        System.out.println(system.generateAcademicReport());
        new TranscriptService().printTranscript(student.getTranscript());

        Researcher researcher = system.findResearcherByUser(teacher);
        if (researcher != null) {
            System.out.println("\nResearch papers sorted by citations:");
            researcher.printPapers(new ResearchPaperByCitationsComparator());
        }
        System.out.println(system.generateReport(new ResearchSummaryReportStrategy()));
    }
}
