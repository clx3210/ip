package errors;

public class CommandParseException extends Exception {
    public CommandParseException(String message) {
        super(message);
    }

    public CommandParseException(String... messages) {
        super(String.join(System.lineSeparator(), messages));
    }
}
