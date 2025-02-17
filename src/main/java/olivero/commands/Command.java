package olivero.commands;

import olivero.exceptions.CommandExecutionException;
import olivero.storage.Storage;
import olivero.tasks.TaskList;

/**
 * Represents the base class for executable commands to be extended from.
 */
public abstract class Command {

    /**
     * Performs the tasks according to the behaviour of the command when called.
     *
     * @param tasks   List of tasks.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    public abstract CommandResult execute(TaskList tasks, Storage storage) throws CommandExecutionException;
}
