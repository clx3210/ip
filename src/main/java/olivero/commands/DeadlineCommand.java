package olivero.commands;

import olivero.common.Responses;
import olivero.exceptions.CommandExecutionException;
import olivero.exceptions.StorageSaveException;
import olivero.storage.Storage;
import olivero.tasks.Deadline;
import olivero.tasks.TaskList;

/**
 * Creates and stores a deadline task.
 */
public class DeadlineCommand extends Command {


    /** Usage information for the deadline command. */
    public static final String MESSAGE_USAGE = "Example usage: "
            + "deadline <description> /by <start date>";

    public static final String MESSAGE_INVALID_FORMAT = "Your "
            + "deadline command format is invalid...";

    /** Display message when the "/by" field of the command syntax is invalid. */
    public static final String MESSAGE_INVALID_BY_TOKEN = "Did you correctly "
            + "specify the '/by <end date>' of your deadline task?";

    /** Display message for an empty deadline task description. */
    public static final String MESSAGE_EMPTY_DESCRIPTION = "HUH? "
            + "You can't have an empty deadline task description...";

    public static final String RESPONSE_SUCCESS = "Got it. I've added this task:"
            + System.lineSeparator()
            + "  %s"
            + System.lineSeparator()
            + "Now you have %d task(s) in the list.";

    private final Deadline deadline;

    /**
     * Constructs the executable command to add the provided deadline task object.
     *
     * @param deadline The deadline task object to be added to a task list.
     */
    public DeadlineCommand(Deadline deadline) {
        this.deadline = deadline;

    }

    /**
     * Adds a deadline task to the provided task list and saves it into storage medium.
     * Displays a success message if saving is successful, otherwise a failed message is
     * displayed on the ui.
     *
     * @param tasks   List of tasks.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    @Override
    public CommandResult execute(TaskList tasks, Storage storage) throws CommandExecutionException {
        try {
            tasks.addTask(deadline);
            storage.save(tasks);
            return new CommandResult(
                    String.format(
                            RESPONSE_SUCCESS,
                            deadline,
                            tasks.getTaskSize()));
        } catch (StorageSaveException e) {
            throw new CommandExecutionException(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
