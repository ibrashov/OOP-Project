# Research + Optional Module

This section describes the Research + Optional part of the university information system. This module covers researchers, research papers, research projects, research paper sorting, top cited researcher calculation, supervisor validation for 4th year students, and optional student club/council functionality.

## Researcher.java

The `Researcher` class represents the research role in the system. In this project, `Researcher` is designed as a separate class that stores a reference to `User`. This design is suitable for the requirement because not only teachers, but also students and other employees can be researchers. A professor is also connected to the research module as a researcher.

The main fields of this class are `researcherId`, `user`, `hIndex`, `school`, `researchArea`, `supervisor`, `projects`, and `papers`. The `researcherId` field identifies the researcher. The `user` field connects the researcher profile to an existing user account. The `hIndex` field is used for supervisor validation. The `school` field is used for finding the top cited researcher in a specific school. The `researchArea` field describes the specialization of the researcher.

The `projects` field stores research projects in which the researcher participates. The `papers` field stores research papers published by the researcher. Both fields are implemented as lists because one researcher can have many projects and many papers.

The `setSupervisor()` method contains validation logic. It checks that the supervisor is not the same researcher and that the supervisor has h-index at least 3. If the h-index is less than 3, the method throws `InvalidSupervisorException`. This implements the project requirement about invalid research supervisors.

The `joinProject()` method adds a project to the researcher if the project is not null and is not already stored in the list. This prevents duplicate project records.

The `publishPaper()` method adds a paper to the researcher's paper list. It also calls `paper.addAuthor(this)`, so the paper also receives the researcher as an author. This keeps the connection between `Researcher` and `ResearchPaper` consistent from both sides.

The `printPapers(Comparator<ResearchPaper> comparator)` method prints papers in sorted order. The sorting depends on the comparator that is passed to the method. For example, papers can be sorted by date, citations, or length. The method creates a copy of the papers list before sorting, so the original order of stored papers is not changed.

The `getTotalCitations()` method calculates the total number of citations for all papers of the researcher. This is used when finding the top cited researcher in a school.

The `getCitationsInYear(int year)` method calculates citations only for papers published in the selected year. This method supports the requirement to find the top cited researcher of a year among all schools.

The static method `getTopCitedResearcherBySchool()` receives a list of researchers and a school name. It filters researchers by school and returns the researcher with the highest total citation count.

The static method `getTopCitedResearcherByYear()` receives a list of researchers and a year. It compares researchers by citations from papers published in that year and returns the top researcher.

The static method `printAllPapers()` prints all papers of all researchers. It uses `LinkedHashSet` to avoid duplicate papers, because the same paper can belong to several authors. After that, it sorts papers with the given comparator and prints them.

The class overrides `equals()` and `hashCode()` using `researcherId`. This means two researcher objects with the same researcher ID are treated as the same researcher. The class also implements `Serializable`, so it can be saved with the rest of the system data.

## ResearchPaper.java

The `ResearchPaper` class represents a scientific paper. It stores information about publication identity, authorship, publication source, page length, date, and citation count.

The main fields are `paperId`, `title`, `journal`, `doi`, `publicationDate`, `startPage`, `endPage`, `citations`, `abstractText`, `keywords`, and `authors`. These fields match the project requirement to include important paper information such as name, authors, journal, pages, date, DOI, and citations.

The `authors` field is a list of `Researcher` objects because a research paper can have more than one author. The `addAuthor()` method checks that the author is not null and is not already in the list. This prevents duplicate authors.

The `getLength()` method returns the length of the paper using `endPage - startPage + 1`. If the ending page is smaller than the starting page, the method returns 0. This protects the system from invalid page data.

The `wasPublishedInYear(int year)` method checks whether the paper was published in a specific year. It uses `Calendar` to extract the year from `publicationDate`. This method is used in citation calculations by year.

The class overrides `equals()` and `hashCode()` using `paperId`. This is important because papers must not be duplicated when all papers of all researchers are printed.

The class implements `Serializable`, so research paper objects can be saved through file storage.

## ResearchProject.java

The `ResearchProject` class represents a university research project. It has a topic, status, supervisor, participants, and published papers.

The main fields are `projectId`, `topic`, `status`, `supervisor`, `participants`, and `papers`. The `projectId` identifies the project. The `topic` describes what the project is about. The `status` field can show whether the project is active or completed. The `supervisor` field stores the responsible researcher. The `participants` list stores researchers who joined the project. The `papers` list stores papers connected to this project.

The `addParticipant(Researcher researcher)` method adds a researcher to the project. Internally, it delegates to `addParticipant(Object participant)`. This second method checks the real type of the participant.

If the participant is not an instance of `Researcher`, the system throws `NonResearcherJoinException`. This directly implements the project requirement: if someone who is not a researcher tries to join a research project, a custom exception must be thrown.

If the participant is a valid researcher and is not already in the list, the method adds the researcher to `participants`. It also calls `researcher.joinProject(this)`, so the relationship is updated from both sides.

The `removeParticipant()` method removes a researcher from the project. The `addPaper()` method adds a research paper to the project if the paper is not null and not already added.

The class overrides `equals()` and `hashCode()` using `projectId`, because the project ID is the unique identifier of a project. The class also implements `Serializable`.

## Research Paper Comparators

The research module uses three comparator classes:

