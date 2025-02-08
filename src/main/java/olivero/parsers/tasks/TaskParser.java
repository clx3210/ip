package olivero.parsers.tasks;

import olivero.exceptions.TaskParseException;
import olivero.tasks.Task;

public abstract class TaskParser<T extends Task> {

    /** Regex for padding to unexpected ' | ' occurrences within the string input to escape them. */
    protected static final String ESCAPE_REGEX = "\\*";
    protected static final String SEPARATOR_REGEX = "\\s\\|\\s";

    public static final String ESCAPED_SEPARATOR_TOKEN = "*|";
    public static final String SEPARATOR_TOKEN = "|";

    /**
     * Parses the taskString and returns a Task object
     * @param taskString The serialised task to be parsed
     * @return The Task object that was parsed
     */
    public abstract T parse(String taskString) throws TaskParseException;
}
