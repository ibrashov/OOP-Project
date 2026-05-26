# Project Report

## Research-Oriented University Information System

**Course:** Object-Oriented Programming  
**Project type:** Console-based Java application  
**Team members:** _Add full names and surnames here_  
**Team leader:** _Add full name here_  
**Submission date:** _Add date here_  

---

## 1. Introduction

This project is a console-based information system for a research-oriented university. The system models the main academic, administrative, and research processes of a university: user authentication, course registration, mark management, transcript generation, manager approval, user administration, research paper management, research projects, and report generation.

The project was developed according to the OOP course requirements. It includes required domain classes such as `User`, `Employee`, `Teacher`, `Manager`, `Student`, `Admin`, `Course`, `Mark`, `Lesson`, `Researcher`, `ResearchPaper`, and `ResearchProject`. It also demonstrates inheritance, abstraction, encapsulation, polymorphism, enumerations, custom exceptions, collections, serialization, comparators, and design patterns.

The project is designed as a layered console application. Domain models are separated from services and infrastructure classes. The main entry point is `university.main.Main`, while `university.main.DemoApp` provides a fast automated demonstration of the main functionality.

---

## 2. Project Goals

The main goals of the project are:

- To model a research-oriented university using proper OOP design.
- To implement a working console system with authentication.
- To support course registration, approval, marks, and transcript generation.
- To support research activities such as research papers, research projects, researchers, paper sorting, and top-cited researcher search.
- To use custom exceptions for important academic and research rules.
- To use serialization for persistent data storage.
- To apply at least four design patterns.
- To keep the implementation consistent with Use Case and UML Class diagrams.

---

## 3. Requirements Coverage

| Requirement | Status | Implementation |
|---|---:|---|
| Required classes: `User`, `Employee`, `Teacher`, `Manager`, `Student`, `Admin`, `Course`, `Mark`, `Lesson`, `Researcher`, `ResearchPaper`, `ResearchProject` | Implemented | Packages `university.model.users`, `university.model.academic`, `university.model.research` |
| Lesson type: lecture/practice | Implemented | `LessonType` enum |
| Student is bachelor | Implemented | `DegreeType.BACHELOR`, default student factory creation |
| 4th year bachelor students have research supervisor | Implemented | `Student.setResearchSupervisor(...)` |
| Supervisor h-index must be at least 3 | Implemented | `InvalidSupervisorException` |
| More than one instructor per course | Implemented | `Course` uses `Set<Teacher> instructors` |
| Teachers and students can be researchers | Implemented | `UniversitySystem.createResearcherRole(...)` links any `User` to `Researcher` |
| Professors are always researchers | Implemented | `UniversitySystem.ensureProfessorResearchers()` |
| Research paper fields | Implemented | title, journal, doi, date, pages, citations, abstract, keywords, authors |
| Print papers sorted by comparator | Implemented | `Researcher.printPapers(Comparator<ResearchPaper>)` |
| Print all university papers sorted | Implemented | `UniversitySystem.printAllResearchPapers(...)` |
| Top cited researcher by school/year | Implemented | `Researcher.getTopCitedResearcherBySchool`, `getTopCitedResearcherByYear` |
| Research project participants must be researchers | Implemented | `NonResearcherJoinException` |
| Report generation about marks | Implemented | `AcademicPerformanceReportStrategy` |
| 4 or more design patterns | Implemented | Singleton, Facade, Factory, Strategy, Comparator |
| Comparable, comparators, equals, hashCode, toString | Implemented | `Course`, research comparators, user/research/support models |
| Proper serialization | Implemented | `DataStorage`, `UniversitySystem`, serializable models |
| Authentication | Implemented | `AuthenticationService` |
| Collections usage | Implemented | `List`, `Set`, `Map`, `LinkedHashSet`, streams |
| Documentation | Implemented | Generated Javadoc in `docs/` |
| UML consistency | Implemented | PUML diagrams in `diagrams/` |

Optional bonus features such as attendance, schedule generation, advanced regex search, startups, and recommendation letters are not part of the core implementation.

---

## 4. System Architecture

The project is organized into packages:

| Package | Purpose |
|---|---|
| `university.main` | Console entry points: `Main` and `DemoApp` |
| `university.model.users` | User hierarchy: admin, manager, teacher, student, employee |
| `university.model.academic` | Academic entities: course, lesson, enrollment, mark |
| `university.model.research` | Research entities: researcher, paper, project |
| `university.model.support` | Supporting entities: news, logs, messages, complaints, clubs, requests |
| `university.enums` | Enumerations for domain states and types |
| `university.exceptions` | Custom checked exceptions |
| `university.service` | Core services, storage, reports, authentication |
| `university.factory` | User creation factory |
| `university.comparators.research` | Research paper comparators |

