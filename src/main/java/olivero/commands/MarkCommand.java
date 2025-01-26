package olivero.commands;

import olivero.common.Responses;
import olivero.exceptions.StorageSaveException;
import olivero.storage.Storage;
import olivero.tasks.TaskList;
import olivero.ui.Ui;

public class MarkCommand extends Command {

    private final int taskNumber;

    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }
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
