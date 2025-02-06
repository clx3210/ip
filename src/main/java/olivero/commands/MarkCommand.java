package olivero.commands;

import java.util.regex.Pattern;

import olivero.common.Responses;
import olivero.exceptions.CommandExecutionException;
import olivero.exceptions.StorageSaveException;
import olivero.storage.Storage;
import olivero.tasks.TaskList;

/**
 * Marks a task as done.
 */
public class MarkCommand extends Command {

    public static final String RESPONSE_SUCCESS = "Cool! I've marked this task as done: %s";

    public static final String MESSAGE_INVALID_FORMAT = "Your mark command format is invalid...";

    public static final String MESSAGE_USAGE = "Usage: mark <task number>";


    private final int taskNumber;

    /**
     * Constructs an executable command to mark a task at the
     * given task number in a task list as completed.
     *
     * @param taskNumber The task number associated with the task to be
     *                   marked as complete.
     */
    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Marks a task at the given task number specified from the constructor as complete.
     * <p> If the given task number is out of the range of the provided list of tasks or
     * the task fails to save into storage, a context-specific invalid message is
     * displayed on the provided {@code ui}.
     *
     * @param tasks   List of tasks.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    @Override
    public CommandResult execute(TaskList tasks, Storage storage) throws CommandExecutionException {
        int taskSize = tasks.getTaskSize();
        if (taskNumber > taskSize || taskNumber <= 0) {
            throw new CommandExecutionException(
                    String.format(
                            Responses.RESPONSE_INVALID_TASK_NUMBER,
                            taskNumber));
        }
        try {
            tasks.markTaskAt(taskNumber);
            storage.save(tasks);
            return new CommandResult(
                    String.format(
                            RESPONSE_SUCCESS,
                            tasks.getTaskDescription(taskNumber)));
        } catch (StorageSaveException e) {
            throw new CommandExecutionException(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