The central class is `UniversitySystem`. It works as a facade over the whole application state: users, courses, enrollments, researchers, research projects, news, action logs, and requests.

```java
public class UniversitySystem implements Serializable {
    private static transient UniversitySystem instance;

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<Enrollment> enrollments = new ArrayList<>();
    private List<Researcher> researchers = new ArrayList<>();
    private List<ResearchProject> researchProjects = new ArrayList<>();
}
```

---

## 5. Use Case Diagram

The Use Case diagram is stored in:

```text
diagrams/FULL_USE_CASE_DIAGRAM.puml
```

The main actors are:

- `Guest`
- `User`
- `Admin`
- `Manager`
- `Teacher`
- `Student`
- `Employee`
- `Researcher`

The main use cases are:

- Login, logout, password change.
- Admin user and course management.
- Manager registration approval, teacher assignment, news, reports.
- Teacher course viewing, student info, mark assignment.
- Student course registration, dropping courses, transcript, marks, teacher rating, clubs.
- Researcher paper publishing, project participation, paper sorting, top-cited researcher search.
- Data saving and loading through serialization.

_Insert exported Use Case diagram image here in the final PDF report._

---

## 6. Class Diagram

The Class diagram is stored in:

```text
diagrams/FULL_CLASS_DIAGRAM.puml
```

The class diagram reflects:

- Inheritance between `User`, `Employee`, `Admin`, `Manager`, `Teacher`, and `Student`.
- Academic relationships between `Student`, `Course`, `Enrollment`, `Lesson`, and `Mark`.
- Research relationships between `Researcher`, `ResearchPaper`, and `ResearchProject`.
- Support models such as `News`, `Message`, `Complaint`, `Request`, `ActionLog`, and clubs.
- Services such as `UniversitySystem`, `AuthenticationService`, `DataStorage`, and report strategies.
- Enumerations and custom exceptions.

_Insert exported UML Class diagram image here in the final PDF report._

---

## 7. Main Classes Description

### 7.1 User

`User` is the abstract superclass for all system users. It stores common fields such as ID, full name, email, password hash, and active status. It also defines common behavior such as signing in, changing password, deleting account, equality, hash code, cloning, and `toString`.

Important fields:

- `id`
- `fullname`
- `email`
- `passwordHash`
- `isActive`

Important methods:

- `signIn(...)`
- `changePassword(...)`
- `deleteAccount()`
- `getRole()`

### 7.2 Employee

`Employee` extends `User` and is the superclass for `Admin`, `Manager`, and `Teacher`. It adds salary, employee ID, messages, complaints, and requests.

Important methods:

- `sendMessage(...)`
- `sendComplaint(...)`
- `createRequest(...)`

### 7.3 Admin

`Admin` is responsible for administrative operations. It can add, update, and deactivate users, add courses, and view action logs.

Implemented operations:

- Add users.
- Deactivate users.
- Update user information.
- Add courses.
- View logs.
- View reports through `UniversitySystem`.

### 7.4 Manager

`Manager` is responsible for academic administration. It can approve registrations, assign teachers to courses, manage news, view sorted student/teacher information, and generate statistical reports.

Important methods:

- `approveRegistration(...)`
- `approveRegistrations()`
- `assignTeacher(...)`
- `generateStatisticalReport()`
- `manageNews(...)`
- `viewStudentsSortedByGpa()`
- `viewTeachersAlphabetically()`

### 7.5 Teacher

`Teacher` represents academic staff. It can view assigned courses, manage course assignments, view student information, and put marks.

Important fields:

- `teacherId`
- `title`
- `courses`

Important methods:

- `viewCourses()`
- `assignCourse(...)`
- `removeCourse(...)`
- `gradeStudent(...)`
- `viewStudentInfo(...)`

Professors are automatically registered as researchers by `UniversitySystem`.

### 7.6 Student

`Student` represents university students. It supports course registration, dropping courses, viewing marks, viewing transcript, rating teachers, joining clubs, and assigning a research supervisor.

Important fields:

- `studentId`
- `major`
- `yearOfStudy`
- `gpa`
- `totalCredits`
- `failedCoursesCnt`
- `researchSupervisor`
- `enrollments`
- `teacherRatings`

Important business rules:

- A student cannot register for more than 21 credits.
- A student cannot fail more than 3 times.
- Only 4th year bachelor students can have a research supervisor.
- Research supervisor must have h-index at least 3.

