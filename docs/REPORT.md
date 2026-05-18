# Project Report

## Project Description

The project is a console-based information system for a research-oriented university. It models academic registration, user roles, mark management, transcript generation, research activity, reports and data serialization.

## Team Scope Covered in Code

- Admin operations for users, courses and logs.
- Manager operations for approvals, teacher assignment, news and statistics.
- Teacher operations for viewing courses and putting marks.
- Student operations for course registration, marks, transcript, teacher info and teacher rating.
- Researcher operations for papers, projects, sorting and top cited analytics.
- Support models for messages, complaints, requests, news, clubs and student council.

## Main Classes

- `User`: common account data and authentication helper.
- `Employee`: base class for university employees.
- `Admin`: user/course/log administration.
- `Manager`: academic office workflows.
- `Teacher`: course assignment and grading.
- `Student`: bachelor student registration, transcript and supervisor.
- `Course`: course data, lessons, instructors and seat reservation.
- `Enrollment`: course registration state.
- `Mark`: first attestation, second attestation and final exam.
- `Lesson`: lesson time, room and type.
- `Researcher`: research role for any user.
- `ResearchPaper`: publication data.
- `ResearchProject`: participants and project papers.
- `UniversitySystem`: central facade for workflows.
- `DataStorage`: serialization storage.

## OOP Techniques

- Inheritance is used for users and employees.
- Abstract classes are used for `User`, `Employee` and `Club`.
- Encapsulation is used through private fields and public methods.
- Polymorphism is used through role-specific subclasses and report strategies.
- Collections are used for users, courses, enrollments, instructors, papers and logs.
- Comparators sort papers by date, citations and length.
- Custom exceptions enforce credit limit, fail limit, supervisor rule and research project participation.

## Design Patterns

- Factory: `UserFactory`.
- Singleton: `UniversitySystem`.
- Facade: `UniversitySystem`.
- Strategy: research comparators and report strategies.
- Storage utility/repository style: `DataStorage`.

## Demo Summary

`university.demo.DemoApp` demonstrates:

1. Login.
2. Adding users and courses.
3. Assigning a teacher to a course.
4. Student course registration.
5. Manager approval.
6. Teacher grading.
7. Transcript generation.
8. Research supervisor validation.
9. Research project membership validation.
10. Research paper sorting and top cited analytics.
11. Academic and research reports.
12. Serialization save.

## Known Limitations

The project provides a deterministic console demo instead of a full interactive menu. For the exam this is enough to show implemented parts quickly, but an interactive menu would be a natural next step.
