package olivero.parsers.commands;

import olivero.commands.Command;
import olivero.exceptions.CommandParseException;

/**
 * Represents the base parser class for parsing raw command arguments into a subclass of {@code Command} object.
 */
public abstract class CommandParser<T extends Command> {

    /**
     * Parses the raw command arguments into an executable {@code Command} object,
     * and returns it.
     *
     * @param arguments The command arguments contained in the user input.
     * @return An executable {@code Command} object.
     * @throws CommandParseException If the provided arguments cannot be formatted
     *                               into any of the existing supported {@code Command} types or subtypes.
     */
    public abstract T parse(String arguments) throws CommandParseException;
}
