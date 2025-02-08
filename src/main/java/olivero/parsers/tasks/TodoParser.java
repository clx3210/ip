package olivero.parsers.tasks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.exceptions.TaskParseException;
import olivero.tasks.ToDo;

/**
 * Represents a parser for parsing ToDo tasks.
 */
public class TodoParser extends TaskParser<ToDo> {

    private static final String MESSAGE_INVALID_ARGUMENT_FORMAT = "Invalid argument format for ToDo task.";
    private static final Pattern TODO_TASK_FORMAT = Pattern.compile(
            "T" + TaskParser.SEPARATOR_REGEX
                    + "(?<isDone>[01])"
                    + TaskParser.SEPARATOR_REGEX
                    + "(?<description>.*)");
    @Override
    public ToDo parse(String taskString) throws TaskParseException {
        final Matcher matcher = TODO_TASK_FORMAT.matcher(taskString);
        if (!matcher.matches()) {
            throw new TaskParseException(MESSAGE_INVALID_ARGUMENT_FORMAT);
        }
        String isDoneString = matcher.group("isDone");
        String description = TaskParseUtils.unescapeDescription(matcher.group("description"));

        assert isDoneString.equals(TASK_NOT_DONE)
                || isDoneString.equals(TASK_DONE)
                : "isDoneString should be 0 or 1";
        boolean isDone = isDoneString.equals(TASK_DONE);

        return new ToDo(description, isDone);
    }
}
