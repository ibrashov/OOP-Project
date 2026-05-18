# Research-Oriented University System

Console-based Java OOP project for a research-oriented university.

## Run

```powershell
javac -encoding UTF-8 -d out '@sources.txt'
java -cp out university.main.Main
```

Automated demo:

```powershell
java -cp out university.main.DemoApp
```

## Demo Accounts

- Admin: `admin1@kbtu.kz` / `admin123`
- Manager: `manager1@kbtu.kz` / `manager123`
- Teacher: `ada@kbtu.kz` / `teach123`
- Student: `grace@kbtu.kz` / `student123`

## Implemented Requirements

- Required models: users, academic models, research models, support models.
- Authentication for user actions.
- Course registration with 21 credit limit, seats, pending/approved statuses.
- Manager approval, course creation, teacher assignment, reports, news.
- Teacher courses, student info and marks.
- Student transcript, marks, teacher rating, clubs, supervisor assignment.
- Researcher role, papers, projects, comparators, top cited researcher.
- Serialization through `DataStorage` and `UniversitySystem`.
- Patterns: Singleton, Facade, Factory, Strategy, Comparator.

PUML diagrams are in `diagrams/`.
