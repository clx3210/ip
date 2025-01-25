import errors.CommandParseException;
import errors.UnsupportedCommandException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

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
        LocalDateTime fromDate, toDate;
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
                    DeadlineCommand.MESSAGE_INVALID_DATE,
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
            throw new CommandParseException(ToDoCommand.MESSAGE_EMPTY_DESCRIPTION);
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
                    Responses.RESPONSE_INVALID_NUMBER_FORMAT
                            + e.getMessage());
        }
    }

    private UnMarkCommand setupUnMark(String arguments) throws CommandParseException {
        try {
            int taskNumber = Integer.parseInt(arguments.strip());
            return new UnMarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new CommandParseException(
                    Responses.RESPONSE_INVALID_NUMBER_FORMAT
                            + e.getMessage());
        }
    }

    private DeleteCommand setupDelete(String arguments) throws CommandParseException {
        try {
            int taskNumber = Integer.parseInt(arguments.strip());
            return new DeleteCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new CommandParseException(
                    Responses.RESPONSE_INVALID_NUMBER_FORMAT
                            + e.getMessage());
        }
    }

    private ByeCommand setupExit() {
        return new ByeCommand();
    }

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
            case BYE -> setupExit();
        };
    }
}
