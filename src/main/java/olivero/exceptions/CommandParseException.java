package olivero.exceptions;

/**
 * Signals an error when parsing a raw command input.
 */
public class CommandParseException extends Exception {

    /**
     * Creates an exception object representing an exceptional command
     * parsing.
     *
     * @param message Message to be potentially displayed to the user.
     */
    public CommandParseException(String message) {
        super(message);
    }

    /**
     * Creates an exception object representing an exceptional command
     * parsing.
     *
     * @param messages List of messages to be displayed separated by line separators.
     */
    public CommandParseException(String... messages) {
        super(String.join(System.lineSeparator(), messages));
    }
}
