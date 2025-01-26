package olivero.exceptions;

/**
 * Signals an error when loading a stored object from disk.
 */
public class StorageLoadException extends Exception {

    /**
     * Identifies the reason for the enclosing exception.
     */
    public enum Reason {

        /** Enum value for missing data. */
        DATA_MISSING,

        /** Enum value for corrupted data. */
        DATA_CORRUPT
    }

    /** Cause of the current exception object. */
    private final Reason reason;

    /**
     * Constructs a throwable exception with the specified message and reason.
     *
     * @param message Message to specify more details of the exceptional event.
     * @param reason Cause of the exceptional event.
     */
    public StorageLoadException(String message, Reason reason) {
        super(message);
        this.reason = reason;
    }

    /**
     * Returns an enum {@code Reason} specifying the cause of the exception.
     *
     * @return The {@code Reason} enum.
     */
    public Reason getReason() {
        return reason;
    }

}
