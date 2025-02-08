package olivero.parsers.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.MarkCommand;
import olivero.exceptions.CommandParseException;

/**
 * Represents the parser for parsing arguments into a {@link MarkCommand} object.
 */
public class MarkCommandParser extends CommandParser<MarkCommand> {

    public static final Pattern MARK_COMMAND_FORMAT = Pattern.compile(" (?<taskNumber>.*)");

    @Override
    public MarkCommand parse(String arguments) throws CommandParseException {
        final Matcher matcher = MARK_COMMAND_FORMAT.matcher(arguments);
        if (!matcher.matches()) {
            throw new CommandParseException(
                    MarkCommand.MESSAGE_INVALID_FORMAT,
                    MarkCommand.MESSAGE_USAGE);
        }
        final String taskNumberString = matcher.group("taskNumber").strip();
        int taskNumber = CommandParseUtils.parseInteger(taskNumberString);
        return new MarkCommand(taskNumber);
    }
}
