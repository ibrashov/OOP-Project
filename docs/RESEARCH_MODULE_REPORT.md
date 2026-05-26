# Research + Optional Module Report

## 1. Researcher.java

The `Researcher` class represents the research role in the university system. It is not a subclass of `Student` or `Teacher`; instead, it stores a reference to a `User`. This design is flexible because the project requirements say that students and teachers can be researchers, professors are always researchers, and even an employee who is not a teacher or student can also be a researcher.

The main fields are `researcherId`, `user`, `hIndex`, `school`, `researchArea`, `supervisor`, `projects`, and `papers`. The `user` field connects the researcher profile to a real university account. The `hIndex` field is used for supervisor validation. The `school` field is used when the system searches for the top cited researcher of a school. The `researchArea` field describes the research specialization.

`List<ResearchProject>` is used for projects because one researcher can participate in many research projects. `List<ResearchPaper>` is used for papers because one researcher can publish many papers. The methods check duplicates before adding objects, so the same project or paper is not stored twice.

The `setSupervisor()` method validates the supervisor rule. If the supervisor has h-index less than 3, the method throws `InvalidSupervisorException`. It also prevents a researcher from supervising themselves. This implements the project requirement about custom exceptions for invalid supervisors.

The `joinProject()` method adds a project to the researcher if the project is not null and not already present. This method is called from `ResearchProject.addParticipant()`, so the connection works in both directions: the project knows its participants, and the researcher knows their projects.

The `publishPaper()` method adds a research paper to the researcher's list of papers. It also calls `paper.addAuthor(this)`, which means the author list inside `ResearchPaper` is updated automatically. This keeps the relationship between researcher and paper consistent.

The `printPapers(Comparator<ResearchPaper> comparator)` method prints the researcher's papers in sorted order. It creates a copy of the paper list, sorts the copy using the given comparator, and prints each paper. The original list is not changed. If the comparator is null, papers are printed in their current order.

The methods `getTotalCitations()` and `getCitationsInYear(int year)` calculate research impact. Total citations are used for the top cited researcher by school. Citations in a specific year are used for the top cited researcher of the year among all schools.

The static method `getTopCitedResearcherBySchool()` receives a list of researchers and a school name. It loops through all researchers, filters by school, compares total citations, and returns the researcher with the highest citation count.

The static method `getTopCitedResearcherByYear()` works similarly, but it compares citations only from papers published in the selected year. This supports the requirement to print the top cited researcher of the year among all schools.

The static method `printAllPapers()` collects papers from all researchers and prints them sorted by the selected comparator. It uses `LinkedHashSet` first, so the same paper is not printed multiple times if it has several authors.

`Researcher` overrides `equals()` and `hashCode()` using `researcherId`. This means each researcher has a unique identity. It also implements `Serializable`, so researcher data can be saved together with the system.

## 2. ResearchPaper.java

The `ResearchPaper` class represents a scientific publication. It stores important paper fields such as `paperId`, `title`, `journal`, `doi`, `publicationDate`, `startPage`, `endPage`, `citations`, `abstractText`, `keywords`, and `authors`.

These fields were chosen according to the project requirement about research paper data. The paper has identification data (`paperId`, `doi`), publication data (`title`, `journal`, `publicationDate`), impact data (`citations`), size data (`startPage`, `endPage`), and author data (`authors`).

`List<Researcher>` is used for authors because one paper can have multiple authors. The `addAuthor()` method checks that the author is not null and is not already in the list. This prevents duplicate authors and keeps the author list clean.

The `getLength()` method calculates the number of pages in the paper. If `endPage` is smaller than `startPage`, the method returns 0. This avoids negative page length and protects the system from incorrect page data.

The `wasPublishedInYear(int year)` method checks whether the paper was published in a given year. It uses `Calendar` to extract the year from `publicationDate`. This method is used by `Researcher.getCitationsInYear()` when calculating the top cited researcher of a specific year.

`equals()` and `hashCode()` use `paperId`, because the paper ID is the unique identifier of a research paper. This is important when the system removes duplicate papers in `Researcher.printAllPapers()`.

The class implements `Serializable`, so research papers can be stored in files through the data storage module.

## 3. ResearchProject.java

The `ResearchProject` class represents a research project in the university. It has `projectId`, `topic`, `status`, `supervisor`, `participants`, and `papers`.

The `topic` field stores the project topic. The `status` field stores the current state of the project, for example active or completed. The `supervisor` field stores the researcher responsible for the project. The `participants` list stores researchers who joined the project. The `papers` list stores published papers connected to the project.

`List<Researcher>` is used for participants because a project can have many researchers. `List<ResearchPaper>` is used for papers because one project can produce many publications.

The method `addParticipant(Researcher researcher)` delegates to `addParticipant(Object participant)`. The object version is useful because it can check whether the passed participant is really a researcher. If someone who is not a `Researcher` tries to join the project, the method throws `NonResearcherJoinException`.

