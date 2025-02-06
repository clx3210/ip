package olivero.parsers.commands;

import olivero.commands.ListCommand;
import olivero.exceptions.CommandParseException;

public class ListCommandParser extends CommandParser<ListCommand> {

    @Override
    public ListCommand parse(String arguments) throws CommandParseException {
        if (!arguments.isBlank()) {
            throw new CommandParseException(
                    ListCommand.MESSAGE_INVALID_FORMAT,
                    ListCommand.MESSAGE_USAGE);
        }
        return new ListCommand();
    }
}
