package olivero.commands;

import java.util.HashSet;
import java.util.Set;

import olivero.common.Responses;
import olivero.exceptions.CommandExecutionException;
import olivero.exceptions.StorageSaveException;
import olivero.storage.Storage;
import olivero.tasks.TaskList;

/**
 * Marks a single or range of task(s) as done.
 */
public class MarkCommand extends Command {

    public static final String RESPONSE_SUCCESS = "Cool! I've marked the following task(s) as done:"
            + System.lineSeparator()
            + "%s";

    public static final String MESSAGE_INVALID_FORMAT = "Your mark command format is invalid...";

    public static final String MESSAGE_USAGE = "Usages:" + System.lineSeparator()
            + "1. mark <task number>"
            + System.lineSeparator()
            + "2. mark -m <taskNo 1> <taskNo 2> ... <taskNo K>"
            + System.lineSeparator()
            + "3. mark -m <startTaskNo>-<endTaskNo>";


    private final Set<Integer> taskNumbers;

    /**
     * Constructs an executable command to mark a task at the
     * given task number in a task list as completed.
     *
     * @param taskNumber The task number associated with the task to be
     *                   marked as complete.
     */
    public MarkCommand(int taskNumber) {
        this.taskNumbers = new HashSet<>();
        taskNumbers.add(taskNumber);
    }

    /**
     * Constructs an executable command to mark tasks at the given task numbers.
     *
     * @param taskNumbers The set of task numbers associated with the tasks to be marked.
     */
    public MarkCommand(Set<Integer> taskNumbers) {
        this.taskNumbers = taskNumbers;
    }

    /**
     * Marks a task at the given task number specified from the constructor as complete.
     * <p> If the given task number is out of the range of the provided list of tasks or
     * the task fails to save into storage, a context-specific invalid message is
     * displayed on the provided {@code ui}.
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

            for (int taskNumber : taskNumbers) {
                tasks.markTaskAt(taskNumber);
            }

            TaskList affectedTasks = tasks.filter((taskNumber, task) ->
                    taskNumbers.contains(taskNumber));
            storage.save(tasks);

            return new CommandResult(
                    String.format(RESPONSE_SUCCESS, affectedTasks));
        } catch (StorageSaveException e) {
            throw new CommandExecutionException(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }

}
