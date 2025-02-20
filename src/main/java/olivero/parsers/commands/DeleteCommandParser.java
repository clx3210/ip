package olivero.parsers.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.DeleteCommand;
import olivero.exceptions.CommandParseException;

/**
 * Represents the parser for parsing command arguments into a {@link DeleteCommand} object.
 * Solution inspired by DeleteCommandParser.java in
 * <a href="https://github.com/se-edu/addressbook-level4/tree/master/src">AB4</a>.
 */
public class DeleteCommandParser extends CommandParser<DeleteCommand> {

    private static final Pattern DELETE_COMMAND_FORMAT = Pattern.compile("^\\s(?!-m)(?<taskNumber>.*)");

    private final MassOpsParser massOpsParser;

    /**
     * Constructs a DeleteCommandParser object for parsing delete commands.
     */
    public DeleteCommandParser() {
        this.massOpsParser = new MassOpsParser(
                DeleteCommand.MESSAGE_INVALID_FORMAT,
                DeleteCommand.MESSAGE_USAGE);
    }

    private DeleteCommand setUpSingleDelete(Matcher matcher) throws CommandParseException {
        String taskNumberString = matcher.group("taskNumber").trim();
        int taskNumber = CommandParseUtil.parseInteger(taskNumberString);
        return new DeleteCommand(taskNumber);
    }

    private DeleteCommand setUpMassDelete(String arguments) throws CommandParseException {
        return new DeleteCommand(massOpsParser.parse(arguments));
    }

    @Override
    public DeleteCommand parse(String arguments) throws CommandParseException {
        final Matcher matcher = DELETE_COMMAND_FORMAT.matcher(arguments);

        final boolean isSingleDelete = matcher.matches();
        final boolean isMassOpsDelete = massOpsParser.isMassOpsMatch(arguments);

        if (!isSingleDelete && !isMassOpsDelete) {
            throw new CommandParseException(
                    DeleteCommand.MESSAGE_INVALID_FORMAT,
                    DeleteCommand.MESSAGE_USAGE
            );
        }

        if (isSingleDelete) {
            return setUpSingleDelete(matcher);
        } else {
            return setUpMassDelete(arguments);
        }
    }
}
