package university.main;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import university.comparators.research.ResearchPaperByCitationsComparator;
import university.comparators.research.ResearchPaperByDateComparator;
import university.comparators.research.ResearchPaperByLengthComparator;
import university.enums.DegreeType;
import university.enums.ManagerType;
import university.enums.TeacherTitle;
import university.factory.UserFactory;
import university.model.academic.Course;
import university.model.academic.Enrollment;
import university.model.academic.Mark;
import university.model.research.ResearchPaper;
import university.model.research.ResearchProject;
import university.model.research.Researcher;
import university.model.support.News;
import university.model.support.Request;
import university.model.users.Admin;
import university.model.users.Employee;
import university.model.users.Manager;
import university.model.users.Student;
import university.model.users.Teacher;
import university.model.users.User;
import university.service.AuthenticationService;
import university.service.ResearchSummaryReportStrategy;
import university.service.TranscriptService;
import university.service.UniversitySystem;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UniversitySystem system = UniversitySystem.getInstance();
    private static final AuthenticationService auth = AuthenticationService.getInstance();
    private static final TranscriptService transcriptService = new TranscriptService();

    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println(" Research-Oriented University Management");
        System.out.println("=============================================");

        while (true) {
            User user = auth.getCurrentUser();
            if (user == null) {
                guestMenu();
            } else {
                userMenu(user);
            }
        }
    }

    private static void guestMenu() {
        System.out.println("\n--- Guest ---");
        System.out.println("1. Login");
        System.out.println("2. Show demo accounts");
        System.out.println("0. Exit");
        switch (readLine("Choose: ")) {
            case "1" -> login();
            case "2" -> showDemoAccounts();
            case "0" -> {
                system.save();
                System.out.println("Goodbye.");
                System.exit(0);
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void login() {
        String credential = readLine("Email or ID: ");
        String password = readLine("Password: ");
        if (auth.login(credential, password)) {
            User user = auth.getCurrentUser();
            system.addLog(user.getId(), "Logged in");
            System.out.println("Logged in as " + user.getFullname() + " [" + user.getRole() + "]");
        } else {
            System.out.println("Wrong credentials or inactive account.");
        }
    }

    private static void userMenu(User user) {
        System.out.println("\n--- " + user.getRole() + " | " + user.getFullname() + " ---");
        switch (user.getRole()) {
            case "ADMIN" -> adminMenu((Admin) user);
            case "MANAGER" -> managerMenu((Manager) user);
            case "TEACHER" -> teacherMenu((Teacher) user);
            case "STUDENT" -> studentMenu((Student) user);
            default -> commonMenu(user);
        }
    }

    private static void adminMenu(Admin admin) {
        System.out.println("1. Add student");
        System.out.println("2. Add teacher");
        System.out.println("3. Add manager");
        System.out.println("4. Update user");
        System.out.println("5. Deactivate user");
        System.out.println("6. Add course");
        System.out.println("7. Assign teacher to course");
        System.out.println("8. View action logs");
        System.out.println("9. View all users");
        System.out.println("10. Research menu");
        System.out.println("11. Change password");
        System.out.println("0. Logout");
        switch (readLine("Choose: ")) {
            case "1" -> addStudent();
            case "2" -> addTeacher();
            case "3" -> addManager();
            case "4" -> updateUser();
            case "5" -> deactivateUser();
            case "6" -> addCourse();
            case "7" -> assignTeacher(admin);
            case "8" -> system.getActionLogs().forEach(System.out::println);
            case "9" -> system.getAllUsers().forEach(System.out::println);
            case "10" -> researchMenu(admin);
            case "11" -> changePassword(admin);
            case "0" -> logout();
            default -> System.out.println("Invalid option.");
        }
    }

    private static void managerMenu(Manager manager) {
        System.out.println("1. View pending registrations");
        System.out.println("2. Approve one registration");
        System.out.println("3. Approve all registrations");
        System.out.println("4. Add course for registration");
        System.out.println("5. Assign teacher to course");
        System.out.println("6. Generate academic report");
        System.out.println("7. Manage news");
        System.out.println("8. View students sorted by GPA");
        System.out.println("9. View teachers alphabetically");
        System.out.println("10. View employee requests");
        System.out.println("11. Research summary");
        System.out.println("0. Logout");
        switch (readLine("Choose: ")) {
            case "1" -> printPendingEnrollments();
            case "2" -> approveOne(manager);
            case "3" -> system.approveAllPendingEnrollments(manager);
            case "4" -> addCourse();
            case "5" -> assignTeacher(manager);
            case "6" -> System.out.println(system.generateAcademicReport());
            case "7" -> manageNews();
            case "8" -> system.getStudentsSortedByGpa().forEach(System.out::println);
            case "9" -> system.getTeachersAlphabetically().forEach(System.out::println);
            case "10" -> system.getRequests().forEach(System.out::println);
            case "11" -> System.out.println(system.generateReport(new ResearchSummaryReportStrategy()));
            case "0" -> logout();
            default -> System.out.println("Invalid option.");
        }
    }

    private static void teacherMenu(Teacher teacher) {
        System.out.println("1. View assigned courses");
        System.out.println("2. View students in my courses");
        System.out.println("3. Put mark");
        System.out.println("4. Send message to employee");
        System.out.println("5. Send complaint");
        System.out.println("6. Research menu");
        System.out.println("0. Logout");
        switch (readLine("Choose: ")) {
            case "1" -> teacher.viewCourses();
            case "2" -> viewStudentsForTeacher(teacher);
            case "3" -> putMark(teacher);
            case "4" -> sendMessage(teacher);
            case "5" -> sendComplaint(teacher);
            case "6" -> researchMenu(teacher);
            case "0" -> logout();
            default -> System.out.println("Invalid option.");
        }
    }

    private static void studentMenu(Student student) {
        System.out.println("1. View courses");
        System.out.println("2. Register for course");
        System.out.println("3. Drop course");
        System.out.println("4. View marks");
        System.out.println("5. View transcript");
        System.out.println("6. View teacher info for course");
        System.out.println("7. Rate teacher");
        System.out.println("8. Join club");
        System.out.println("9. Assign research supervisor");
        System.out.println("10. Research menu");
        System.out.println("0. Logout");
        switch (readLine("Choose: ")) {
            case "1" -> system.getCourses().forEach(System.out::println);
            case "2" -> registerForCourse(student);
            case "3" -> dropCourse(student);
            case "4" -> student.viewMarks();
            case "5" -> transcriptService.printTranscript(student.getTranscript());
            case "6" -> viewTeacherInfo();
            case "7" -> rateTeacher(student);
            case "8" -> joinClub(student);
            case "9" -> assignSupervisor(student);
            case "10" -> researchMenu(student);
            case "0" -> logout();
            default -> System.out.println("Invalid option.");
        }
    }

    private static void commonMenu(User user) {
        System.out.println("1. Research menu");
        System.out.println("2. Change password");
        System.out.println("0. Logout");
        switch (readLine("Choose: ")) {
            case "1" -> researchMenu(user);
            case "2" -> changePassword(user);
            case "0" -> logout();
            default -> System.out.println("Invalid option.");
        }
    }

    private static void researchMenu(User user) {
        Researcher researcher = system.findResearcherByUser(user);
        if (researcher == null) {
            System.out.println("You do not have a researcher role yet.");
            if ("y".equalsIgnoreCase(readLine("Create researcher role? (y/n): "))) {
                researcher = system.createResearcherRole(user, readLine("School: "), readLine("Research area: "), readInt("h-index: "));
                system.save();
            } else {
                return;
            }
        }

        System.out.println("\n--- Research ---");
        System.out.println("1. Publish paper");
        System.out.println("2. Create project");
        System.out.println("3. Join project");
        System.out.println("4. Print my papers sorted");
        System.out.println("5. Print all papers sorted");
        System.out.println("6. Top cited researcher by school");
        System.out.println("7. Top cited researcher by year");
        System.out.println("0. Back");
        switch (readLine("Choose: ")) {
            case "1" -> publishPaper(researcher);
            case "2" -> system.createResearchProject(readLine("Topic: "), researcher);
            case "3" -> joinProject(researcher);
            case "4" -> researcher.printPapers(choosePaperComparator());
            case "5" -> system.printAllResearchPapers(choosePaperComparator());
            case "6" -> System.out.println(system.getTopCitedResearcherBySchool(readLine("School: ")));
            case "7" -> System.out.println(system.getTopCitedResearcherByYear(readInt("Year: ")));
            case "0" -> { }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void addStudent() {
        String name = readLine("Full name: ");
        String email = readLine("Email: ");
        String password = readLine("Password: ");
        Student student = UserFactory.createStudent(name, email, password, DegreeType.BACHELOR);
        student.setMajor(readLine("Major: "));
        student.setYearOfStudy(readInt("Year of study: "));
        System.out.println(system.addUser(student) ? "Student added: " + student.getId() : "Could not add student.");
    }

    private static void addTeacher() {
        String name = readLine("Full name: ");
        String email = readLine("Email: ");
        String password = readLine("Password: ");
        Teacher teacher = (Teacher) UserFactory.createUser("TEACHER", name, email, password);
        teacher.setTitle(chooseTeacherTitle());
        System.out.println(system.addUser(teacher) ? "Teacher added: " + teacher.getId() : "Could not add teacher.");
    }

    private static void addManager() {
        String name = readLine("Full name: ");
        String email = readLine("Email: ");
        String password = readLine("Password: ");
        Manager manager = (Manager) UserFactory.createUser("MANAGER", name, email, password);
        manager.setManagerType(chooseManagerType());
        System.out.println(system.addUser(manager) ? "Manager added: " + manager.getId() : "Could not add manager.");
    }

    private static void updateUser() {
        User user = system.findById(readLine("User ID: "));
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        String name = readLine("New full name (blank to keep): ");
        String email = readLine("New email (blank to keep): ");
        if (!name.isBlank()) user.setFullname(name);
        if (!email.isBlank()) user.setEmail(email);
        system.updateUser(user);
        System.out.println("User updated.");
    }

    private static void deactivateUser() {
        System.out.println(system.removeUser(readLine("User ID: ")) ? "User deactivated." : "User not found.");
    }

    private static void addCourse() {
        Course course = new Course(
                readLine("Course code: "),
                readLine("Title: "),
                readInt("Credits: "),
                readInt("Target year: "),
                readLine("Major: "),
                readInt("Max students: "));
        System.out.println(system.addCourse(course) ? "Course added." : "Course already exists.");
    }

    private static void assignTeacher(Manager manager) {
        Course course = chooseCourse();
        Teacher teacher = chooseTeacher();
        if (system.assignTeacherToCourse(manager, course, teacher)) {
            System.out.println("Teacher assigned.");
        } else {
            System.out.println("Could not assign teacher.");
        }
    }

    private static void assignTeacher(Admin admin) {
        Course course = chooseCourse();
        Teacher teacher = chooseTeacher();
        if (system.assignTeacherToCourse(admin, course, teacher)) {
            System.out.println("Teacher assigned.");
        } else {
            System.out.println("Could not assign teacher.");
        }
    }

    private static void registerForCourse(Student student) {
        Course course = chooseCourse();
        try {
            Enrollment enrollment = system.registerStudentForCourse(student, course);
            System.out.println("Registration created: " + enrollment);
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private static void dropCourse(Student student) {
        Course course = chooseCourse();
        student.dropCourse(course);
        system.save();
        System.out.println("Course dropped if it was active.");
    }

    private static void printPendingEnrollments() {
        List<Enrollment> pending = system.getPendingEnrollments();
        for (int i = 0; i < pending.size(); i++) {
            System.out.println((i + 1) + ". " + pending.get(i));
        }
        if (pending.isEmpty()) {
            System.out.println("No pending registrations.");
        }
    }

    private static void approveOne(Manager manager) {
        List<Enrollment> pending = system.getPendingEnrollments();
        if (pending.isEmpty()) {
            System.out.println("No pending registrations.");
            return;
        }
        printPendingEnrollments();
        int index = readInt("Registration number: ") - 1;
        if (index >= 0 && index < pending.size()) {
            system.approveEnrollment(manager, pending.get(index));
            System.out.println("Approved.");
        }
    }

    private static void putMark(Teacher teacher) {
        viewStudentsForTeacher(teacher);
        Student student = chooseStudent();
        Course course = chooseCourse();
        Enrollment enrollment = student == null ? null : student.findEnrollment(course);
        if (enrollment == null) {
            System.out.println("Enrollment not found.");
            return;
        }
        Mark mark = new Mark(readDouble("First attestation: "), readDouble("Second attestation: "), readDouble("Final exam: "));
        try {
            system.putMark(teacher, enrollment, mark);
            System.out.println("Mark saved.");
        } catch (Exception e) {
            System.out.println("Could not put mark: " + e.getMessage());
        }
    }

    private static void viewStudentsForTeacher(Teacher teacher) {
        for (Enrollment enrollment : system.getEnrollments()) {
            if (teacher.getCourses().contains(enrollment.getCourse())) {
                System.out.println(enrollment.getStudent() + " | " + enrollment.getCourse().getCourseCode());
            }
        }
    }

    private static void viewTeacherInfo() {
        Course course = chooseCourse();
        if (course != null) {
            course.getInstructors().forEach(System.out::println);
        }
    }

    private static void rateTeacher(Student student) {
        Teacher teacher = chooseTeacher();
        if (teacher == null) {
            return;
        }
        try {
            student.rateTeacher(teacher, readInt("Rating 1-5: "));
            system.save();
            System.out.println("Rating saved.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void joinClub(Student student) {
        for (int i = 0; i < system.getClubs().size(); i++) {
            System.out.println((i + 1) + ". " + system.getClubs().get(i));
        }
        int index = readInt("Club number: ") - 1;
        if (index >= 0 && index < system.getClubs().size()) {
            student.addClub(system.getClubs().get(index));
            system.save();
            System.out.println("Joined club.");
        }
    }

    private static void assignSupervisor(Student student) {
        printResearchers();
        Researcher supervisor = chooseResearcher();
        try {
            student.setResearchSupervisor(supervisor);
            system.save();
            System.out.println("Supervisor assigned.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void manageNews() {
        System.out.println("1. Add news");
        System.out.println("2. View news");
        switch (readLine("Choose: ")) {
            case "1" -> system.addNews(new News(system.getNextNewsId(), readLine("Title: "), readLine("Content: ")));
            case "2" -> system.getNews().forEach(System.out::println);
            default -> System.out.println("Invalid option.");
        }
    }

    private static void sendMessage(Employee sender) {
        Employee receiver = chooseEmployee();
        if (receiver == null) {
            return;
        }
        sender.sendMessage(system.getActionLogs().size() + 1, readLine("Message: "), receiver);
        system.save();
        System.out.println("Message sent.");
    }

    private static void sendComplaint(Employee sender) {
        sender.sendComplaint(system.getActionLogs().size() + 1, readLine("Complaint: "));
        system.addRequest(new Request(system.getActionLogs().size() + 1, "Complaint from " + sender.getFullname(), "See complaint list", sender));
        System.out.println("Complaint sent.");
    }

    private static void publishPaper(Researcher researcher) {
        ResearchPaper paper = new ResearchPaper(system.getNextPaperId(),
                readLine("Title: "),
                readLine("Journal: "),
                readLine("DOI: "));
        Calendar calendar = Calendar.getInstance();
        calendar.set(readInt("Publication year: "), readInt("Month 1-12: ") - 1, readInt("Day: "));
        paper.setPublicationDate(calendar.getTime());
        paper.setStartPage(readInt("Start page: "));
        paper.setEndPage(readInt("End page: "));
        paper.setCitations(readInt("Citations: "));
        paper.setAbstractText(readLine("Abstract: "));
        paper.setKeywords(readLine("Keywords: "));
        system.publishPaper(researcher, paper);
        System.out.println("Paper published.");
    }

    private static void joinProject(Researcher researcher) {
        List<ResearchProject> projects = system.getResearchProjects();
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i));
        }
        int index = readInt("Project number: ") - 1;
        if (index >= 0 && index < projects.size()) {
            try {
                projects.get(index).addParticipant(researcher);
                system.save();
                System.out.println("Joined project.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Comparator<ResearchPaper> choosePaperComparator() {
        System.out.println("1. By date");
        System.out.println("2. By citations");
        System.out.println("3. By length");
        return switch (readLine("Sort: ")) {
            case "2" -> new ResearchPaperByCitationsComparator();
            case "3" -> new ResearchPaperByLengthComparator();
            default -> new ResearchPaperByDateComparator();
        };
    }

    private static Course chooseCourse() {
        List<Course> courses = system.getCourses();
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i));
        }
        int index = readInt("Course number: ") - 1;
        return index >= 0 && index < courses.size() ? courses.get(index) : null;
    }

    private static Student chooseStudent() {
        List<Student> students = system.getAllStudents();
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i));
        }
        int index = readInt("Student number: ") - 1;
        return index >= 0 && index < students.size() ? students.get(index) : null;
    }

    private static Teacher chooseTeacher() {
        List<Teacher> teachers = system.getAllTeachers();
        for (int i = 0; i < teachers.size(); i++) {
            System.out.println((i + 1) + ". " + teachers.get(i));
        }
        int index = readInt("Teacher number: ") - 1;
        return index >= 0 && index < teachers.size() ? teachers.get(index) : null;
    }

    private static Employee chooseEmployee() {
        List<Employee> employees = system.getAllUsers().stream()
                .filter(Employee.class::isInstance)
                .map(Employee.class::cast)
                .toList();
        for (int i = 0; i < employees.size(); i++) {
            System.out.println((i + 1) + ". " + employees.get(i).getFullname() + " [" + employees.get(i).getRole() + "]");
        }
        int index = readInt("Employee number: ") - 1;
        return index >= 0 && index < employees.size() ? employees.get(index) : null;
    }

    private static void printResearchers() {
        for (int i = 0; i < system.getResearchers().size(); i++) {
            System.out.println((i + 1) + ". " + system.getResearchers().get(i));
        }
    }

    private static Researcher chooseResearcher() {
        List<Researcher> researchers = system.getResearchers();
        int index = readInt("Researcher number: ") - 1;
        return index >= 0 && index < researchers.size() ? researchers.get(index) : null;
    }

    private static TeacherTitle chooseTeacherTitle() {
        TeacherTitle[] titles = TeacherTitle.values();
        for (int i = 0; i < titles.length; i++) {
            System.out.println((i + 1) + ". " + titles[i]);
        }
        int index = readInt("Title number: ") - 1;
        return index >= 0 && index < titles.length ? titles[index] : TeacherTitle.LECTURER;
    }

    private static ManagerType chooseManagerType() {
        ManagerType[] types = ManagerType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }
        int index = readInt("Manager type number: ") - 1;
        return index >= 0 && index < types.length ? types[index] : ManagerType.OR;
    }

    private static void changePassword(User user) {
        user.changePassword(readLine("New password: "));
        system.save();
        System.out.println("Password changed.");
    }

    private static void logout() {
        User user = auth.getCurrentUser();
        if (user != null) {
            system.addLog(user.getId(), "Logged out");
        }
        auth.logout();
    }

    private static void showDemoAccounts() {
        System.out.println("Admin:   admin1@kbtu.kz / admin123");
        System.out.println("Manager: manager1@kbtu.kz / manager123");
        System.out.println("Teacher: ada@kbtu.kz / teach123");
        System.out.println("Student: grace@kbtu.kz / student123");
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Enter an integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Enter a number.");
            }
        }
    }
}