Code fragment:

```java
public void setResearchSupervisor(Researcher researchSupervisor)
        throws InvalidSupervisorException {
    if (degreeType != DegreeType.BACHELOR || yearOfStudy != 4) {
        throw new InvalidSupervisorException(
                "Only 4th year bachelor students can have research supervisors.");
    }
    if (researchSupervisor.getHIndex() < 3) {
        throw new InvalidSupervisorException(
                "Supervisor must have h-index of at least 3.");
    }
    this.researchSupervisor = researchSupervisor;
}
```

### 7.7 Course

`Course` models an academic course. It supports multiple instructors, lessons, student seat reservation, and comparison by course code.

Important fields:

- `courseCode`
- `title`
- `credits`
- `yearOfStudy`
- `major`
- `maxStudents`
- `reservedStudentsCount`
- `lessons`
- `instructors`

Code fragment:

```java
public class Course implements Comparable<Course>, Serializable {
    private Set<Teacher> instructors = new LinkedHashSet<>();

    public boolean hasAvailableSeat() {
        return reservedStudentsCount < maxStudents;
    }

    public void reserveSeat() {
        if (!hasAvailableSeat()) {
            throw new IllegalStateException("No available seats");
        }
        reservedStudentsCount++;
    }
}
```

### 7.8 Enrollment

`Enrollment` connects a student and a course. It stores registration time, semester, status, and mark.

Statuses are represented by `RegistrationStatus`:

- `PENDING`
- `APPROVED`
- `REJECTED`
- `DROPPED`

### 7.9 Mark

`Mark` consists of first attestation, second attestation, and final exam. It calculates total points, letter grade, and pass/fail status.

Important methods:

- `getTotal()`
- `getLetterGrade()`
- `isPassed()`

### 7.10 Lesson

`Lesson` represents a lecture or practice lesson. Lesson type is stored using `LessonType`.

### 7.11 Researcher

`Researcher` represents the research role. It is linked to a `User`, which means teachers, students, and employees can become researchers.

Important fields:

- `researcherId`
- `user`
- `hIndex`
- `school`
- `researchArea`
- `projects`
- `papers`

Important methods:

- `publishPaper(...)`
- `joinProject(...)`
- `printPapers(...)`
- `calculateHIndex()`
- `getTotalCitations()`
- `getTopCitedResearcherBySchool(...)`
- `getTopCitedResearcherByYear(...)`

Code fragment:

```java
public void printPapers(Comparator<ResearchPaper> comparator) {
    List<ResearchPaper> sortedPapers = new ArrayList<>(papers);
    if (comparator != null) {
        sortedPapers.sort(comparator);
    }
    for (ResearchPaper paper : sortedPapers) {
        System.out.println(paper);
    }
}
```

### 7.12 ResearchPaper

`ResearchPaper` stores important publication information:

- `paperId`
- `title`
- `journal`
- `doi`
- `publicationDate`
- `startPage`
- `endPage`
- `citations`
- `abstractText`
- `keywords`
- `authors`

The paper length is calculated using page numbers.

### 7.13 ResearchProject

`ResearchProject` stores topic, status, supervisor, participants, and published papers. It throws `NonResearcherJoinException` if a non-researcher tries to join the project.

Code fragment:

```java
public void addParticipant(Object participant)
        throws NonResearcherJoinException {
    if (!(participant instanceof Researcher)) {
        throw new NonResearcherJoinException(
                "Only researchers can join a research project");
    }
    Researcher researcher = (Researcher) participant;
    participants.add(researcher);
}
```

---

## 8. Enumerations

The project uses enums for fixed domain values:

| Enum | Values |
|---|---|
| `TeacherTitle` | `TUTOR`, `LECTURER`, `SENIOR_LECTURER`, `PROFESSOR` |
| `ManagerType` | `OR`, `DEPARTMENT`, `DEAN` |
| `LessonType` | `LECTURE`, `PRACTICE` |
| `DegreeType` | `BACHELOR`, `MASTER`, `PHD` |
| `RegistrationStatus` | `PENDING`, `APPROVED`, `REJECTED`, `DROPPED` |
| `CouncilRole` | `PRESIDENT`, `VICE_PRESIDENT`, `TREASURER`, `SECRETARY`, `PR_MANAGER`, `MEMBER` |
| `RequestStatus` | `PENDING`, `APPROVED`, `REJECTED`, `SIGNED` |

Using enums improves type safety and avoids invalid string values.

---

## 9. Custom Exceptions

The project uses custom checked exceptions for important business rules.

