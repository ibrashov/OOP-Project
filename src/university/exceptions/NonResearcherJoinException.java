package university.exceptions;

public class NonResearcherJoinException extends Exception {
    private static final long serialVersionUID = 1L;

    public NonResearcherJoinException(String message) {
        super(message);
    }
}
