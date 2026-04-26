package university.comparators.research;

import java.util.Comparator;

import university.model.research.ResearchPaper;

public class ResearchPaperByLengthComparator implements Comparator<ResearchPaper> {
    @Override
    public int compare(ResearchPaper first, ResearchPaper second) {
        return Integer.compare(second.getLength(), first.getLength());
    }
}
