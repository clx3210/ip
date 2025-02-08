package olivero.tasks;

import java.time.LocalDateTime;

import olivero.parsers.DateParser;
import olivero.parsers.tasks.TaskParser;


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
        String dateString = DateParser.formatForInput(endDate);
        String formattedDescription = getDescription()
                .replaceAll("\\|", TaskParser.ESCAPED_SEPARATOR_TOKEN);

        return "D" + " | " + doneStatus + " | " + formattedDescription + " | " + dateString;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String dateString = DateParser.formatForDisplay(endDate);
        return "[D]" + super.toString() + " (by: " + dateString + ")";
    }
}
