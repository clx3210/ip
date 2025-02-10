package olivero.parsers.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.DeleteCommand;
import olivero.exceptions.CommandParseException;

/**
 * Represents the parser for parsing command arguments into a {@link DeleteCommand} object.
 */
public class DeleteCommandParser extends CommandParser<DeleteCommand> {

    private static final Pattern DELETE_COMMAND_FORMAT = Pattern.compile(" (?<taskNumber>\\d*)");

    private final MassOpsParser massOpsParser;

    public DeleteCommandParser() {
        this.massOpsParser = new MassOpsParser(
                DeleteCommand.MESSAGE_INVALID_FORMAT,
                DeleteCommand.MESSAGE_USAGE);
    }

    private DeleteCommand setupSingleDelete(Matcher matcher) throws CommandParseException {
        String taskNumberString = matcher.group("taskNumber").trim();
        int taskNumber = CommandParseUtils.parseInteger(taskNumberString);
        return new DeleteCommand(taskNumber);
    }

    private DeleteCommand setupMassDelete(String arguments) throws CommandParseException {
        return new DeleteCommand(massOpsParser.lazyParse(arguments));
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
            return setupSingleDelete(matcher);
        } else {
            return setupMassDelete(arguments);
        }
    }
}
