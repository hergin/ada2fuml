package exceptions;

public class NamingException extends Exception {
    public NamingException(String message) {
        super(message);
    }
    public NamingException(String message, Throwable cause) {
        super(message, cause);
    }
}
