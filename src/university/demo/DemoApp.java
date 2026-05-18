package university.demo;

import university.comparators.research.ResearchPaperByCitationsComparator;
import university.comparators.research.ResearchPaperByDateComparator;
import university.comparators.research.ResearchPaperByLengthComparator;
import university.enums.ManagerType;
import university.enums.TeacherTitle;
import university.exceptions.InvalidSupervisorException;
import university.exceptions.NonResearcherJoinException;
import university.model.academic.Course;
import university.model.academic.Enrollment;
import university.model.academic.Mark;
import university.model.research.ResearchPaper;
import university.model.research.ResearchProject;
import university.model.research.Researcher;
import university.model.support.News;
import university.model.users.Admin;
import university.model.users.Manager;
import university.model.users.Student;
import university.model.users.Teacher;
import university.report.AcademicPerformanceReportStrategy;
import university.report.ReportStrategy;
import university.report.ResearchSummaryReportStrategy;
import university.service.AuthenticationService;
import university.service.TranscriptService;
import university.storage.DataStorage;
import university.system.UniversitySystem;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class DemoApp {
    public static void main(String[] args) throws Exception {
        UniversitySystem system = UniversitySystem.getInstance();
        system.clear();

        Admin admin = new Admin(1, "Admin User", "admin@uni.kz", "admin", true, 400000, "ADM-1");
        Manager manager = new Manager(2, "Office Manager", "manager@uni.kz", "manager", true, 350000, "MGR-1", ManagerType.OR);
        Teacher teacher = new Teacher(3, "Aigerim Lecturer", "teacher@uni.kz", "teacher", true, "T-1", TeacherTitle.LECTURER, 300000, "EMP-1");
        Teacher professor = new Teacher(4, "Research Professor", "prof@uni.kz", "prof", true, "T-2", TeacherTitle.PROFESSOR, 500000, "EMP-2");
        Student student = new Student(5, "Miras Student", "student@uni.kz", "student", true, "24B001", "Computer Science");
        student.setYearOfStudy(4);

        admin.addUser(admin);
        admin.addUser(manager);
        admin.addUser(teacher);
        admin.addUser(professor);
        admin.addUser(student);

        AuthenticationService.login(system.getUsers(), 5, "student");

        Course oop = new Course("OOP101", "Object-Oriented Programming", 5, 2, "Computer Science", 30);
        admin.addCourse(oop);
        manager.assignTeacher(oop, teacher);

        Enrollment enrollment = system.registerStudentForCourse(student, oop);
        manager.approveRegistration(enrollment);
        teacher.gradeStudent(enrollment, new Mark(28, 30, 35));

        student.viewTeacherInfo(oop);
        student.rateTeacher(teacher, 5);
        new TranscriptService().printTranscript(student.getTranscript());

        Researcher professorResearcher = system.getResearchers().get(0);
        professorResearcher.setSchool("SITE");
        professorResearcher.setResearchArea("Artificial Intelligence");
        professorResearcher.setHIndex(7);
        student.setResearchSupervisor(professorResearcher);
        student.validateSupervisorRequirement();

        Researcher studentResearcher = new Researcher(2, student, "SITE", "Education Technology");
        studentResearcher.setHIndex(3);
        system.addResearcher(studentResearcher);

        ResearchPaper aiPaper = new ResearchPaper(1, "AI in Education", "IEEE Access", "10.1000/ai");
        aiPaper.setPublicationDate(date(2026, Calendar.MARCH, 10));
        aiPaper.setStartPage(1);
        aiPaper.setEndPage(12);
        aiPaper.setCitations(18);

        ResearchPaper oopPaper = new ResearchPaper(2, "OOP Learning Analytics", "ACM Journal", "10.1000/oop");
        oopPaper.setPublicationDate(date(2025, Calendar.NOVEMBER, 5));
        oopPaper.setStartPage(20);
        oopPaper.setEndPage(28);
        oopPaper.setCitations(9);

        professorResearcher.publishPaper(aiPaper);
        studentResearcher.publishPaper(oopPaper);

        ResearchProject project = new ResearchProject(1, "Learning Analytics Platform", "ACTIVE");
        project.addParticipant(professorResearcher);
        project.addParticipant(studentResearcher);
        project.addPaper(aiPaper);
        project.addPaper(oopPaper);

        try {
            project.addParticipant(student);
        } catch (NonResearcherJoinException exception) {
            System.out.println("Expected project exception: " + exception.getMessage());
        }

        try {
            Researcher weakSupervisor = new Researcher(3, teacher, "SITE", "Programming");
            weakSupervisor.setHIndex(2);
            student.setResearchSupervisor(weakSupervisor);
        } catch (InvalidSupervisorException exception) {
            System.out.println("Expected supervisor exception: " + exception.getMessage());
        }

        System.out.println("Papers by citations:");
        system.printAllResearchPapers(new ResearchPaperByCitationsComparator());
        System.out.println("Papers by date:");
        system.printAllResearchPapers(new ResearchPaperByDateComparator());
        System.out.println("Papers by length:");
        system.printAllResearchPapers(new ResearchPaperByLengthComparator());
        System.out.println("Top cited SITE researcher: " + system.getTopCitedResearcherBySchool("SITE"));
        System.out.println("Top cited 2026 researcher: " + system.getTopCitedResearcherByYear(2026));

        manager.manageNews(new News(1, "Registration opened", "Course registration is available."));
        for (ReportStrategy report : Arrays.asList(new AcademicPerformanceReportStrategy(), new ResearchSummaryReportStrategy())) {
            System.out.println(report.generate(system));
        }

        DataStorage.save("university-demo.ser", system);
        System.out.println("Demo data saved to university-demo.ser");
    }

    private static Date date(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
