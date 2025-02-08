package olivero.commands;

import olivero.common.Responses;
import olivero.exceptions.CommandExecutionException;
import olivero.exceptions.StorageSaveException;
import olivero.storage.Storage;
import olivero.tasks.Task;
import olivero.tasks.TaskList;

/**
 * Deletes a specified task.
 */
public class DeleteCommand extends Command {
    public static final String RESPONSE_SUCCESS = "OK, I've removed this task:"
            + System.lineSeparator()
            + " %s"
            + System.lineSeparator()
            + "Now you have %d task(s) in the list.";

    public static final String MESSAGE_INVALID_FORMAT = "Your delete command format is invalid...";
    public static final String MESSAGE_USAGE = "Usage: delete <taskNumber>";

    private final int taskNumber;

    /**
     * Constructs an executable command to delete the task
     * from a provided task list at the provided task number.
     *
     * @param taskNumber The task number of the task to be deleted from the task list.
     */
    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Deletes a task with the given task number and saves the resulting task list
     * into the provided storage medium.
     * <p> If the taskNumber provided from the constructor is out of the range of the provided
     * task list, an invalid task number is displayed.
     *
     * @param tasks   List of tasks.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    @Override
    public CommandResult execute(TaskList tasks, Storage storage) throws CommandExecutionException {
        assert tasks != null;
        assert storage != null;
        try {
            int taskSize = tasks.getTaskSize();
            CommandUtils.validateTaskNumberRange(taskNumber, taskSize);

            Task removedTask = tasks.removeTaskAt(taskNumber);
            storage.save(tasks);
            return new CommandResult(
                    String.format(RESPONSE_SUCCESS, removedTask, tasks.getTaskSize()));
        } catch (StorageSaveException e) {
            throw new CommandExecutionException(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
