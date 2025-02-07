package olivero.commands;

import olivero.exceptions.CommandExecutionException;
import olivero.storage.Storage;
import olivero.tasks.TaskList;

/**
 * Displays the string representations of all saved tasks.
 */
public class ListCommand extends Command {

    public static final String RESPONSE_SUCCESS = "Here are the tasks in your list:"
            + System.lineSeparator() + "%s";

    public static final String MESSAGE_INVALID_FORMAT = "Your list command format is invalid...";
    public static final String MESSAGE_USAGE = "Usage: list";
    /**
     * Lists out the information of all tasks provided by the list of tasks
     * into the provided ui.
     *
     * @param tasks   List of tasks.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    @Override
    public CommandResult execute(TaskList tasks, Storage storage) throws CommandExecutionException {
        return new CommandResult(String.format(RESPONSE_SUCCESS, tasks));
    }
}
