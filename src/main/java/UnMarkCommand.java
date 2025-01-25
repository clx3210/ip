import errors.StorageSaveException;

public class UnMarkCommand extends Command {

    private final int taskNumber;

    public UnMarkCommand(int taskNumber) {
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
        tasks.unmarkTaskAt(taskNumber);
        ui.displayUnMarkResponse(tasks, taskNumber);

        try {
            storage.save(tasks);
        } catch (StorageSaveException e) {
            ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
