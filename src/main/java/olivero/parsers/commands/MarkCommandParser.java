package olivero.parsers.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.MarkCommand;
import olivero.common.Responses;
import olivero.exceptions.CommandParseException;

/**
 * Represents the parser for parsing arguments into a {@link MarkCommand} object.
 */
public class MarkCommandParser extends CommandParser<MarkCommand> {

    public static final Pattern MARK_COMMAND_FORMAT = Pattern.compile(" (?<taskNumber>.*)");
    private MarkCommand setupMark(String arguments) throws CommandParseException {
        try {
            int taskNumber = Integer.parseInt(arguments.strip());
            return new MarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new CommandParseException(
                    String.format(
                            Responses.RESPONSE_INVALID_NUMBER_FORMAT,
                            arguments.strip()));
        }
    }

    @Override
    public MarkCommand parse(String arguments) throws CommandParseException {
        final Matcher matcher = MARK_COMMAND_FORMAT.matcher(arguments);
        if (!matcher.matches()) {
            throw new CommandParseException(
                    MarkCommand.MESSAGE_INVALID_FORMAT,
                    MarkCommand.MESSAGE_USAGE);
        }
        assert matcher.groupCount() == 1;

        final String taskNumber = matcher.group("taskNumber");

        return setupMark(taskNumber);
    }
}
