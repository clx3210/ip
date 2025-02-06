package olivero.parsers.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivero.commands.DeadlineCommand;
import olivero.common.Responses;
import olivero.exceptions.CommandParseException;
import olivero.parsers.DateParser;
import olivero.tasks.Deadline;

/**
 * Represents a parser responsible for parsing command arguments into a {@link DeadlineCommand} object.
 */
public class DeadlineCommandParser extends CommandParser<DeadlineCommand> {
    public static final Pattern DEADLINE_ARGUMENT_FORMAT = Pattern.compile(
            " (?<description>.*) /by (?<endDate>.*)");
    private DeadlineCommand setupDeadline(String description, String endDateString) throws CommandParseException {
        if (description.isBlank()) {
            throw new CommandParseException(DeadlineCommand.MESSAGE_EMPTY_DESCRIPTION);
        }

        LocalDateTime endDate;
        try {
            endDate = DateParser.parseInputDate(endDateString);
        } catch (DateTimeParseException e) {
            throw new CommandParseException(Responses.RESPONSE_INVALID_DATE_FORMAT);
        }

        return new DeadlineCommand(new Deadline(description, endDate, false));
    }

    @Override
    public DeadlineCommand parse(String arguments) throws CommandParseException {
        final Matcher matcher = DEADLINE_ARGUMENT_FORMAT.matcher(arguments);
        if (!matcher.matches()) {
            throw new CommandParseException(
                    DeadlineCommand.MESSAGE_INVALID_FORMAT,
                    DeadlineCommand.MESSAGE_USAGE);
        }
        final String description = matcher.group("description");
        final String endDate = matcher.group("endDate");

        return setupDeadline(description, endDate);
    }
}
