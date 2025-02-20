package olivero.tasks;

import java.time.LocalDateTime;

import olivero.common.DateUtil;
import olivero.parsers.tasks.TaskParseUtil;


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

    @Override
    public String toFormattedString() {
        String dateString = DateUtil.formatForInput(endDate);

        return TaskParseUtil.formatTask(
                TaskType.DEADLINE.getValue(),
                super.toFormattedString(),
                dateString);
    }

    @Override
    public String toString() {
        String dateString = DateUtil.formatForDisplay(endDate);
        return "[" + TaskType.DEADLINE.getValue() + "]" + super.toString() + " (by: " + dateString + ")";
    }
}
