import errors.StorageSaveException;

public class DeadlineCommand extends Command {

    private final Deadline deadline;

    public DeadlineCommand(Deadline deadline) {
        this.deadline = deadline;

    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            tasks.addTask(deadline);
            storage.save(tasks);
            ui.displayTaskResponse(deadline, tasks);
        } catch (StorageSaveException e) {
            ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
