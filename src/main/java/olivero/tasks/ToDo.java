package olivero.tasks;

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
        int doneStatus = isDone() ? 1 : 0;
        return "T" + " | " + doneStatus + " | " + getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}