package university.exceptions;
public class FailLimitExceededException extends Exception {
    private static final long serialVersionUID = 1L;

    public FailLimitExceededException(String message) {
        super(message);
    }
}
