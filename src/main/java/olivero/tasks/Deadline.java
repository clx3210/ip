package olivero.tasks;

import java.time.LocalDateTime;

import olivero.common.DateUtils;
import olivero.parsers.tasks.TaskParseUtils;


/**
 * Represents a Deadline task.
 */
public class Deadline extends Task {
    private final LocalDateTime endDate;

    /**
     * Constructs a {@code Deadline} object with the given task description, end date
     * and initial completion status.
     *
     * @param description The description of the deadline task.
     * @param endDate The deadline/end date of the deadline task.
     * @param isDone The initial completion status of the deadline task.
     */
    public Deadline(String description, LocalDateTime endDate, boolean isDone) {
        super(description, isDone);
        this.endDate = endDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toFormattedString() {
        String dateString = DateUtils.formatForInput(endDate);

        return TaskParseUtils.formatTask(
                TaskType.DEADLINE.getValue(),
                super.toFormattedString(),
                dateString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String dateString = DateUtils.formatForDisplay(endDate);
        return "[" + TaskType.DEADLINE.getValue() + "]" + super.toString() + " (by: " + dateString + ")";
    }
}
