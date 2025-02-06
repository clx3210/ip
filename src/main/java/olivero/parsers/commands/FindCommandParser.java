package olivero.parsers.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.FindCommand;
import olivero.exceptions.CommandParseException;

public class FindCommandParser extends CommandParser<FindCommand> {

    public static final Pattern FIND_COMMAND_FORMAT = Pattern.compile(" (?<keyword>.+)");

    @Override
    public FindCommand parse(String arguments) throws CommandParseException {
        final Matcher matcher = FIND_COMMAND_FORMAT.matcher(arguments);
        if (!matcher.matches()) {
            throw new CommandParseException(
                    FindCommand.MESSAGE_INVALID_FORMAT,
                    FindCommand.MESSAGE_USAGE);
        }
        final String keyword = matcher.group("keyword");
        return new FindCommand(keyword.trim());
    }
}
