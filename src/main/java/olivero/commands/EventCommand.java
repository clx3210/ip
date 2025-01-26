package olivero.commands;

import olivero.common.Responses;
import olivero.exceptions.StorageSaveException;
import olivero.storage.Storage;
import olivero.tasks.Event;
import olivero.tasks.TaskList;
import olivero.ui.Ui;

public class EventCommand extends Command {

    public static final String MESSAGE_USAGE = "Example usage: event <description> "
            + "/from <start date> /to <end date>";
    public static final String MESSAGE_INVALID_FROM = "Oh no!? "
            + "Did you correctly specify the '/from <start date>' of the event?";
    public static final String MESSAGE_INVALID_TO = "Oh no!? "
            + "Did you correctly specify the '/to <end date>' of "
            + "the event?";
    public static final String MESSAGE_INVALID_DATE_ORDER = "Oh no!? "
            + "Did you mix up the order of /from and /to?";
    public static final String MESSAGE_EMPTY_DESCRIPTION = "HUH? "
            + "You can't have an empty Event description...";
    private final Event event;

    public EventCommand(Event event) {
        this.event = event;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            tasks.addTask(event);
            storage.save(tasks);
            ui.displayTaskResponse(event, tasks);
        } catch (StorageSaveException e) {
            ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
