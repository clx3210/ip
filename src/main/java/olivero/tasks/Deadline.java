package olivero.tasks;

import olivero.parsers.DateParser;

import java.time.LocalDateTime;

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
       int doneStatus = isDone() ? 1 : 0;
       String dateString = DateParser.asInputDateString(endDate);
       return "D" + " | " + doneStatus + " | " + getDescription() + " | " + dateString;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String dateString = DateParser.asDisplayDateString(endDate);
        return "[D]" + super.toString() + " (by: " + dateString + ")";
    }
}
