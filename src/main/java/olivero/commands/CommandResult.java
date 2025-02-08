package olivero.commands;

/**
 * Stores the execution result of a command.
 */
public class CommandResult {
    private final String message;
    private final boolean isExit;

    /**
     * Constructs a {@code CommandResult} object representing
     * the output of a command execution.
     * @param message The message to be shown to the user.
     * @param isExit True if the command is to exit the program, false otherwise.
     */
    public CommandResult(String message, boolean isExit) {
        this.message = message;
        this.isExit = isExit;
    }

    /**
     * Constructs a {@code CommandResult} object representing
     *
     * @param message The message to be shown to the user.
     */
    public CommandResult(String message) {
        this(message, false);
    }

    /**
     * Gets the message stored in the command result.
     * @return command execution result.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns true if the command execution is to exit the program.
     */
    public boolean isExit() {
        return isExit;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
