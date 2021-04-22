package exceptions;

public class NoSuchOperation extends Exception{
    public static final String DEFAULT_EXCEPTION_MESSAGE = "Operation doesn't exist.";
    public NoSuchOperation(String message) {
        super(message);
    }

    public NoSuchOperation(String message, Throwable cause) {
        super(message, cause);
    }
}
