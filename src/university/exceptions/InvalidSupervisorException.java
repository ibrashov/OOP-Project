package university.exceptions;

public class InvalidSupervisorException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidSupervisorException(String message) {
        super(message);
    }
}
