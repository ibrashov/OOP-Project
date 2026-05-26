package university.service;

public class ResearchSummaryReportStrategy implements ReportStrategy {
    private static final long serialVersionUID = 1L;

    @Override
    public String generate(UniversitySystem system) {
        return "Research report:\nResearchers: " + system.getResearchers().size()
                + "\nResearch projects: " + system.getResearchProjects().size()
                + "\nResearch papers: " + system.getAllResearchPapers().size();
    }
}
