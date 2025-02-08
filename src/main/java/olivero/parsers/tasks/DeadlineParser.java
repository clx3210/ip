package olivero.parsers.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.common.DateUtils;
import olivero.exceptions.TaskParseException;
import olivero.tasks.Deadline;

/**
 * Represents a parser for parsing Deadline tasks.
 */
public class DeadlineParser extends TaskParser<Deadline> {

    private static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid ending date time format.";
    private static final String MESSAGE_INVALID_ARGUMENT_FORMAT = "Invalid argument format for Deadline task.";
    private static final Pattern DEADLINE_TASK_FORMAT = Pattern.compile(
            "D" + TaskParser.SEPARATOR_REGEX
                    + "(?<isDone>[01])"
                    + TaskParser.SEPARATOR_REGEX
                    + "(?<description>.*)"
                    + TaskParser.SEPARATOR_REGEX
                    + "(?<byDate>.*)");
    @Override
    public Deadline parse(String taskString) throws TaskParseException {
        final Matcher matcher = DEADLINE_TASK_FORMAT.matcher(taskString.trim());
        if (!matcher.matches()) {
            throw new TaskParseException(MESSAGE_INVALID_ARGUMENT_FORMAT);
        }
        try {
            final String isDoneString = matcher.group("isDone");
            final String description = TaskParseUtils.unescapeDescription(matcher.group("description"));
            final String byDateString = matcher.group("byDate");

            assert isDoneString.equals(TASK_NOT_DONE)
                    || isDoneString.equals(TASK_DONE)
                    : "isDoneString should be 0 or 1";

            boolean isDone = isDoneString.equals(TASK_DONE);
            LocalDateTime endDate = DateUtils.parseInputDate(byDateString);
            return new Deadline(description, endDate, isDone);
        } catch (DateTimeParseException e) {
            throw new TaskParseException(MESSAGE_INVALID_DATE_FORMAT);
        }
    }
}
