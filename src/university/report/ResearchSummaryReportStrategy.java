package university.report;

import university.model.research.Researcher;
import university.system.UniversitySystem;

public class ResearchSummaryReportStrategy implements ReportStrategy {
    @Override
    public String generate(UniversitySystem system) {
        int papers = 0;
        int citations = 0;
        for (Researcher researcher : system.getResearchers()) {
            papers += researcher.getPapers().size();
            citations += researcher.getTotalCitations();
        }
        return "Research report: researchers=" + system.getResearchers().size()
                + ", papers=" + papers
                + ", citations=" + citations;
    }
}