This exception implements the project requirement: only researchers can join research projects. It also demonstrates custom exception usage in the research module.

If the participant is a valid researcher and is not already in the list, the method adds them to the project. It also calls `researcher.joinProject(this)`, so the researcher's project list is updated too.

The `removeParticipant()` method removes a researcher from the project participants. The `addPaper()` method adds a research paper to the project if the paper is not null and not already present.

`equals()` and `hashCode()` use `projectId`, so each research project has a unique identity. The class implements `Serializable`, allowing project data to be saved.

## 4. Research Paper Comparators

The research module has three comparators:

- `ResearchPaperByDateComparator`
- `ResearchPaperByCitationsComparator`
- `ResearchPaperByLengthComparator`

These comparators implement the Strategy pattern. The same method `printPapers(Comparator c)` can print papers in different orders depending on which comparator is passed.

`ResearchPaperByDateComparator` sorts papers by publication date. Newer papers are printed first. It also handles null dates safely: papers without publication dates are placed after papers with dates.

`ResearchPaperByCitationsComparator` sorts papers by citation count in descending order. This is useful when the user wants to see the most influential papers first.

`ResearchPaperByLengthComparator` sorts papers by article length in descending order. It uses the `getLength()` method from `ResearchPaper`, so sorting depends on page count.

Using comparators is better than hardcoding sorting logic into one method. It keeps the code flexible and follows the project requirement to use comparators.

## 5. InvalidSupervisorException.java

`InvalidSupervisorException` is a custom checked exception. It is thrown when an invalid research supervisor is assigned.

The main rule is that a supervisor must have h-index at least 3. If the h-index is lower than 3, the system throws this exception. This rule is implemented in both `Researcher.setSupervisor()` and `Student.setResearchSupervisor()`.

This exception is important because the project specifically requires a custom exception for the supervisor rule.

## 6. NonResearcherJoinException.java

`NonResearcherJoinException` is another custom checked exception. It is used in `ResearchProject.addParticipant()`.

If a non-researcher object tries to join a research project, the system throws this exception. This protects the project from invalid participants and directly implements the project requirement that only researchers can join research projects.

## 7. Student Research Supervisor Connection

The `Student` class was connected to the research module through the `researchSupervisor` field. This field stores a `Researcher` object.

The method `setResearchSupervisor()` checks supervisor h-index. If the supervisor has h-index less than 3, `InvalidSupervisorException` is thrown. This means a weak supervisor cannot be assigned to a student.

The method `validateSupervisorRequirement()` checks the 4th year rule. If a student is in year 4 and has no research supervisor, the method throws `InvalidSupervisorException`. This supports the requirement that 4th year students must have a research supervisor.

This design keeps the research supervisor rule inside the student model while still using the `Researcher` class as the supervisor type.

## 8. Optional Club and Student Council

The optional support part includes `Club`, `StudentCouncil`, and `CouncilMembership`.

`Club` is an abstract class. It has `clubId`, `name`, `description`, `advisor`, and `members`. The `members` field is a `List<Student>` because a club can have many student members.

The `addMember()` method adds a student only if the student is not null and is not already in the list. The `removeMember()` method removes a student from the club.

`StudentCouncil` extends `Club`, so it reuses the common club fields and behavior. It adds `budget` and `memberships`. The `memberships` list stores not only students, but also their council roles.

The `assignRole()` method validates that both student and role are provided. It adds the student as a club member, then checks whether the student already has a membership. If membership exists, the role is updated. If not, a new `CouncilMembership` object is created.

`CouncilMembership` stores `startDate`, `endDate`, `student`, `council`, and `role`. This class connects a student to a student council with a specific role.

These optional classes show additional use of inheritance, collections, and object relationships.

## 9. OOP Concepts Used in This Part

Encapsulation is used because fields are private and accessed through methods. This protects internal data and allows validation in setters and add methods.

Collections are used in `Researcher`, `ResearchPaper`, `ResearchProject`, `Club`, and `StudentCouncil`. Lists store many projects, papers, authors, participants, members, and memberships.

Custom exceptions are used for important business rules. `InvalidSupervisorException` protects supervisor assignment. `NonResearcherJoinException` protects project participation.

Polymorphism and the Strategy pattern are used through `Comparator<ResearchPaper>`. The same printing method can use different sorting strategies.

Serialization is supported by research and optional classes, so these objects can be saved with the rest of the system.

## 10. Demo Coverage

The demo application shows the research module in action:

1. A professor is automatically registered as a researcher.
2. A 4th year student receives a valid research supervisor.
3. A weak supervisor with h-index less than 3 causes `InvalidSupervisorException`.
4. Researchers publish papers.
5. A research project accepts only researchers as participants.
6. A non-researcher participant causes `NonResearcherJoinException`.
7. Papers are printed by citations, date, and length.
8. The system prints the top cited researcher by school and by year.

This proves that the research requirements are implemented and can be shown during the exam.
