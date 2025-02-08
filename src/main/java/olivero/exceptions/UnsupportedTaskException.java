package olivero.exceptions;

/**
 * Signals an invalid conversion to a {@code TaskType} enum.
 */
public class UnsupportedTaskException extends RuntimeException {

    public UnsupportedTaskException(String message) {
        super(message);
    }
}
