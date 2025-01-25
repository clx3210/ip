import java.io.IOException;

public class UnMarkCommand extends Command {

    private final int taskNumber;

    public UnMarkCommand(int taskNumber) {
       this.taskNumber = taskNumber;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            tasks.unmarkTaskAt(taskNumber);
            storage.saveData(tasks.asFormattedString());
            ui.displayUnMarkResponse(tasks, taskNumber);
        } catch (IOException e) {
            ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
