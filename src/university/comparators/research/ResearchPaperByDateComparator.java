package university.comparators.research;

import java.util.Comparator;
import java.util.Date;

import university.model.research.ResearchPaper;

public class ResearchPaperByDateComparator implements Comparator<ResearchPaper> {
    @Override
    public int compare(ResearchPaper first, ResearchPaper second) {
        Date firstDate = first.getPublicationDate();
        Date secondDate = second.getPublicationDate();

        if (firstDate == null && secondDate == null) {
            return 0;
        }
        if (firstDate == null) {
            return 1;
        }
        if (secondDate == null) {
            return -1;
        }
        return secondDate.compareTo(firstDate);
    }
}
