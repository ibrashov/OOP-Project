# Defense Roles and Code Ownership

This file maps team members to the parts of the code they should defend during the project presentation.

## Shared Files

These files connect several modules, so they should not be defended by only one person:

| File | Who should know it | Why |
|---|---|---|
| `src/university/main/Main.java` | Everyone, by menu section | Main console demo and role-based menus |
| `src/university/main/DemoApp.java` | Everyone | Fast automated demo of academic + research functionality |
| `src/university/service/UniversitySystem.java` | Split by module | Central Singleton/Facade; contains user, academic, manager, research logic |
| `diagrams/FULL_CLASS_DIAGRAM.puml` | Everyone | Each participant explains their package/classes |
| `diagrams/FULL_USE_CASE_DIAGRAM.puml` | Everyone | Each participant explains their actor/use cases |

## Participant 1 — Users + Auth + Base Hierarchy

### Main Responsibility

Explains the user hierarchy, inheritance, authentication, factory pattern, and common object methods.

### Files to Defend

| File | What to explain |
|---|---|
| `src/university/model/users/User.java` | Base abstract class, common fields, `getRole()`, `equals`, `hashCode`, `toString`, `clone` |
| `src/university/model/users/Employee.java` | Why `Employee` is between `User` and staff roles; salary, employee ID, messages/complaints/requests |
| `src/university/model/users/Student.java` | Student as a direct `User` subclass; basic student fields |
| `src/university/model/users/Teacher.java` | Teacher as `Employee`; teacher title, assigned courses |
| `src/university/model/users/Manager.java` | Manager as `Employee`; manager type |
| `src/university/model/users/Admin.java` | Admin as `Employee`; user management methods |
| `src/university/enums/TeacherTitle.java` | Teacher title enum, especially `PROFESSOR` |
| `src/university/enums/ManagerType.java` | Manager type enum: `OR`, `DEPARTMENT`, `DEAN` |
| `src/university/service/AuthenticationService.java` | Singleton authentication, current user, login/logout |
| `src/university/factory/UserFactory.java` | Factory pattern, user creation, ID generation |

### UniversitySystem Methods to Know

In `src/university/service/UniversitySystem.java`:

- `getInstance()`
- `addUser(...)`
- `removeUser(...)`
- `updateUser(...)`
- `findById(...)`
- `findByEmail(...)`
- `getAllUsers()`
- `getAllStudents()`
- `getAllTeachers()`
- `getAllManagers()`

### What to Say on Defense

- `User` is abstract because every real account has a specific role.
- `Employee` exists because admin, manager, and teacher share employee properties and communication features.
- `Student` does not extend `Employee`, because students are not university staff.
- Authentication is separated into `AuthenticationService`, so login state is not mixed with model classes.
- `UserFactory` centralizes creation of users and demonstrates Factory Pattern.

### Demo Actions

- Login as admin/teacher/student.
- Show user creation through admin menu.
- Explain generated ID format.

---

## Participant 2 — Academic Module

### Main Responsibility

Explains course registration, enrollments, marks, transcript, credit limit, fail limit, lesson types, and multiple instructors.

### Files to Defend

| File | What to explain |
|---|---|
| `src/university/model/academic/Course.java` | Course fields, credits, lessons, multiple instructors, seat reservation, `Comparable` |
| `src/university/model/academic/Lesson.java` | Lesson topic, room, date/time, lecture/practice |
| `src/university/model/academic/Enrollment.java` | Link between student and course; registration status; mark ownership |
| `src/university/model/academic/Mark.java` | First attestation, second attestation, final exam, total, letter grade |
| `src/university/enums/LessonType.java` | `LECTURE`, `PRACTICE` |
| `src/university/enums/RegistrationStatus.java` | `PENDING`, `APPROVED`, `REJECTED`, `DROPPED` |
| `src/university/service/TranscriptService.java` | Transcript printing and GPA conversion |
| `src/university/exceptions/CreditLimitExceededException.java` | 21-credit rule |
| `src/university/exceptions/FailLimitExceededException.java` | Fail limit rule |

### Student Methods to Know

In `src/university/model/users/Student.java`:

- `registerForCourse(...)`
- `dropCourse(...)`
- `hasActiveEnrollment(...)`
- `findEnrollment(...)`
- `getActiveCourses()`
- `viewMarks()`
- `getTranscript()`
- `incrementFailedCourses()`

### UniversitySystem Methods to Know

In `src/university/service/UniversitySystem.java`:

- `addCourse(...)`
- `registerStudentForCourse(...)`
- `approveEnrollment(...)`
- `rejectEnrollment(...)`
- `approveAllPendingEnrollments(...)`
- `putMark(...)`
- `findCourseByCode(...)`
- `findEnrollmentById(...)`
- `getCourses()`
- `getEnrollments()`
- `getPendingEnrollments()`

### What to Say on Defense

- `Enrollment` is needed because registration is not just a relation between student and course; it has status, registration time, semester, and mark.
- `Mark` belongs to `Enrollment`, not directly to `Student`, because a student receives marks per course registration.
- Course registration creates `PENDING` enrollment; manager changes it to `APPROVED`.
- `Course` supports multiple instructors by storing `Set<Teacher>`.
- `Course` implements `Comparable<Course>` to sort courses by code.
- `CreditLimitExceededException` protects the 21-credit limit.
- `FailLimitExceededException` protects the fail-count rule.

### Demo Actions

- Student registers for course.
- Manager approves registration.
- Teacher puts marks.
- Student views transcript.

---

## Participant 3 — Manager/Admin/Support

### Main Responsibility

Explains admin and manager responsibilities, reports, logs, support communication, news, requests, and service logic.

### Files to Defend

