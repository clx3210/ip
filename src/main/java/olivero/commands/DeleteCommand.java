package olivero.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.util.Pair;
import olivero.common.Responses;
import olivero.exceptions.CommandExecutionException;
import olivero.exceptions.StorageSaveException;
import olivero.storage.Storage;
import olivero.tasks.Task;
import olivero.tasks.TaskList;
import olivero.tasks.TaskUtils;

/**
 * Deletes a single or specified range of task(s).
 */
public class DeleteCommand extends Command {
    public static final String RESPONSE_SUCCESS = "OK, I've removed the following task(s):"
            + System.lineSeparator()
            + "%s"
            + System.lineSeparator()
            + "Now you have %d task(s) in the list.";

    public static final String MESSAGE_INVALID_FORMAT = "Your delete command format is invalid...";
    public static final String MESSAGE_USAGE = "Usages:" + System.lineSeparator()
            + "1. delete <taskNumber>"
            + System.lineSeparator()
            + "2. delete -m <taskNo 1> <taskNo 2> ... <taskNo K>"
            + System.lineSeparator()
            + "3. delete -m <startTaskNo>-<endTaskNo>";


    private final Set<Integer> taskNumbers;


    /**
     * Constructs an executable command to delete the task
     * from a provided task list at the provided task number.
     *
     * @param taskNumber The task number of the task to be deleted from the task list.
     */
    public DeleteCommand(int taskNumber) {
        this.taskNumbers = new HashSet<>();
        taskNumbers.add(taskNumber);
    }

    public DeleteCommand(Set<Integer> taskNumbers) {
        this.taskNumbers = taskNumbers;
    }


    /**
     * Deletes a task with the given task number and saves the resulting task list
     * into the provided storage medium.
     * <p> If the taskNumber provided from the constructor is out of the range of the provided
     * task list, an invalid task number is displayed.
     *
     * @param tasks   List of tasks.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    @Override
    public CommandResult execute(TaskList tasks, Storage storage) throws CommandExecutionException {
        assert tasks != null;
        assert storage != null;
        try {
            int taskSize = tasks.getTaskSize();
            CommandUtils.validateTaskNumbers(taskNumbers, taskSize);
            List<Pair<Integer, Task>> removedTasks = tasks.removeTasksAt(taskNumbers);

            storage.save(tasks);
            return new CommandResult(
                    String.format(RESPONSE_SUCCESS, TaskUtils.toDisplayString(removedTasks), tasks.getTaskSize()));
        } catch (StorageSaveException e) {
            throw new CommandExecutionException(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
