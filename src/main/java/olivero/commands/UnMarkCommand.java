package olivero.commands;

import olivero.common.Responses;
import olivero.exceptions.CommandExecutionException;
import olivero.exceptions.StorageSaveException;
import olivero.storage.Storage;
import olivero.tasks.TaskList;

/**
 * Un marks a given task.
 */
public class UnMarkCommand extends Command {

    public static final String RESPONSE_SUCCESS = "Alright, I've un-marked this task: "
            + System.lineSeparator()
            + " %s";

    public static final String MESSAGE_INVALID_FORMAT = "Your unmark command format is invalid...";
    public static final String MESSAGE_USAGE = "Usage: unmark <taskNumber>";
    private final int taskNumber;


    /**
     * Creates an UnMarkCommand instance with the specified 1-indexed task number
     * in a task list.
     *
     * @param taskNumber the task number for a task in the task list.
     */
    public UnMarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Sets the status of the specified task in the provided {@code tasks} as incomplete/undone.
     * <p> Updates the {@code storage} medium with the updated task list and displays
     * a response onto the {@code ui} provided.
     * <p> If the task number given is out of the range of the tasks, then an error message is displayed.
     * Otherwise, if storage saving fails, then an error message is also displayed.
     *
     * @param tasks   List of tasks.
     * @param storage Storage medium for saving or loading tasks from disk.
     */
    @Override
    public CommandResult execute(TaskList tasks, Storage storage) throws CommandExecutionException {
        int taskSize = tasks.getTaskSize();
        if (taskNumber > taskSize || taskNumber <= 0) {
            throw new CommandExecutionException(
                    String.format(
                            Responses.RESPONSE_INVALID_TASK_NUMBER,
                            taskNumber));
        }
        try {
            tasks.unmarkTaskAt(taskNumber);
            storage.save(tasks);
            return new CommandResult(
                    String.format(
                            RESPONSE_SUCCESS,
                            tasks.getTaskDescription(taskNumber)));
        } catch (StorageSaveException e) {
            throw new CommandExecutionException(Responses.RESPONSE_SAVE_FILE_FAILED);
        }

    }
}
