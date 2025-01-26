package olivero.exceptions;

/**
 * Signals an error in saving data to disk.
 */
public class StorageSaveException extends Exception {

    /**
     * Constructs a throwable exception.
     *
     * @param message Message to specify more details of the exceptional event.
     */
    public StorageSaveException(String message) {
        super(message);
    }
}
