package exceptions;

public class AttributeNotFoundException extends Exception {
    public AttributeNotFoundException() { super(); }
    public AttributeNotFoundException(String message) {
        super(message);
    }
}
