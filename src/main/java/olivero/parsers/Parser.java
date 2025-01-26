package olivero.parsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import olivero.commands.ByeCommand;
import olivero.commands.Command;
import olivero.commands.CommandType;
import olivero.commands.DeadlineCommand;
import olivero.commands.DeleteCommand;
import olivero.commands.EventCommand;
import olivero.commands.FindCommand;
import olivero.commands.ListCommand;
import olivero.commands.MarkCommand;
import olivero.commands.ToDoCommand;
import olivero.commands.UnMarkCommand;
import olivero.common.Responses;
import olivero.exceptions.CommandParseException;
import olivero.exceptions.UnsupportedCommandException;
import olivero.tasks.Deadline;
import olivero.tasks.Event;
import olivero.tasks.ToDo;

/**
 * Parses commands from the user input and returns a {@code Command} object.
 */
public class Parser {
    private static final String BY_TOKEN = " /by ";
    private static final String FROM_TOKEN = " /from ";
    private static final String TO_TOKEN = " /to ";
    private EventCommand setupEvent(String arguments) throws CommandParseException {
        int fromStartId = arguments.indexOf(FROM_TOKEN);
        if (fromStartId == -1) {
            throw new CommandParseException(
                    EventCommand.MESSAGE_INVALID_FROM,
                    EventCommand.MESSAGE_USAGE);
        }
        int fromEndId = fromStartId + FROM_TOKEN.length();

        int toStartId = arguments.indexOf(TO_TOKEN);
        if (toStartId == -1) {
            throw new CommandParseException(
                    EventCommand.MESSAGE_INVALID_TO,
                    EventCommand.MESSAGE_USAGE);
        }
        // check for invalid ordering of /from and /to
        if (toStartId < fromStartId) {
            throw new CommandParseException(
                    EventCommand.MESSAGE_INVALID_DATE_ORDER,
                    EventCommand.MESSAGE_USAGE);
        }

        String description = arguments.substring(0, fromStartId).strip();
        if (description.isBlank()) {
            throw new CommandParseException(
                    EventCommand.MESSAGE_EMPTY_DESCRIPTION);
        }

        int toEndId = toStartId + TO_TOKEN.length();
        String fromDateString = arguments.substring(fromEndId, toStartId).strip();
        String toDateString = arguments.substring(toEndId).strip();
        LocalDateTime fromDate;
        LocalDateTime toDate;
        try {
            fromDate = DateParser.parseInputDate(fromDateString);
            toDate = DateParser.parseInputDate(toDateString);
        } catch (DateTimeParseException e) {
            throw new CommandParseException(Responses.RESPONSE_INVALID_DATE_FORMAT);
        }

        if (fromDate.isAfter(toDate)) {
            throw new CommandParseException(Responses.RESPONSE_INVALID_DATE_ORDER);
        }

        return new EventCommand(new Event(description, fromDate, toDate, false));
    }

    private DeadlineCommand setupDeadline(String arguments) throws CommandParseException {
        int byStartId = arguments.indexOf(BY_TOKEN);
        if (byStartId == -1) {
            throw new CommandParseException(
                    DeadlineCommand.MESSAGE_INVALID_BY_TOKEN,
                    DeadlineCommand.MESSAGE_USAGE);
        }

        String description = arguments.substring(0, byStartId).strip();
        if (description.isBlank()) {
            throw new CommandParseException(DeadlineCommand.MESSAGE_EMPTY_DESCRIPTION);
        }

        int byEndId = byStartId + BY_TOKEN.length();
        String endDateString = arguments.substring(byEndId).strip();
        LocalDateTime endDate;
        try {
            endDate = DateParser.parseInputDate(endDateString);
        } catch (DateTimeParseException e) {
            throw new CommandParseException(Responses.RESPONSE_INVALID_DATE_FORMAT);
        }

        return new DeadlineCommand(new Deadline(description, endDate, false));
    }

    private ToDoCommand setupToDo(String arguments) throws CommandParseException {
        if (arguments.isBlank()) {
            throw new CommandParseException(
                    ToDoCommand.MESSAGE_EMPTY_DESCRIPTION,
                    ToDoCommand.MESSAGE_USAGE);
        }
        return new ToDoCommand(new ToDo(arguments.strip(), false));
    }

    private ListCommand setupList() {
        return new ListCommand();
    }

    private MarkCommand setupMark(String arguments) throws CommandParseException {
        try {
            int taskNumber = Integer.parseInt(arguments.strip());
            return new MarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new CommandParseException(
                    String.format(
                            Responses.RESPONSE_INVALID_NUMBER_FORMAT,
                            arguments));
        }
    }

    private UnMarkCommand setupUnMark(String arguments) throws CommandParseException {
        try {
            int taskNumber = Integer.parseInt(arguments.strip());
            return new UnMarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new CommandParseException(
                    String.format(
                            Responses.RESPONSE_INVALID_NUMBER_FORMAT,
                            arguments));
        }
    }

    private DeleteCommand setupDelete(String arguments) throws CommandParseException {
        try {
            int taskNumber = Integer.parseInt(arguments.strip());
            return new DeleteCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new CommandParseException(
                    String.format(
                            Responses.RESPONSE_INVALID_NUMBER_FORMAT,
                            arguments));
        }
    }

    private FindCommand setupFind(String arguments) throws CommandParseException {
        if (arguments.strip().isBlank()) {
            throw new CommandParseException(
                    FindCommand.MESSAGE_EMPTY_DESCRIPTION,
                    FindCommand.MESSAGE_USAGE);
        }
        return new FindCommand(arguments.strip());
    }

    private ByeCommand setupExit() {
        return new ByeCommand();
    }

    /**
     * Parses the raw user input into an executable {@code Command} object,
     * and returns it.
     *
     * @param rawInput The user input containing the raw command string.
     * @return An executable {@code Command} object.
     * @throws CommandParseException If the provided user input cannot be formatted
     * into any of the existing supported {@code Command} types or subtypes.
     */
    public Command parse(String rawInput) throws CommandParseException {
        int idx = rawInput.indexOf(" ");
        CommandType commandType;
        try {
            commandType = CommandType.asCommandType(
                    idx > -1 ? rawInput.substring(0, idx) : rawInput);
        } catch (UnsupportedCommandException e) {
            throw new CommandParseException(Responses.RESPONSE_UNKNOWN_COMMAND);
        }
        String argumentString = idx > -1 ? rawInput.substring(idx) : "";
        return switch (commandType) {
        case TODO -> setupToDo(argumentString);
        case DEADLINE -> setupDeadline(argumentString);
        case EVENT -> setupEvent(argumentString);
        case LIST -> setupList();
        case MARK -> setupMark(argumentString);
        case UNMARK -> setupUnMark(argumentString);
        case DELETE -> setupDelete(argumentString);
        case FIND -> setupFind(argumentString);
        case BYE -> setupExit();
        };
    }
}