| Exception | Purpose |
|---|---|
| `CreditLimitExceededException` | Thrown when a student tries to register for more than 21 credits |
| `FailLimitExceededException` | Thrown when a student exceeds allowed failed course count |
| `InvalidSupervisorException` | Thrown when supervisor assignment violates research rules |
| `NonResearcherJoinException` | Thrown when a non-researcher tries to join a research project |

These exceptions make rule violations explicit and easier to handle in console workflows.

---

## 10. Design Patterns

### 10.1 Singleton

Used in:

- `UniversitySystem`
- `AuthenticationService`
- `LogService`

Purpose: there must be one shared application state, one authentication session manager, and one logging service.

### 10.2 Facade

Used in:

- `UniversitySystem`

Purpose: `UniversitySystem` hides the complexity of many models and provides one high-level API for use cases such as registration, approval, mark assignment, research operations, reports, and storage.

### 10.3 Factory

Used in:

- `UserFactory`

Purpose: centralizes creation of `Student`, `Teacher`, `Manager`, and `Admin` objects and generates IDs.

### 10.4 Strategy

Used in:

- `ReportStrategy`
- `AcademicPerformanceReportStrategy`
- `ResearchSummaryReportStrategy`

Purpose: different reports can be generated using interchangeable strategies.

### 10.5 Comparator

Used in:

- `ResearchPaperByDateComparator`
- `ResearchPaperByCitationsComparator`
- `ResearchPaperByLengthComparator`

Purpose: research papers can be sorted by different criteria without changing `ResearchPaper` or `Researcher`.

---

## 11. Data Storage and Serialization

The project uses Java serialization for persistent data storage. `UniversitySystem` implements `Serializable` and stores all important application data:

- users
- courses
- enrollments
- researchers
- research projects
- news
- logs
- requests

`DataStorage` is responsible for saving and loading serialized objects.

```java
public class DataStorage {
    public void save(String path, Serializable data) {
        try (ObjectOutputStream output =
                     new ObjectOutputStream(new FileOutputStream(path))) {
            output.writeObject(data);
        }
    }

    public Object load(String path) {
        try (ObjectInputStream input =
                     new ObjectInputStream(new FileInputStream(path))) {
            return input.readObject();
        }
    }
}
```

The serialized data file is:

```text
university_system.ser
```

---

## 12. Authentication

All users access the system through authentication. The `AuthenticationService` checks credentials by email or ID and stores the current active user.

Demo accounts:

| Role | Email | Password |
|---|---|---|
| Admin | `admin1@kbtu.kz` | `admin123` |
| Manager | `manager1@kbtu.kz` | `manager123` |
| Teacher | `ada@kbtu.kz` | `teach123` |
| Student | `grace@kbtu.kz` | `student123` |

---

## 13. Console Demo

The main console application is:

```text
src/university/main/Main.java
```

Run commands:

```powershell
javac -encoding UTF-8 -d out '@sources.txt'
java -cp out university.main.Main
```

The automated demonstration class is:

```text
src/university/main/DemoApp.java
```

Run command:

```powershell
java -cp out university.main.DemoApp
```

The demo shows:

- academic report generation
- transcript generation
- research paper sorting
- research summary report

Example output:

```text
Academic report:
Students: 1
Courses: 2
Enrollments: 1
Graded enrollments: 1
Passed: 1
Failed: 0
Average total score: 90.00

CS101 Object-Oriented Programming | APPROVED | A- (90.0)
Calculated GPA: 3.67

Research papers sorted by citations:
ResearchPaper{paperId=1, title='Research-Oriented University Information Systems', ...}

Research report:
Researchers: 2
Research projects: 1
Research papers: 1
```

---

## 14. Important Workflows

### 14.1 Course Registration

1. Student logs in.
2. Student views available courses.
3. Student registers for a course.
4. System checks credit limit, duplicate registration, and available seats.
5. Enrollment is created with `PENDING` status.
6. Manager approves registration.
7. Enrollment status becomes `APPROVED`.

### 14.2 Putting Marks

1. Teacher logs in.
2. Teacher views assigned courses and students.
3. Teacher selects enrollment.
4. Teacher enters first attestation, second attestation, and final exam.
5. System saves the mark and updates academic report statistics.

### 14.3 Research Paper Publishing

1. User with researcher role opens research menu.
2. Researcher creates a paper.
3. System stores title, journal, DOI, date, pages, citations, abstract, and keywords.
4. Paper is linked to the researcher as author.
5. Papers can be printed sorted by date, citations, or length.

### 14.4 Research Project Participation

