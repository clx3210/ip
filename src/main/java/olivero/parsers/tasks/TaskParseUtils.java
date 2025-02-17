package olivero.parsers.tasks;

import static olivero.parsers.tasks.TaskParser.SEPARATOR_TOKEN;


/**
 * Provides utility methods for encoding and decoding tasks.
 */
public final class TaskParseUtils {

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
