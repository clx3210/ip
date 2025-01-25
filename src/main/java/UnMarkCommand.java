import errors.StorageSaveException;

public class UnMarkCommand extends Command {

    private final int taskNumber;

    public UnMarkCommand(int taskNumber) {
       this.taskNumber = taskNumber;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            tasks.unmarkTaskAt(taskNumber);
            ui.displayUnMarkResponse(tasks, taskNumber);
            storage.save(tasks);
        } catch (StorageSaveException e) {
            ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