1. Researcher creates or selects research project.
2. Researcher joins project.
3. System verifies that participant is a `Researcher`.
4. If participant is not a researcher, `NonResearcherJoinException` is thrown.

---

## 15. Testing and Verification

The project was verified using compilation and console runs.

Compilation:

```powershell
javac -encoding UTF-8 -d out '@sources.txt'
```

Main application run:

```powershell
java -cp out university.main.Main
```

Automated demo:

```powershell
java -cp out university.main.DemoApp
```

Documentation generation:

```powershell
javadoc -encoding UTF-8 -charset UTF-8 -d docs '@sources.txt'
```

Verification results:

| Test | Result |
|---|---:|
| Project compilation | Passed |
| Main console application launch | Passed |
| Automated demo launch | Passed |
| Serialization file creation | Passed |
| Academic report output | Passed |
| Transcript output | Passed |
| Research paper sorting output | Passed |
| Javadoc generation | Passed with warnings about missing comments |

---

## 16. Documentation

HTML documentation was generated using Javadoc and is stored in:

```text
docs/index.html
```

The documentation includes packages and classes for models, services, enums, exceptions, comparators, factory, and main application.

---

## 17. Project Management

_This section must be personalized by the team._

Suggested content:

- Team member names and responsibilities.
- How tasks were divided.
- Which member implemented models, services, diagrams, documentation, presentation, and testing.
- Screenshots of Telegram or Teams chat.
- Short description of communication and decision-making process.
- Problems faced during implementation.
- How the team solved these problems.

Example:

| Team member | Responsibility |
|---|---|
| _Name 1_ | UML diagrams, use cases |
| _Name 2_ | User models, authentication |
| _Name 3_ | Academic module |
| _Name 4_ | Research module |
| _Name 5_ | Console demo and testing |
| _Name 6_ | Report and presentation |

---

## 18. Problems and Solutions

During development, the main problems were:

1. Designing the `Researcher` role.

   The requirement allowed several possible designs: interface, abstract class, decorator, or separate model. The project uses a separate `Researcher` model linked to `User`. This allows students, teachers, and other employees to become researchers without breaking the main inheritance hierarchy.

2. Keeping serialization consistent.

   Since the system stores complex object graphs, academic, research, support, and user models had to implement `Serializable`. The final solution uses `UniversitySystem` as the serializable aggregate root and `DataStorage` as the storage helper.

3. Supporting multiple instructors per course.

   The project uses `Set<Teacher>` in `Course` to avoid duplicate instructor assignments.

4. Enforcing academic rules.

   Credit limit, fail limit, supervisor h-index, and research project participation rules were implemented using custom exceptions and validation methods.

5. Keeping UML consistent with implementation.

   The final PUML diagrams were updated after implementation to reflect actual classes, methods, relationships, enums, exceptions, and patterns.

---

## 19. Limitations and Future Improvements

The system covers the main required functionality. Possible future improvements:

- Attendance tracking.
- Schedule generation with room load and room type.
- Advanced search by regular expressions.
- Recommendation letters.
- Startup/project module.
- More detailed user interface validation.
- Export reports to PDF or CSV.
- More unit tests.
- Password hashing instead of storing plain demo passwords.

---

## 20. Conclusion

The project implements a console-based research-oriented university information system using object-oriented programming principles. It contains required classes, inheritance, abstract classes, enums, custom exceptions, collections, serialization, comparators, and multiple design patterns.

The most important functionality is implemented:

- course registration
- registration approval
- putting marks
- transcript generation
- research paper management
- research project participation
- sorted research paper printing
- top-cited researcher search
- report generation

The project is consistent with the provided UML and Use Case diagrams and includes generated HTML documentation.

---

## Appendix A. Project Structure

```text
src/
  university/
    comparators/research/
    enums/
    exceptions/
    factory/
    main/
    model/
      academic/
      research/
      support/
      users/
    service/
diagrams/
  FULL_CLASS_DIAGRAM.puml
  FULL_USE_CASE_DIAGRAM.puml
docs/
  index.html
README.md
sources.txt
```

---

## Appendix B. Submission Checklist

| Item | Status |
|---|---:|
| Source code | Ready |
| Console demo | Ready |
| Use Case diagram | Ready, export image for PDF |
| Class diagram | Ready, export image for PDF |
| Javadoc HTML documentation | Ready |
| Report PDF | Convert this Markdown to PDF after personal edits |
| Presentation PDF | Needs 3-4 slides |
| Team member names | Must be added |
| Telegram/Teams chat screenshots | Must be added |
| Team leader private chat link | Must be sent separately |

