package olivero.parsers.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.ToDoCommand;
import olivero.exceptions.CommandParseException;
import olivero.tasks.ToDo;

/**
 * Represents a parser responsible for parsing command arguments into a {@link ToDoCommand} object.
 */
public class ToDoCommandParser extends CommandParser<ToDoCommand> {

    public static final Pattern TODO_COMMAND_FORMAT = Pattern.compile(" (?<description>.+)");
    private ToDoCommand setupToDo(String description) throws CommandParseException {
        if (description.isBlank()) {
            throw new CommandParseException(
                    ToDoCommand.MESSAGE_EMPTY_DESCRIPTION,
                    ToDoCommand.MESSAGE_USAGE);
        }
        return new ToDoCommand(new ToDo(description, false));
    }

    @Override
    public ToDoCommand parse(String arguments) throws CommandParseException {
        final Matcher matcher = TODO_COMMAND_FORMAT.matcher(arguments);
        if (!matcher.matches()) {
            throw new CommandParseException(
                    ToDoCommand.MESSAGE_INVALID_FORMAT,
                    ToDoCommand.MESSAGE_USAGE);
        }
        final String description = matcher.group("description");
        return setupToDo(description);
    }
}
