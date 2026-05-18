# Prompt for Generating Final Research Report Text

Copy this prompt into ChatGPT if you want it to rewrite the Research + Optional report in a more polished style.

```text
Write a university OOP project report section for my module: Research + Optional.

Use a clear academic style similar to a Java project report. Explain each class separately, including purpose, fields, collections, important methods, validation rules, custom exceptions, OOP concepts, and demo coverage.

My module includes these classes:

1. Researcher.java
- Represents the research role.
- Stores researcherId, User user, hIndex, school, researchArea, supervisor, List<ResearchProject> projects, List<ResearchPaper> papers.
- User reference is used because students, teachers and other employees can be researchers.
- setSupervisor() throws InvalidSupervisorException if supervisor h-index is less than 3 or if researcher tries to supervise themselves.
- joinProject() adds a project without duplicates.
- publishPaper() adds a paper without duplicates and also adds this researcher as author.
- printPapers(Comparator<ResearchPaper>) prints sorted papers.
- getTotalCitations() calculates all citations.
- getCitationsInYear(year) calculates citations from papers published in that year.
- getTopCitedResearcherBySchool(list, school) returns top cited researcher in a school.
- getTopCitedResearcherByYear(list, year) returns top cited researcher for a year.
- printAllPapers(list, comparator) prints all papers of all researchers and avoids duplicates with LinkedHashSet.
- equals/hashCode use researcherId.
- Implements Serializable.

2. ResearchPaper.java
- Represents a scientific paper.
- Stores paperId, title, journal, doi, publicationDate, startPage, endPage, citations, abstractText, keywords, List<Researcher> authors.
- addAuthor() adds authors without duplicates.
- getLength() calculates number of pages.
- wasPublishedInYear(year) checks publication year.
- equals/hashCode use paperId.
- Implements Serializable.

3. ResearchProject.java
- Represents a research project.
- Stores projectId, topic, status, supervisor, List<Researcher> participants, List<ResearchPaper> papers.
- addParticipant(Object) checks if participant is Researcher.
- If not Researcher, throws NonResearcherJoinException.
- addPaper() adds papers without duplicates.
- equals/hashCode use projectId.
- Implements Serializable.

4. Comparators
- ResearchPaperByDateComparator sorts by publication date.
- ResearchPaperByCitationsComparator sorts by citations.
- ResearchPaperByLengthComparator sorts by page length.
- Explain that this is Strategy pattern.

5. Exceptions
- InvalidSupervisorException for h-index < 3 supervisor.
- NonResearcherJoinException for non-researcher joining a research project.

6. Student supervisor connection
- Student has Researcher researchSupervisor.
- setResearchSupervisor() checks h-index.
- validateSupervisorRequirement() throws InvalidSupervisorException if yearOfStudy == 4 and supervisor is missing.

7. Optional part
- Club is abstract, has clubId, name, description, advisor, List<Student> members.
- addMember() prevents duplicates, removeMember() removes members.
- StudentCouncil extends Club, has budget and List<CouncilMembership>.
- assignRole() adds member and creates/updates membership.
- CouncilMembership stores startDate, endDate, student, council, role.

Also include:
- OOP concepts used: encapsulation, inheritance, collections, custom exceptions, comparator strategy, serialization.
- A short conclusion.
- Keep the text ready to paste into a shared project report.
```
