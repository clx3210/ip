package olivero.commands;

import olivero.exceptions.CommandExecutionException;
import olivero.storage.Storage;
import olivero.tasks.TaskList;

/**
 * Ends the program execution.
 */
public class ByeCommand extends Command {
    public static final String MESSAGE_INVALID_FORMAT = "Your bye command format is invalid...";
    public static final String MESSAGE_USAGE = "Usage: bye";
    private static final String MESSAGE_EMPTY = "";

    /**
     * Displays an exit message to the provided ui when called.
     *
     * @param tasks   List of tasks.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    @Override
    public CommandResult execute(TaskList tasks, Storage storage) throws CommandExecutionException {
        return new CommandResult(MESSAGE_EMPTY, true);
    }
}
