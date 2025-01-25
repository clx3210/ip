import java.io.IOException;

public class MarkCommand extends Command{

    private final int taskNumber;

    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            tasks.markTaskAt(taskNumber);
            storage.saveData(tasks.asFormattedString());
            ui.displayMarkResponse(tasks, taskNumber);
        } catch (IOException e) {
           ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
