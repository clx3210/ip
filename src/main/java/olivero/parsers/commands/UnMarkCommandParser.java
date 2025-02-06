package olivero.parsers.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.UnMarkCommand;
import olivero.common.Responses;
import olivero.exceptions.CommandParseException;

public class UnMarkCommandParser extends CommandParser<UnMarkCommand> {

    public static final Pattern UNMARK_COMMAND_FORMAT = Pattern.compile(" (?<taskNumber>.*)");
    private UnMarkCommand setupUnMark(String arguments) throws CommandParseException {
        try {
            int taskNumber = Integer.parseInt(arguments.strip());
            return new UnMarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new CommandParseException(
                    String.format(
                            Responses.RESPONSE_INVALID_NUMBER_FORMAT,
                            arguments.strip()));
        }
    }

    @Override
    public UnMarkCommand parse(String arguments) throws CommandParseException {
        final Matcher matcher = UNMARK_COMMAND_FORMAT.matcher(arguments);
        if (!matcher.matches()) {
            throw new CommandParseException(
                    UnMarkCommand.MESSAGE_INVALID_FORMAT,
                    UnMarkCommand.MESSAGE_USAGE);
        }
        final String taskNumber = matcher.group("taskNumber");
        return setupUnMark(taskNumber);
    }
}
