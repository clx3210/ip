package olivero.parsers.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.MarkCommand;
import olivero.exceptions.CommandParseException;

/**
 * Represents the parser for parsing arguments into a {@link MarkCommand} object.
 */
public class MarkCommandParser extends CommandParser<MarkCommand> {

    public static final Pattern MARK_COMMAND_FORMAT = Pattern.compile("^\\s(?!-m)(?<taskNumber>.*)");

    private final MassOpsParser massOpsParser;

    /**
     * Constructs a MarkCommandParser object for parsing mark commands.
     */
    public MarkCommandParser() {
        this.massOpsParser = new MassOpsParser(
                MarkCommand.MESSAGE_INVALID_FORMAT,
                MarkCommand.MESSAGE_USAGE);
    }

    private MarkCommand setUpMark(Matcher matcher) throws CommandParseException {
        final String taskNumberString = matcher.group("taskNumber").trim();
        int taskNumber = CommandParseUtil.parseInteger(taskNumberString);

        return new MarkCommand(taskNumber);
    }

    @Override
    public MarkCommand parse(String arguments) throws CommandParseException {
        final Matcher matcher = MARK_COMMAND_FORMAT.matcher(arguments);
        final boolean isSingleMark = matcher.matches();
        final boolean isMassOpsMark = massOpsParser.isMassOpsMatch(arguments);

        if (!isSingleMark && !isMassOpsMark) {
            throw new CommandParseException(
                    MarkCommand.MESSAGE_INVALID_FORMAT,
                    MarkCommand.MESSAGE_USAGE);
        }
        if (isSingleMark) {
            return setUpMark(matcher);
        } else {
            return new MarkCommand(massOpsParser.parse(arguments));
        }
    }
}
