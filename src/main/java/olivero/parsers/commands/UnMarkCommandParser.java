package olivero.parsers.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.UnMarkCommand;
import olivero.exceptions.CommandParseException;

/**
 * Represents the parser for parsing arguments into a {@link UnMarkCommand} object.
 */
public class UnMarkCommandParser extends CommandParser<UnMarkCommand> {

    public static final Pattern UNMARK_COMMAND_FORMAT = Pattern.compile(" (?<taskNumber>.*)");

    @Override
    public UnMarkCommand parse(String arguments) throws CommandParseException {
        final Matcher matcher = UNMARK_COMMAND_FORMAT.matcher(arguments);
        if (!matcher.matches()) {
            throw new CommandParseException(
                    UnMarkCommand.MESSAGE_INVALID_FORMAT,
                    UnMarkCommand.MESSAGE_USAGE);
        }
        assert matcher.groupCount() == 1;

        final String taskNumberString = matcher.group("taskNumber");
        int taskNumber = CommandParseUtils.parseInteger(taskNumberString);
        return new UnMarkCommand(taskNumber);
    }
}
