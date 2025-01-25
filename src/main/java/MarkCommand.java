import errors.StorageSaveException;

public class MarkCommand extends Command{

    private final int taskNumber;

    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            tasks.markTaskAt(taskNumber);
            ui.displayMarkResponse(tasks, taskNumber);
            storage.save(tasks);
        } catch (StorageSaveException e) {
           ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