`ResearchPaperByDateComparator` sorts papers by publication date. Newer papers are placed before older papers. It also handles null dates safely.

`ResearchPaperByCitationsComparator` sorts papers by number of citations in descending order. This allows the system to show the most cited papers first.

`ResearchPaperByLengthComparator` sorts papers by paper length in descending order. It uses the `getLength()` method from `ResearchPaper`.

These comparators demonstrate the Strategy pattern. The same method, `printPapers(Comparator c)`, can use different sorting strategies without changing its own code.

## InvalidSupervisorException.java

`InvalidSupervisorException` is a custom checked exception. It is used when the supervisor rule is broken.

The rule says that a supervisor must have h-index at least 3. If a researcher with h-index less than 3 is assigned as a supervisor, this exception is thrown.

This exception is used in `Researcher.setSupervisor()` and `Student.setResearchSupervisor()`. Therefore, the rule is applied both for researcher supervisors and student research supervisors.

## NonResearcherJoinException.java

`NonResearcherJoinException` is a custom checked exception for research project participation.

It is thrown in `ResearchProject.addParticipant(Object participant)` when the provided participant is not a `Researcher`. This prevents invalid objects from joining a research project and keeps project data logically correct.

## Student Supervisor Connection

The `Student` class is connected to the research module through the `researchSupervisor` field. This field stores a `Researcher` object.

The method `setResearchSupervisor()` checks the h-index of the supervisor. If the supervisor has h-index less than 3, the method throws `InvalidSupervisorException`. This prevents weak supervisors from being assigned.

The method `validateSupervisorRequirement()` checks that a 4th year student has a research supervisor. If `yearOfStudy` is 4 and `researchSupervisor` is null, the method throws `InvalidSupervisorException`.

This implements the requirement that 4th year students must have a research supervisor who is a valid researcher.

## Optional Part: Club.java

The `Club` class is an abstract class for student organizations. It stores `clubId`, `name`, `description`, `advisor`, and `members`.

The `advisor` field is an `Employee`, because a university club can have an employee advisor. The `members` field is a list of `Student` objects because a club can have many student members.

The `addMember()` method checks that the student is not null and not already in the list. This prevents invalid members and duplicates. The `removeMember()` method removes a student from the member list.

The class implements `Serializable`, so club data can be stored with the system.

## Optional Part: StudentCouncil.java

`StudentCouncil` extends `Club`. This means it inherits common club fields and behavior, such as name, advisor, and members.

It adds two fields: `budget` and `memberships`. The `budget` field stores the council budget. The `memberships` field stores detailed membership records with student roles.

The `assignRole()` method assigns a council role to a student. It first checks that the student and role are not null. Then it adds the student as a club member. If the student already has a membership, the role is updated. If the student has no membership, a new `CouncilMembership` object is created.

This method connects students with council roles and avoids duplicate membership records.

## Optional Part: CouncilMembership.java

The `CouncilMembership` class connects a student with a student council and a role.

It stores `startDate`, `endDate`, `student`, `council`, and `role`. The `role` field uses the `CouncilRole` enum, which makes role values safer than plain strings.

This class is useful because membership is not only a student name. It also has dates and a role, so it deserves a separate object.

## OOP Concepts in This Module

Encapsulation is used in all classes because fields are private and accessed through getters, setters, and controlled methods.

Collections are used to represent one-to-many relationships: one researcher has many papers, one project has many participants, one paper has many authors, and one club has many members.

Custom exceptions are used to enforce business rules. `InvalidSupervisorException` protects supervisor assignment. `NonResearcherJoinException` protects research project membership.

Comparators demonstrate polymorphism and the Strategy pattern. Different comparator objects produce different sorting behavior for the same paper printing method.

Inheritance is used in the optional part: `StudentCouncil` extends `Club`.

Serialization is supported by research and optional classes, so these objects can be saved with the project data.

## Code Fragment Examples

Supervisor validation:

```java
if (researchSupervisor != null && researchSupervisor.getHIndex() < 3) {
    throw new InvalidSupervisorException("Research supervisor must have h-index of at least 3");
}
```

Adding a paper and connecting the author:

```java
if (paper != null && !papers.contains(paper)) {
    papers.add(paper);
    paper.addAuthor(this);
}
```

Checking project participants:

```java
if (!(participant instanceof Researcher)) {
    throw new NonResearcherJoinException("Only researchers can join a research project");
}
```

Printing sorted papers:

```java
List<ResearchPaper> sortedPapers = new ArrayList<>(papers);
sortedPapers.sort(comparator);
```

## What This Module Demonstrates in the Demo

During the demo, this module can show:

1. A 4th year student receives a valid research supervisor.
2. A supervisor with h-index less than 3 causes `InvalidSupervisorException`.
3. Researchers publish research papers.
4. Research papers are sorted by date, citations, and length.
5. The system finds the top cited researcher by school.
6. The system finds the top cited researcher by year.
7. A research project accepts researchers as participants.
8. A non-researcher cannot join a research project and causes `NonResearcherJoinException`.
9. Optional club/council classes show inheritance and student role assignment.

## Short Conclusion

The Research + Optional module satisfies the research requirements of the project. It supports researchers, papers, projects, custom exceptions, paper sorting, citation analytics, supervisor validation, and optional student council functionality. The implementation uses OOP principles, collections, comparators, custom exceptions, inheritance, and serialization.
