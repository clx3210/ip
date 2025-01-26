package olivero.commands;

import olivero.storage.Storage;
import olivero.tasks.TaskList;
import olivero.ui.Ui;

/**
 * Ends the program execution.
 */
public class ByeCommand extends Command {

    /**
     * Displays an exit message to the provided ui when called.
     *
     * @param tasks List of tasks.
     * @param ui The User interface for the command to output messages to during execution.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.displayExitMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
