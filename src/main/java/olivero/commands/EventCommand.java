package olivero.commands;

import olivero.common.Responses;
import olivero.exceptions.CommandExecutionException;
import olivero.exceptions.StorageSaveException;
import olivero.storage.Storage;
import olivero.tasks.Event;
import olivero.tasks.TaskList;

/**
 * Creates and saves an event task.
 */
public class EventCommand extends Command {

    /** Usage information for the event command. */
    public static final String MESSAGE_USAGE = "Example usage: event <description> "
            + "/from <start date> /to <end date>";

    /** Display message for a missing or invalid "/from" token in the user input. */
    public static final String MESSAGE_INVALID_FROM = "Oh no!? "
            + "Did you correctly specify the '/from <start date>' of the event?";

    /** Display message for a missing or invalid "/to" token in the user input. */
    public static final String MESSAGE_INVALID_TO = "Oh no!? "
            + "Did you correctly specify the '/to <end date>' of "
            + "the event?";

    /** Display message when "/to" token comes before "/from" in the command input. */
    public static final String MESSAGE_INVALID_DATE_ORDER = "Oh no!? "
            + "Did you mix up the order of /from and /to?";

    /** Display message for an empty event description. */
    public static final String MESSAGE_EMPTY_DESCRIPTION = "HUH? "
            + "You can't have an empty Event description...";

    public static final String RESPONSE_SUCCESS = "Got it. I've added this task:"
            + System.lineSeparator()
            + "  %s"
            + System.lineSeparator()
            + "Now you have %d task(s) in the list.";
    private final Event event;

    /**
     * Constructs an executable command to add an event task to a task list.
     *
     * @param event The event task to be added on execution.
     */
    public EventCommand(Event event) {
        this.event = event;
    }

    /**
     * Adds an event task specified from the constructor into the provided list of tasks
     * and saves it into the provided storage medium.
     *
     * @param tasks   List of tasks.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    @Override
    public CommandResult execute(TaskList tasks, Storage storage) throws CommandExecutionException {
        try {
            tasks.addTask(event);
            storage.save(tasks);
            return new CommandResult(String.format(RESPONSE_SUCCESS, event));
        } catch (StorageSaveException e) {
            throw new CommandExecutionException(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
