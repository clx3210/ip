package olivero.commands;

import olivero.exceptions.CommandExecutionException;
import olivero.storage.Storage;
import olivero.tasks.TaskList;

/**
 * The base representation for executable commands to be extended from.
 */
public abstract class Command {

    /**
     * Performs the tasks according to the behaviour of the command when called.
     *
     * @param tasks   List of tasks.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    public abstract CommandResult execute(TaskList tasks, Storage storage) throws CommandExecutionException;

    /**
     * Returns a boolean flag specifying if the executing program should exit.
     *
     * @return True if this command terminates program execution; False otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
