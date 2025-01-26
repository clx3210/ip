package olivero.commands;

import olivero.storage.Storage;
import olivero.tasks.TaskList;
import olivero.ui.Ui;

/**
 * Displays the string representations of all saved tasks.
 */
public class ListCommand extends Command {

    /**
     * Lists out the information of all tasks provided by the list of tasks
     * into the provided ui.
     *
     * @param tasks List of tasks.
     * @param ui The User interface for the command to output messages to during execution.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.displayListResponse(tasks);
    }
}
