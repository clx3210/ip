package olivero.commands;

import java.util.Collection;

import olivero.common.Responses;
import olivero.exceptions.CommandExecutionException;

/**
 * Provides utility methods executing commands.
 */
public final class CommandUtils {
    /**
     * Validates that the task number is within the valid range between 0 and taskListSize - 1.
     *
     * @param taskNumber The task number to be validated.
     * @param taskListSize The size of the task list containing the task.
     * @throws CommandExecutionException If the task number is out of range.
     */
    public static void validateTaskNumberRange(int taskNumber, int taskListSize) throws CommandExecutionException {
        if (taskNumber > taskListSize || taskNumber <= 0) {
            throw new CommandExecutionException(
                    String.format(
                            Responses.RESPONSE_INVALID_TASK_NUMBER,
                            taskNumber));
        }
    }

    public static void validateTaskNumbers(Collection<Integer> taskNumbers, int taskListSize)
            throws CommandExecutionException {
        for (int taskNumber : taskNumbers) {
            validateTaskNumberRange(taskNumber, taskListSize);
        }
    }
}
