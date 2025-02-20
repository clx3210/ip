package olivero.parsers.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.UnMarkCommand;
import olivero.exceptions.CommandParseException;

/**
 * Represents the parser for parsing arguments into a {@link UnMarkCommand} object.
 */
public class UnMarkCommandParser extends CommandParser<UnMarkCommand> {

    public static final Pattern UNMARK_COMMAND_FORMAT = Pattern.compile(" (?<taskNumber>\\d*)");

    private final MassOpsParser massOpsParser;

    /**
     * Constructs a UnMarkCommandParser object for parsing unmark commands.
     */
    public UnMarkCommandParser() {
        this.massOpsParser = new MassOpsParser(
                UnMarkCommand.MESSAGE_INVALID_FORMAT,
                UnMarkCommand.MESSAGE_USAGE);
    }

    private UnMarkCommand setUpSingleUnmark(Matcher matcher) throws CommandParseException {
        final String taskNumberString = matcher.group("taskNumber");
        int taskNumber = CommandParseUtil.parseInteger(taskNumberString);
        return new UnMarkCommand(taskNumber);
    }

    private UnMarkCommand setUpMassUnmark(String arguments) throws CommandParseException {
        return new UnMarkCommand(massOpsParser.parse(arguments));
    }
    @Override
    public UnMarkCommand parse(String arguments) throws CommandParseException {
        final Matcher matcher = UNMARK_COMMAND_FORMAT.matcher(arguments);
        final boolean isSingleUnmark = matcher.matches();
        final boolean isMassOpsUnmark = massOpsParser.isMassOpsMatch(arguments);

        if (!isMassOpsUnmark && !isSingleUnmark) {
            throw new CommandParseException(
                    UnMarkCommand.MESSAGE_INVALID_FORMAT,
                    UnMarkCommand.MESSAGE_USAGE);
        }
        if (isSingleUnmark) {
            return setUpSingleUnmark(matcher);
        } else {
            return setUpMassUnmark(arguments);
        }
    }
}
