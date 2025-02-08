package olivero.parsers.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.exceptions.TaskParseException;
import olivero.parsers.DateParser;
import olivero.tasks.Deadline;

public class DeadlineParser extends TaskParser<Deadline> {
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
            throw new TaskParseException("Invalid argument format for Deadline task.");
        }
        try {
            final String isDoneString = matcher.group("isDone");
            final String description = matcher.group("description")
                    .replaceAll(ESCAPE_REGEX + "\\|", "|");
            final String byDateString = matcher.group("byDate");

            assert isDoneString.equals("0") || isDoneString.equals("1") : "isDoneString should be 0 or 1";

            boolean isDone = isDoneString.equals("1");
            LocalDateTime endDate = DateParser.parseInputDate(byDateString);
            return new Deadline(description, endDate, isDone);
        } catch (DateTimeParseException e) {
            throw new TaskParseException("Invalid ending date time format.");
        }
    }
}
