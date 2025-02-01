package olivero.commands;

public class CommandResult {
    private final String message;
    private final boolean isExit;

    public CommandResult(String message, boolean isExit) {
        this.message = message;
        this.isExit = isExit;
    }

    public CommandResult(String message) {
        this(message, false);
    }

    public String getMessage() {
        return message;
    }

    public boolean isExit() {
        return isExit;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
