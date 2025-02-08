package olivero.parsers.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.DeleteCommand;
import olivero.exceptions.CommandParseException;

/**
 * Represents the parser for parsing command arguments into a {@link DeleteCommand} object.
 */
public class DeleteCommandParser extends CommandParser<DeleteCommand> {

    private static final Pattern DELETE_COMMAND_FORMAT = Pattern.compile(" (?<taskNumber>.*)");
    @Override
    public DeleteCommand parse(String arguments) throws CommandParseException {
        final Matcher matcher = DELETE_COMMAND_FORMAT.matcher(arguments);
        if (!matcher.matches()) {
            throw new CommandParseException(
                    DeleteCommand.MESSAGE_INVALID_FORMAT,
                    DeleteCommand.MESSAGE_USAGE
            );
        }
        assert matcher.groupCount() == 1;

        String taskNumberString = matcher.group("taskNumber").trim();
        int taskNumber = CommandParseUtils.parseInteger(taskNumberString);
        return new DeleteCommand(taskNumber);
    }
}
