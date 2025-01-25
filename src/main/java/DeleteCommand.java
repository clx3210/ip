import java.io.IOException;

public class DeleteCommand extends Command {

    private final int taskNumber;
    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task removedTask = tasks.removeTaskAt(taskNumber);
            storage.saveData(tasks.asFormattedString());
            ui.displayDeleteTaskResponse(removedTask, tasks);
        } catch (IOException e) {
            ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }

    }
}
