package olivero.storage;

import java.util.HashMap;

import olivero.exceptions.TaskParseException;
import olivero.exceptions.UnsupportedTaskException;
import olivero.parsers.tasks.DeadlineParser;
import olivero.parsers.tasks.EventParser;
import olivero.parsers.tasks.TaskParser;
import olivero.parsers.tasks.TodoParser;
import olivero.tasks.Task;
import olivero.tasks.TaskList;
import olivero.tasks.TaskType;

/**
 * Represents a decoder for decoding raw string input into a {@code TaskList} object.
 */
public class DataDecoder {

    private static final String MESSAGE_INVALID_TASK_FORMAT = "Invalid task format.";
    private final HashMap<TaskType, TaskParser<? extends Task>> decoders;

    /**
     * Constructs a decoder object for parsing data into a task list.
     */
    public DataDecoder() {
        decoders = new HashMap<>();
        decoders.put(TaskType.TODO, new TodoParser());
        decoders.put(TaskType.EVENT, new EventParser());
        decoders.put(TaskType.DEADLINE, new DeadlineParser());

        assert decoders.size() == TaskType.values().length;
    }

    /**
     * Returns a {@code TaskList} object that has been decoded from the raw string input.
     *
     * @param rawTasks The raw string input containing the list of tasks.
     * @return Parsed {@code TaskList} object.
     * @throws TaskParseException If the provided format of the raw tasks is invalid during parsing.
     */
    public TaskList decode(String rawTasks) throws TaskParseException {
        TaskList taskList = new TaskList();
        if (rawTasks.isEmpty()) {
            return taskList;
        }

        String[] lines = rawTasks.split(System.lineSeparator());

        for (String line : lines) {
            if (line.isBlank()) {
                throw new TaskParseException(MESSAGE_INVALID_TASK_FORMAT);
            }
            try {
                TaskType taskType = TaskType.parseString(line.substring(0, 1));
                Task task = decoders.get(taskType).parse(line);
                taskList.addTask(task);
            } catch (UnsupportedTaskException e) {
                throw new TaskParseException(MESSAGE_INVALID_TASK_FORMAT);
            }
        }
        return taskList;

    }
}
