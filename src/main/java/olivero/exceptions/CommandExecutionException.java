package olivero.exceptions;

/**
 * Signals an error when executing a command.
 */
public class CommandExecutionException extends Exception {

    /**
     * Creates an exception object representing an exceptional command
     * execution.
     *
     * @param message Message to be potentially displayed to the user.
     */
    public CommandExecutionException(String message) {
        super(message);
    }

    /**
     * Creates an exception object representing an exceptional command
     * execution.
     *
     * @param messages List of messages to be displayed separated by line separators.
     */
    public CommandExecutionException(String... messages) {
        super(String.join(System.lineSeparator(), messages));
    }
}
