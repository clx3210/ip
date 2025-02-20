package olivero.tasks;

import olivero.parsers.tasks.TaskParseUtil;

/**
 * Represents a Todo task.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task object containing the task description and completion status.
     *
     * @param description The task description of the {@code Todo} task.
     * @param isDone The completion status of the {@code Todo} task.
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String toFormattedString() {
        return TaskParseUtil.formatTask(
                TaskType.TODO.getValue(),
                super.toFormattedString());
    }

    @Override
    public String toString() {
        return "[" + TaskType.TODO.getValue() + "]" + super.toString();
    }
}
