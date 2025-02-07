package olivero.parsers.commands;

import olivero.commands.ByeCommand;
import olivero.exceptions.CommandParseException;

/**
 * Represents the parser for parsing command arguments into a {@link ByeCommand} object.
 */
public class ByeCommandParser extends CommandParser<ByeCommand> {

    @Override
    public ByeCommand parse(String arguments) throws CommandParseException {
        if (!arguments.isBlank()) {
            throw new CommandParseException(
                    ByeCommand.MESSAGE_INVALID_FORMAT,
                    ByeCommand.MESSAGE_USAGE);
        }
        return new ByeCommand();
    }
}
