package olivero.parsers.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.EventCommand;
import olivero.common.DateUtils;
import olivero.common.Responses;
import olivero.exceptions.CommandParseException;
import olivero.tasks.Event;

/**
 * Represents the parser for parsing arguments into a {@link EventCommand} object.
 */
public class EventCommandParser extends CommandParser<EventCommand> {
    public static final Pattern EVENT_COMMAND_FORMAT = Pattern.compile(
            " (?<description>.*) /from (?<fromDate>.*) /to (?<toDate>.*)");
    private EventCommand setupEvent(String description, String fromDateString,
                                    String toDateString) throws CommandParseException {
        if (description.isBlank()) {
            throw new CommandParseException(EventCommand.MESSAGE_EMPTY_DESCRIPTION);
        }
        try {
            LocalDateTime fromDate = DateUtils.parseInputDate(fromDateString);
            LocalDateTime toDate = DateUtils.parseInputDate(toDateString);

            if (fromDate.isAfter(toDate)) {
                throw new CommandParseException(Responses.RESPONSE_INVALID_DATE_ORDER);
            }

            return new EventCommand(new Event(description, fromDate, toDate, false));
        } catch (DateTimeParseException e) {
            throw new CommandParseException(Responses.RESPONSE_INVALID_DATE_FORMAT);
        }
    }

    @Override
    public EventCommand parse(String arguments) throws CommandParseException {
        final Matcher matcher = EVENT_COMMAND_FORMAT.matcher(arguments);
        if (!matcher.matches()) {
            throw new CommandParseException(
                    EventCommand.MESSAGE_INVALID_FORMAT,
                    EventCommand.MESSAGE_USAGE);
        }
        assert matcher.groupCount() == 3;

        final String description = matcher.group("description");
        final String fromDate = matcher.group("fromDate");
        final String toDate = matcher.group("toDate");
        return setupEvent(description, fromDate, toDate);
    }
}
