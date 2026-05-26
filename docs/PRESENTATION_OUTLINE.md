# Presentation Outline

## Slide 1 - Project Scope

- Research-oriented university information system.
- Main roles: Admin, Manager, Teacher, Student, Researcher.
- Core modules: academic registration, marks/transcript, research, support, storage.

## Slide 2 - Architecture

- OOP hierarchy: `User -> Employee -> Admin/Manager/Teacher`, `User -> Student`.
- `Researcher` wraps any `User`, so students, teachers and employees can be researchers.
- `UniversitySystem` is the singleton facade for workflows.
- Patterns: Factory, Singleton, Facade, Strategy, Storage utility.

## Slide 3 - Demo Scenarios

- Login.
- Course creation and teacher assignment.
- Student registration and manager approval.
- Teacher puts mark and transcript is printed.
- Research project, paper sorting, top cited researcher.
- Exceptions for weak supervisor and non-researcher project participant.

## Slide 4 - What Works / Limitations

- Works: model classes, collections, exceptions, comparators, reports, serialization demo.
- Limitations: console demo is deterministic, not a full interactive menu.
- Next improvement: add persistent interactive UI and generated PDF report.
