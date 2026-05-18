package university.report;

import university.system.UniversitySystem;

public class AcademicPerformanceReportStrategy implements ReportStrategy {
    @Override
    public String generate(UniversitySystem system) {
        return system.generateAcademicReport();
    }
}
