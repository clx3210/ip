package olivero.parsers.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.FindCommand;
import olivero.exceptions.CommandParseException;

/**
 * Represents the parser for parsing command arguments into a {@link FindCommand} object.
 */
public class FindCommandParser extends CommandParser<FindCommand> {

    private static final Pattern FIND_COMMAND_FORMAT = Pattern.compile(" (?<keyword>.+)");

    @Override
    public FindCommand parse(String arguments) throws CommandParseException {
        final Matcher matcher = FIND_COMMAND_FORMAT.matcher(arguments);
        if (!matcher.matches()) {
            throw new CommandParseException(
                    FindCommand.MESSAGE_INVALID_FORMAT,
                    FindCommand.MESSAGE_USAGE);
        }
        assert matcher.groupCount() == 1;

        final String keyword = matcher.group("keyword");
        return new FindCommand(keyword.trim());
    }
}
