package olivero.commands;

import olivero.common.Responses;
import olivero.exceptions.StorageSaveException;
import olivero.storage.Storage;
import olivero.tasks.TaskList;
import olivero.tasks.ToDo;
import olivero.ui.Ui;

/**
 * Creates and saves a ToDo task.
 */
public class ToDoCommand extends Command {

    /** Usage information for the todo command. */
    public static final String MESSAGE_USAGE = "Usage: todo <description>";

    /** Display message for an empty todo task description. */
    public static final String MESSAGE_EMPTY_DESCRIPTION = "You can't have an empty Todo...";

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
     * @param tasks List of tasks.
     * @param ui The User interface for the command to output messages to during execution.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            tasks.addTask(toDo);
            storage.save(tasks);
            ui.displayTaskResponse(toDo, tasks);
        } catch (StorageSaveException e) {
            ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
