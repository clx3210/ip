package olivero.commands;

import olivero.common.Responses;
import olivero.exceptions.CommandExecutionException;
import olivero.exceptions.StorageSaveException;
import olivero.storage.Storage;
import olivero.tasks.TaskList;
import olivero.tasks.ToDo;

/**
 * Creates and saves a ToDo task.
 */
public class ToDoCommand extends Command {


    /** Usage information for the todo command. */
    public static final String MESSAGE_USAGE = "Usage: todo <description>";

    public static final String MESSAGE_INVALID_FORMAT = "Your todo command format is invalid...";

    /** Display message for an empty todo task description. */
    public static final String MESSAGE_EMPTY_DESCRIPTION = "You can't have an empty Todo...";

    public static final String RESPONSE_SUCCESS = "Got it. I've added this task:"
            + System.lineSeparator()
            + "  %s"
            + System.lineSeparator()
            + "Now you have %d task(s) in the list.";

    private final ToDo toDo;

    /**
     * Constructs an executable command to add the given ToDo task.
     *
     * @param toDo The task to be added to a task list on command execution.
     */
    public ToDoCommand(ToDo toDo) {
        this.toDo = toDo;
    }

    /**
     * Adds the given toDo task provided from the constructor
     * into the given list of tasks {@code task} and saves it into the {@code storage} medium.
     * <p> Displays a success message to {@code ui} or an error response
     * if saving to storage failed.
     *
     * @param tasks   List of tasks.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    @Override
    public CommandResult execute(TaskList tasks, Storage storage) throws CommandExecutionException {
        assert tasks != null;
        assert storage != null;
        assert toDo != null;

        try {
            tasks.addTask(toDo);
            storage.save(tasks);
            return new CommandResult(
                    String.format(RESPONSE_SUCCESS, toDo, tasks.getTaskSize()));
        } catch (StorageSaveException e) {
            throw new CommandExecutionException(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
