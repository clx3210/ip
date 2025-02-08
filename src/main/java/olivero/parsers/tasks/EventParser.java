package olivero.parsers.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.exceptions.TaskParseException;
import olivero.parsers.DateParser;
import olivero.tasks.Event;

public class EventParser extends TaskParser<Event> {
    private static final Pattern EVENT_TASK_FORMAT = Pattern.compile(
            "E" + TaskParser.SEPARATOR_REGEX
                    + "(?<isDone>[01])"
                    + TaskParser.SEPARATOR_REGEX
                    + "(?<description>.*)"
                    + TaskParser.SEPARATOR_REGEX
                    + "(?<startDate>.*)"
                    + TaskParser.SEPARATOR_REGEX
                    + "(?<endDate>.*)");
    @Override
    public Event parse(String taskString) throws TaskParseException {
        try {
            final Matcher matcher = EVENT_TASK_FORMAT.matcher(taskString.trim());
            if (!matcher.matches()) {
                throw new TaskParseException("Invalid argument format for Event task.");
            }

            String isDoneString = matcher.group("isDone");
            String description = matcher.group("description").replaceAll(ESCAPE_REGEX + "\\|", "|");
            String startDateString = matcher.group("startDate");
            String endDateString = matcher.group("endDate");

            assert isDoneString.equals("0") || isDoneString.equals("1") : "isDoneString should be 0 or 1";

            boolean isDone = isDoneString.equals("1");
            LocalDateTime startDate = DateParser.parseInputDate(startDateString);
            LocalDateTime endDate = DateParser.parseInputDate(endDateString);

            if (startDate.isAfter(endDate)) {
                throw new TaskParseException("Start date should not be after the End date.");
            }

            return new Event(description, startDate, endDate, isDone);
        } catch (DateTimeParseException e) {
            throw new TaskParseException("Invalid ending date time format.");
        }
    }
}
