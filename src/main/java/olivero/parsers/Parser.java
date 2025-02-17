package olivero.parsers;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.Command;
import olivero.commands.CommandType;
import olivero.common.Responses;
import olivero.exceptions.CommandParseException;
import olivero.exceptions.UnsupportedCommandException;
import olivero.parsers.commands.ByeCommandParser;
import olivero.parsers.commands.CommandParser;
import olivero.parsers.commands.DeadlineCommandParser;
import olivero.parsers.commands.DeleteCommandParser;
import olivero.parsers.commands.EventCommandParser;
import olivero.parsers.commands.FindCommandParser;
import olivero.parsers.commands.ListCommandParser;
import olivero.parsers.commands.MarkCommandParser;
import olivero.parsers.commands.ToDoCommandParser;
import olivero.parsers.commands.UnMarkCommandParser;

/**
 * Represents the main parser for parsing user input into {@link Command} objects.
 */
public class Parser {

    private static final Pattern COMMAND_FORMAT = Pattern.compile("(?<command>\\S+)(?<arguments>.*)");
    private final HashMap<CommandType, CommandParser<? extends Command>> commandParsers;


    /**
     * Constructs a new {@link Parser} object.
     */
    public Parser() {
        commandParsers = new HashMap<>();

        commandParsers.put(CommandType.BYE, new ByeCommandParser());
        commandParsers.put(CommandType.DEADLINE, new DeadlineCommandParser());
        commandParsers.put(CommandType.DELETE, new DeleteCommandParser());
        commandParsers.put(CommandType.EVENT, new EventCommandParser());
        commandParsers.put(CommandType.FIND, new FindCommandParser());
        commandParsers.put(CommandType.LIST, new ListCommandParser());
        commandParsers.put(CommandType.MARK, new MarkCommandParser());
        commandParsers.put(CommandType.TODO, new ToDoCommandParser());
        commandParsers.put(CommandType.UNMARK, new UnMarkCommandParser());

        // super important to ensure each command has a corresponding parser.
        assert commandParsers.size() == CommandType.values().length;
    }


    /**
     * Parses the raw input into a {@link Command} object.
     *
     * @param rawInput The raw user input to be parsed.
     * @return Command object representing the parsed input.
     * @throws CommandParseException If the user input is invalid and cannot be parsed.
     */
    public Command parseCommand(String rawInput) throws CommandParseException {
        final Matcher matcher = COMMAND_FORMAT.matcher(rawInput.trim());
        if (!matcher.matches()) {
            throw new CommandParseException(Responses.RESPONSE_UNKNOWN_COMMAND);
        }

        try {

            String command = matcher.group("command");
            String arguments = matcher.group("arguments");
            CommandType type = CommandType.asCommandType(command);
            return commandParsers.get(type).parse(arguments);
        } catch (UnsupportedCommandException e) {
            throw new CommandParseException(Responses.RESPONSE_UNKNOWN_COMMAND);
        }
    }
}
