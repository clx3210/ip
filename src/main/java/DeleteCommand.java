import errors.StorageSaveException;

public class DeleteCommand extends Command {

    private final int taskNumber;
    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task removedTask = tasks.removeTaskAt(taskNumber);
            ui.displayDeleteTaskResponse(removedTask, tasks);
            storage.save(tasks);
        } catch (StorageSaveException e) {
            ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }

    }
}
