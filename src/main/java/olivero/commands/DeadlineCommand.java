package olivero.commands;

import olivero.common.Responses;
import olivero.exceptions.StorageSaveException;
import olivero.storage.Storage;
import olivero.tasks.Deadline;
import olivero.tasks.TaskList;
import olivero.ui.Ui;

public class DeadlineCommand extends Command {

    public static final String MESSAGE_USAGE = "Example usage: "
            + "deadline <description> /by <start date>";
    public static final String MESSAGE_INVALID_BY_TOKEN = "Did you correctly "
            + "specify the '/by <end date>' of your deadline task?";
    public static final String MESSAGE_EMPTY_DESCRIPTION = "HUH? "
            + "You can't have an empty deadline task description...";
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
