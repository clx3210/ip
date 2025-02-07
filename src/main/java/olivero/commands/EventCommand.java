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
            + "/from <start date: YYYY-M-d Hmm> /to <end date: YYYY-M-d Hmm>";

    /** Display message for an empty event description. */
    public static final String MESSAGE_EMPTY_DESCRIPTION = "HUH? "
            + "You can't have an empty Event description...";

    public static final String MESSAGE_INVALID_FORMAT = "Your "
            + "event command format is invalid...";

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
            return new CommandResult(String.format(RESPONSE_SUCCESS, event, tasks.getTaskSize()));
        } catch (StorageSaveException e) {
            throw new CommandExecutionException(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
