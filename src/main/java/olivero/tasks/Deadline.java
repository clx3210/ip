package olivero.tasks;

import java.time.LocalDateTime;

import olivero.parsers.DateParser;


public class Deadline extends Task {
    private LocalDateTime endDate;
    public Deadline(String description, LocalDateTime endDate, boolean isDone) {
        super(description, isDone);
        this.endDate = endDate;
    }

    @Override
    public String toFormattedString() {
        int doneStatus = isDone() ? 1 : 0;
        String dateString = DateParser.asInputDateString(endDate);
        return "D" + " | " + doneStatus + " | " + getDescription() + " | " + dateString;
    }

    @Override
    public String toString() {
        String dateString = DateParser.asDisplayDateString(endDate);
        return "[D]" + super.toString() + " (by: " + dateString + ")";
    }
}
