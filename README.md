# OOP-Project

Console-based information system for a research-oriented university.

## Implemented functionality

- Authentication by user id and password.
- User hierarchy: `User`, `Employee`, `Admin`, `Manager`, `Teacher`, `Student`.
- Course registration with 21 credit limit, fail limit, seat reservation, manager approval.
- Multiple instructors per course.
- Teacher grading and transcript/GPA generation.
- Research module: researchers, papers, projects, paper sorting, top cited researcher by school/year.
- 4th year student research supervisor rule with `InvalidSupervisorException`.
- Research project participant validation with `NonResearcherJoinException`.
- Admin user/course/log operations.
- Manager registration approval, teacher assignment, news and reports.
- Support models for messages, complaints, requests, clubs and student council.
- Serialization storage through `DataStorage`.

## Design patterns used

- Factory: `UserFactory`.
- Singleton: `UniversitySystem`.
- Facade: `UniversitySystem` centralizes workflows.
- Strategy: research comparators and report strategies.
- Repository/Storage utility: `DataStorage` for serialization.

## How to run demo

```powershell
javac -d out $(Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName })
java -cp out university.demo.DemoApp
```

The demo covers login, registration, approval, grading, transcript, research project, sorting papers, top cited researchers, exceptions and saving demo data.

## Exam materials

- Documentation: `docs/PROJECT_DOCUMENTATION.html`
- Use cases: `docs/USE_CASES.md`
- Class diagram source: `docs/CLASS_DIAGRAM.puml`
- Presentation outline: `docs/PRESENTATION_OUTLINE.md`
