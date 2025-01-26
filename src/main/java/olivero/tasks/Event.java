package olivero.tasks;

import java.time.LocalDateTime;

import olivero.parsers.DateParser;

public class Event extends Task {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate, boolean isDone) {
        super(description, isDone);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        String endDateString = DateParser.asDisplayDateString(endDate);
        String startDateString = DateParser.asDisplayDateString(startDate);

        return "[E]" + super.toString() + " (from: " + startDateString
                + " to: " + endDateString + ")";
    }

    @Override
    public String toFormattedString() {
        int doneStatus = isDone() ? 1 : 0;
        String startDateString = DateParser.asInputDateString(startDate);
        String endDateString = DateParser.asInputDateString(endDate);

        return "E" + " | " + doneStatus + " | " + getDescription() + " | "
                + startDateString + " | " + endDateString;
    }
}
