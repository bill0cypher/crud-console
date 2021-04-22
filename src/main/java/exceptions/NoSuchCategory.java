package exceptions;

public class NoSuchCategory extends Exception{
    public static final String DEFAULT_MESSAGE_TEXT = "Option %1$s doesn't exist";

    public NoSuchCategory(String message) {
        super(message);
    }

    public NoSuchCategory(String message, Throwable cause) {
        super(message, cause);
    }
}
