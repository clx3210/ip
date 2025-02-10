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

    public UnMarkCommandParser() {
        this.massOpsParser = new MassOpsParser(
                UnMarkCommand.MESSAGE_INVALID_FORMAT,
                UnMarkCommand.MESSAGE_USAGE);
    }

    private UnMarkCommand setupSingleUnmark(Matcher matcher) throws CommandParseException {
        final String taskNumberString = matcher.group("taskNumber");
        int taskNumber = CommandParseUtils.parseInteger(taskNumberString);
        return new UnMarkCommand(taskNumber);
    }

    private UnMarkCommand setupMassUnmark(String arguments) throws CommandParseException {
        return new UnMarkCommand(massOpsParser.lazyParse(arguments));
    }
    @Override
    public UnMarkCommand parse(String arguments) throws CommandParseException {
        final Matcher matcher = UNMARK_COMMAND_FORMAT.matcher(arguments);
        final boolean isSingleMark = matcher.matches();
        final boolean isMassOpsMark = massOpsParser.isMassOpsMatch(arguments);

        if (!isMassOpsMark && !isSingleMark) {
            throw new CommandParseException(
                    UnMarkCommand.MESSAGE_INVALID_FORMAT,
                    UnMarkCommand.MESSAGE_USAGE);
        }
        if (isSingleMark) {
            return setupSingleUnmark(matcher);
        } else {
            return setupMassUnmark(arguments);
        }
    }
}
