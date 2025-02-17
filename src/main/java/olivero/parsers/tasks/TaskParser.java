package olivero.parsers.tasks;

import olivero.exceptions.TaskParseException;
import olivero.tasks.Task;

/**
 * Represents the base class for parsing tasks of type T.
 *
 * @param <T> Any subclass of Task.
 */
public abstract class TaskParser<T extends Task> {

    /** Regexes for ' | ' during parsing. */
    protected static final String SEPARATOR_REGEX = "\\s\\|\\s";

    /** Tokens used in parsing tasks. */
    static final String SEPARATOR_TOKEN = " | ";

    /** String representations of task done status. */
    static final String TASK_DONE = "1";
    static final String TASK_NOT_DONE = "0";



    /**
     * Parses the taskString and returns a Task object.
     *
     * @param taskString The serialised task to be parsed.
     * @return The Task object that was parsed.
     * @throws TaskParseException If the taskString is not in the correct format.
     */
    public abstract T parse(String taskString) throws TaskParseException;
}
