package olivero.tasks;

import olivero.parsers.tasks.TaskParseUtils;

/**
 * Represents a Todo task.
 */
public class ToDo extends Task {

    /**
     * Constructs a ToDo task object containing the task description and completion status.
     * @param description The task description of the {@code ToDo} task.
     * @param isDone The completion status of the {@code ToDo} task.
     */
    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toFormattedString() {
        return TaskParseUtils.formatTask(
                TaskType.TODO.getValue(),
                super.toFormattedString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[" + TaskType.TODO.getValue() + "]" + super.toString();
    }
}
