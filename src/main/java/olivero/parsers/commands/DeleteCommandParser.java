package olivero.parsers.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.DeleteCommand;
import olivero.common.Responses;
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

        try {
            int taskNumber = Integer.parseInt(taskNumberString);
            return new DeleteCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new CommandParseException(
                    String.format(
                            Responses.RESPONSE_INVALID_NUMBER_FORMAT,
                            taskNumberString));
        }
    }
}
