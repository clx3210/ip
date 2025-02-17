package olivero.tasks;

import olivero.parsers.tasks.TaskParseUtils;

/**
 * Represents an abstract task to be extended from.
 */
public abstract class Task {
    private final String description;
    private boolean isDone;

    /**
     * Constructs a default implementation of a subclass of {@code Task} object.
     *
     * @param description The task description.
     * @param isDone The task completion status.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Sets the completion status of the task to the provided boolean value.
     *
     * @param isDone The provided boolean value representing task completion status.
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns true if this task is completed and false otherwise.
     *
     * @return Task completion status.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the task description.
     *
     * @return Task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the current {@code Task} object as a formatted string that can
     * be parsed or reconstructed to the original {@code Task} object or its subtypes.
     *
     * @return Formatted task string.
     */
    public String toFormattedString() {
        String doneStatus = TaskParseUtils.prepareDoneStatus(isDone);
        return TaskParseUtils.formatTask(doneStatus, description);
    }

    /**
     * Returns the string representation of this task object or its subtype.
     *
     * @return Task string representation.
     */
    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }
}
