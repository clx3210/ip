package olivero.tasks;

import java.time.LocalDateTime;

import olivero.parsers.DateParser;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String endDateString = DateParser.asDisplayDateString(endDate);
        String startDateString = DateParser.asDisplayDateString(startDate);

        return "[E]" + super.toString() + " (from: " + startDateString
                + " to: " + endDateString + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toFormattedString() {
        int doneStatus = isDone() ? 1 : 0;
        String startDateString = DateParser.asInputDateString(startDate);
        String endDateString = DateParser.asInputDateString(endDate);

        return "E" + " | " + doneStatus + " | " + getDescription() + " | "
                + startDateString + " | " + endDateString;
    }
}
