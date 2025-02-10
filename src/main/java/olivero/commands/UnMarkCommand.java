package olivero.commands;

import java.util.HashSet;
import java.util.Set;

import olivero.common.Responses;
import olivero.exceptions.CommandExecutionException;
import olivero.exceptions.StorageSaveException;
import olivero.storage.Storage;
import olivero.tasks.TaskList;

/**
 * Un marks a given task.
 */
public class UnMarkCommand extends Command {

    public static final String RESPONSE_SUCCESS = "Alright, I've un-marked the following task(s):"
            + System.lineSeparator()
            + "%s";

    public static final String MESSAGE_INVALID_FORMAT = "Your unmark command format is invalid...";
    public static final String MESSAGE_USAGE = "Usages:" + System.lineSeparator()
            + "1. unmark <task number>"
            + System.lineSeparator()
            + "2. unmark -m <taskNo 1> <taskNo 2> ... <taskNo K>"
            + System.lineSeparator()
            + "3. unmark -m <startTaskNo>-<endTaskNo>";
    private final Set<Integer> taskNumbers;


    /**
     * Creates an UnMarkCommand instance with the specified 1-indexed task number
     * in a task list.
     *
     * @param taskNumber the task number for a task in the task list.
     */
    public UnMarkCommand(int taskNumber) {
        this.taskNumbers = new HashSet<>();
        taskNumbers.add(taskNumber);
    }

    public UnMarkCommand(Set<Integer> taskNumberProducer) {
        this.taskNumbers = taskNumberProducer;
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
        assert tasks != null;
        assert storage != null;
        try {
            int taskSize = tasks.getTaskSize();
            CommandUtils.validateTaskNumbers(taskNumbers, taskSize);

            for (int taskNumber : taskNumbers) {
                tasks.unmarkTaskAt(taskNumber);
            }
            TaskList affectedTasks = tasks.filter((taskNumber, task) ->
                    taskNumbers.contains(taskNumber));

            storage.save(tasks);
            return new CommandResult(String.format(RESPONSE_SUCCESS, affectedTasks));
        } catch (StorageSaveException e) {
            throw new CommandExecutionException(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
