package olivero.tasks;

import java.time.LocalDateTime;

import olivero.common.DateUtils;
import olivero.parsers.tasks.TaskParseUtils;

/**
 * Represents an Event task.
 */
public class Event extends Task {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    /**
     * Constructs an Event task object containing a task description,
     * a start date, end date and a task completion status.
     *
     * @param description The task description of the event task.
     * @param startDate The starting date of the event task.
     * @param endDate The end date of the event task.
     * @param isDone The completion status of the event task.
     */
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate, boolean isDone) {
        super(description, isDone);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        String endDateString = DateUtils.formatForDisplay(endDate);
        String startDateString = DateUtils.formatForDisplay(startDate);

        return "[" + TaskType.EVENT.getValue() + "]" + super.toString() + " (from: " + startDateString
                + " to: " + endDateString + ")";
    }

    @Override
    public String toFormattedString() {
        String startDateString = DateUtils.formatForInput(startDate);
        String endDateString = DateUtils.formatForInput(endDate);

        return TaskParseUtils.formatTask(
                TaskType.EVENT.getValue(),
                super.toFormattedString(),
                startDateString,
                endDateString);
    }
}
