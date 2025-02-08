package olivero.parsers.tasks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.exceptions.TaskParseException;
import olivero.tasks.ToDo;

public class TodoParser extends TaskParser<ToDo> {

    private static final Pattern TODO_TASK_FORMAT = Pattern.compile(
            "T" + TaskParser.SEPARATOR_REGEX
                    + "(?<isDone>[01])"
                    + TaskParser.SEPARATOR_REGEX
                    + "(?<description>.*)");
    @Override
    public ToDo parse(String taskString) throws TaskParseException {
        final Matcher matcher = TODO_TASK_FORMAT.matcher(taskString);
        if (!matcher.matches()) {
            throw new TaskParseException("Invalid argument format for ToDo task.");
        }
        String isDoneString = matcher.group("isDone");
        String description = matcher.group("description").replaceAll(TaskParser.ESCAPE_REGEX + "\\|", "|");

        assert isDoneString.equals("0") || isDoneString.equals("1") : "isDoneString should be 0 or 1";
        boolean isDone = isDoneString.equals("1");

        return new ToDo(description, isDone);
    }
}
