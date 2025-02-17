package olivero.parsers.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.common.DateUtils;
import olivero.exceptions.TaskParseException;
import olivero.tasks.Event;

/**
 * Represents a parser for parsing Event tasks.
 */
public class EventParser extends TaskParser<Event> {

    private static final String ERROR_INVALID_ARGUMENT_FORMAT = "Invalid argument format "
            + "for Event task.";
    private static final String ERROR_INVALID_DATE_FORMAT = "Invalid ending date time format.";
    private static final String ERROR_INVALID_DATE_ORDER = "Start date should not be "
            + "after the End date.";


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
                throw new TaskParseException(ERROR_INVALID_ARGUMENT_FORMAT);
            }

            String isDoneString = matcher.group("isDone");
            String startDateString = matcher.group("startDate");
            String endDateString = matcher.group("endDate");
            String description = matcher.group("description");

            assert isDoneString.equals(TASK_DONE) || isDoneString.equals(TASK_NOT_DONE)
                    : "isDoneString should be 0 or 1";

            boolean isDone = isDoneString.equals(TASK_DONE);
            LocalDateTime startDate = DateUtils.parseInputDate(startDateString);
            LocalDateTime endDate = DateUtils.parseInputDate(endDateString);

            if (startDate.isAfter(endDate)) {
                throw new TaskParseException(ERROR_INVALID_DATE_ORDER);
            }

            return new Event(description, startDate, endDate, isDone);
        } catch (DateTimeParseException e) {
            throw new TaskParseException(ERROR_INVALID_DATE_FORMAT);
        }
    }
}
