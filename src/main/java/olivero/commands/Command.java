package olivero.commands;

import olivero.storage.Storage;
import olivero.tasks.TaskList;
import olivero.ui.Ui;

/**
 * The base representation for executable commands to be extended from.
 */
public abstract class Command {

    /**
     * Performs the tasks according to the behaviour of the command when called.
     *
     * @param tasks List of tasks.
     * @param ui The User interface for the command to output messages to during execution.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    /**
     * Returns a boolean flag specifying if the executing program should exit.
     *
     * @return True if this command terminates program execution; False otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
