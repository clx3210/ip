package olivero.exceptions;

public class StorageLoadException extends Exception {
    public enum Reason {
        STORAGE_MISSING, STORAGE_CORRUPT
    }
    private final Reason reason;
    public StorageLoadException(String message, Reason reason) {
        super(message);
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }

}
