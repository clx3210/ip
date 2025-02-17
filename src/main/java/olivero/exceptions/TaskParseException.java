package olivero.exceptions;

/**
 * Signals an error when parsing raw task data.
 */
public class TaskParseException extends Exception {

    public TaskParseException(String message) {
        super(message);
    }
}
