package olivero.commands;

import olivero.common.Responses;
import olivero.exceptions.StorageSaveException;
import olivero.storage.Storage;
import olivero.tasks.Task;
import olivero.tasks.TaskList;
import olivero.ui.Ui;

/**
 * Deletes a specified task.
 */
public class DeleteCommand extends Command {

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
        Task removedTask = tasks.removeTaskAt(taskNumber);
        ui.displayDeleteTaskResponse(removedTask, tasks);

        try {
            storage.save(tasks);
        } catch (StorageSaveException e) {
            ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }

    }
}
