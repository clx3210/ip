package olivero.parsers.tasks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.exceptions.TaskParseException;
import olivero.tasks.Todo;

/**
 * Represents a parser for parsing Todo tasks.
 */
public class TodoParser extends TaskParser<Todo> {

    private static final String MESSAGE_INVALID_ARGUMENT_FORMAT = "Invalid argument format for Todo task.";
    private static final Pattern TODO_TASK_FORMAT = Pattern.compile(
            "T" + TaskParser.SEPARATOR_REGEX
                    + "(?<isDone>[01])"
                    + TaskParser.SEPARATOR_REGEX
                    + "(?<description>.*)");
    @Override
    public Todo parse(String taskString) throws TaskParseException {
        final Matcher matcher = TODO_TASK_FORMAT.matcher(taskString);
        if (!matcher.matches()) {
            throw new TaskParseException(MESSAGE_INVALID_ARGUMENT_FORMAT);
        }
        String isDoneString = matcher.group("isDone");
        String description = matcher.group("description");

        assert isDoneString.equals(TASK_NOT_DONE)
                || isDoneString.equals(TASK_DONE)
                : "isDoneString should be 0 or 1";
        boolean isDone = isDoneString.equals(TASK_DONE);

        return new Todo(description, isDone);
    }
}
