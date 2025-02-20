package olivero.parsers.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.TodoCommand;
import olivero.exceptions.CommandParseException;
import olivero.tasks.Todo;

/**
 * Represents a parser responsible for parsing command arguments into a {@link TodoCommand} object.
 */
public class TodoCommandParser extends CommandParser<TodoCommand> {

    public static final Pattern TODO_COMMAND_FORMAT = Pattern.compile(" (?<description>.+)");
    private TodoCommand setUpTodo(String description) throws CommandParseException {
        if (description.isBlank()) {
            throw new CommandParseException(
                    TodoCommand.MESSAGE_EMPTY_DESCRIPTION,
                    TodoCommand.MESSAGE_USAGE);
        }
        return new TodoCommand(new Todo(description, false));
    }

    @Override
    public TodoCommand parse(String arguments) throws CommandParseException {
        final Matcher matcher = TODO_COMMAND_FORMAT.matcher(arguments);
        if (!matcher.matches()) {
            throw new CommandParseException(
                    TodoCommand.MESSAGE_INVALID_FORMAT,
                    TodoCommand.MESSAGE_USAGE);
        }

        final String description = matcher.group("description");
        return setUpTodo(description);
    }
}
