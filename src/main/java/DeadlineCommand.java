import java.io.IOException;

public class DeadlineCommand extends Command {

    private final Deadline deadline;

    public DeadlineCommand(Deadline deadline) {
        this.deadline = deadline;

    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            tasks.addTask(deadline);
            storage.saveData(tasks.asFormattedString());
            ui.displayTaskResponse(deadline, tasks);
        } catch (IOException e) {
            ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
