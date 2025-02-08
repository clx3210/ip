package olivero.parsers.tasks;

import static olivero.parsers.tasks.TaskParser.ESCAPED_REGEX;
import static olivero.parsers.tasks.TaskParser.ESCAPED_TOKEN;
import static olivero.parsers.tasks.TaskParser.SEPARATOR_TOKEN;
import static olivero.parsers.tasks.TaskParser.UNESCAPED_REGEX;
import static olivero.parsers.tasks.TaskParser.UNESCAPED_TOKEN;

/**
 * Provides utility methods for encoding and decoding tasks.
 */
public final class TaskParseUtils {

    /**
     * Unescapes the description of a task.
     *
     * @param description The description containing escaped tokens.
     * @return The description with escaped tokens replaced with their original values.
     */
    public static String unescapeDescription(String description) {
        return description.replaceAll(ESCAPED_REGEX, UNESCAPED_TOKEN);
    }

    /**
     * Escapes the description of a task.
     *
     * @param description The description containing unescaped tokens.
     * @return The description with unescaped tokens replaced with their escaped values.
     */
    public static String escapeDescription(String description) {
        return description.replaceAll(UNESCAPED_REGEX, ESCAPED_TOKEN);
    }

    /**
     * Returns the String representation of the done status of a task.
     *
     * @param isDone The task done status.
     * @return String representation of the done status.
     */
    public static String prepareDoneStatus(boolean isDone) {
        return isDone ? TaskParser.TASK_DONE : TaskParser.TASK_NOT_DONE;
    }

    /**
     * Returns a formatted task string for encoding.
     *
     * @param args Task arguments to be separated via {@link TaskParser#SEPARATOR_TOKEN}.
     * @return Formatted task string.
     */
    public static String formatTask(String... args) {
        return String.join(SEPARATOR_TOKEN, args);
    }
}
