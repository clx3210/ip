package olivero.commands;

import olivero.common.Responses;
import olivero.storage.Storage;
import olivero.tasks.TaskList;
import olivero.ui.Ui;
import olivero.exceptions.StorageSaveException;

/**
 * Marks a task as done.
 */
public class MarkCommand extends Command{

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
     * @param tasks List of tasks.
     * @param ui The User interface for the command to output messages to during execution.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        int taskSize = tasks.getTaskSize();
        if (taskNumber > taskSize || taskNumber <= 0) {
            ui.displayMessage(
                    String.format(
                            Responses.RESPONSE_INVALID_TASK_NUMBER,
                            taskNumber));
            return;
        }
        tasks.markTaskAt(taskNumber);
        ui.displayMarkResponse(tasks, taskNumber);

        try {
            storage.save(tasks);
        } catch (StorageSaveException e) {
           ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