| File | What to explain |
|---|---|
| `src/university/model/users/Admin.java` | Manage users, add courses, view logs, view reports |
| `src/university/model/users/Manager.java` | Approve registrations, assign teachers, reports, news, sorted views |
| `src/university/model/support/News.java` | News model |
| `src/university/model/support/ActionLog.java` | User/system action logs |
| `src/university/model/support/Message.java` | Employee-to-employee messages |
| `src/university/model/support/Complaint.java` | Employee complaints |
| `src/university/model/support/Request.java` | Employee requests and signing/approval statuses |
| `src/university/enums/RequestStatus.java` | `PENDING`, `APPROVED`, `REJECTED`, `SIGNED` |
| `src/university/service/LogService.java` | Singleton logging service |
| `src/university/service/ReportStrategy.java` | Report strategy interface |
| `src/university/service/AcademicPerformanceReportStrategy.java` | Academic performance report |
| `src/university/service/ResearchSummaryReportStrategy.java` | Research summary report |
| `src/university/service/DataStorage.java` | Save/load serialization helper |

### UniversitySystem Methods to Know

In `src/university/service/UniversitySystem.java`:

- `save()`
- `addUser(...)`
- `removeUser(...)`
- `updateUser(...)`
- `assignTeacherToCourse(...)`
- `approveEnrollment(...)`
- `approveAllPendingEnrollments(...)`
- `generateAcademicReport()`
- `generateReport(...)`
- `addNews(...)`
- `addRequest(...)`
- `addLog(...)`
- `getStudentsSortedByGpa()`
- `getTeachersAlphabetically()`
- `getNews()`
- `getActionLogs()`
- `getRequests()`

### What to Say on Defense

- Admin manages users and can view logs.
- Manager controls academic processes: approval, teacher assignment, reports, news.
- Support models represent real university communication: messages, complaints, requests.
- Requests can be signed, which models dean/rector confirmation.
- `ReportStrategy` allows different report types without changing `UniversitySystem`.
- `DataStorage` separates persistence logic from business logic.
- `UniversitySystem` is a Singleton and Facade.

### Demo Actions

- Admin adds/deactivates user.
- Manager approves pending registration.
- Manager assigns teacher to course.
- Manager generates academic report.
- Show logs/news/requests.

---

## Participant 4 — Research + Optional Student Activity

### Main Responsibility

Explains the research subsystem, researcher role, h-index rule, research papers, research projects, comparators, top-cited researcher logic, and optional student activities.

### Files to Defend

| File | What to explain |
|---|---|
| `src/university/model/research/Researcher.java` | Researcher as separate role/object linked to `User`; papers, projects, h-index |
| `src/university/model/research/ResearchPaper.java` | Paper fields: title, authors, journal, DOI, pages, date, citations |
| `src/university/model/research/ResearchProject.java` | Topic, participants, papers, non-researcher join exception |
| `src/university/comparators/research/ResearchPaperByDateComparator.java` | Sort papers by publication date |
| `src/university/comparators/research/ResearchPaperByCitationsComparator.java` | Sort papers by citations |
| `src/university/comparators/research/ResearchPaperByLengthComparator.java` | Sort papers by article length/pages |
| `src/university/exceptions/InvalidSupervisorException.java` | Supervisor h-index and 4th year bachelor rule |
| `src/university/exceptions/NonResearcherJoinException.java` | Only researchers can join research project |
| `src/university/model/support/Club.java` | Optional student activity base class |
| `src/university/model/support/StudentCouncil.java` | Optional student council |
| `src/university/model/support/CouncilMembership.java` | Student council membership |
| `src/university/enums/CouncilRole.java` | Council roles |

### Student Methods to Know

In `src/university/model/users/Student.java`:

- `setResearchSupervisor(...)`
- `validateSupervisorRequirement()`
- `addClub(...)`
- `removeClub(...)`
- `getUserClubs()`

### UniversitySystem Methods to Know

In `src/university/service/UniversitySystem.java`:

- `createResearcherRole(...)`
- `addResearcher(...)`
- `publishPaper(...)`
- `createResearchProject(...)`
- `printAllResearchPapers(...)`
- `getTopCitedResearcherBySchool(...)`
- `getTopCitedResearcherByYear(...)`
- `findResearcherByUser(...)`
- `findResearcherByUserId(...)`
- `getResearchers()`
- `getResearchProjects()`
- `getAllResearchPapers()`
- `ensureProfessorResearchers()`

### What to Say on Defense

- `Researcher` is a separate role/object, not a subclass of `Teacher` or `Student`, because different users can be researchers.
- Professors are automatically given researcher role by `UniversitySystem`.
- A 4th year bachelor student can have a supervisor only if the supervisor h-index is at least 3.
- `ResearchPaper` is an object with bibliographic data and citations.
- Comparators allow sorting papers by date, citations, and length.
- `ResearchProject.addParticipant(Object)` throws `NonResearcherJoinException` for non-researchers.
- Top-cited researcher is calculated by total citations or citations in a selected year.

### Demo Actions

- Open research menu.
- Create researcher role for a user.
- Publish paper.
- Print papers sorted by citations/date/length.
- Show top-cited researcher by school or year.
- Join research project.

---

## Recommended Defense Order

1. Participant 1 explains user hierarchy, authentication, and user creation.
2. Participant 2 explains academic registration, enrollment, marks, transcript.
3. Participant 3 explains admin/manager actions, reports, logs, support models.
4. Participant 4 explains research subsystem and optional student activity.
5. Team leader runs `DemoApp` or `Main` and connects all modules together.

## Quick Run Commands

```powershell
javac -encoding UTF-8 -d out '@sources.txt'
java -cp out university.main.Main
```

Fast demo:

```powershell
java -cp out university.main.DemoApp
```

