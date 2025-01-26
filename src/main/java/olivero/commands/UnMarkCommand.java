package olivero.commands;

import olivero.common.Responses;
import olivero.storage.Storage;
import olivero.tasks.TaskList;
import olivero.ui.Ui;
import olivero.exceptions.StorageSaveException;

/**
 * Un marks a given task.
 */
public class UnMarkCommand extends Command {

    private final int taskNumber;

    /**
     * Creates an UnMarkCommand instance with the specified 1-indexed task number
     * in a task list.
     *
     * @param taskNumber the task number for a task in the task list.
     */
    public UnMarkCommand(int taskNumber) {
       this.taskNumber = taskNumber;
    }

    /**
     * Sets the status of the specified task in the provided {@code tasks} as incomplete/undone.
     * <p> Updates the {@code storage} medium with the updated task list and displays
     * a response onto the {@code ui} provided.
     * <p> If the task number given is out of the range of the tasks, then an error message is displayed.
     * Otherwise, if storage saving fails, then an error message is also displayed.
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
        tasks.unmarkTaskAt(taskNumber);
        ui.displayUnMarkResponse(tasks, taskNumber);

        try {
            storage.save(tasks);
        } catch (StorageSaveException e) {
            ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
