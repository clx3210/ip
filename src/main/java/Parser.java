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
            String errorMessage = """
                        Oh no!? Did you correctly specify the "/from <start date>" of the event?
                        Example usage: event <description> /from <start date> /to <end date>""";
            throw new CommandParseException(errorMessage);
        }
        int fromEndId = fromStartId + FROM_TOKEN.length();

        int toStartId = arguments.indexOf(TO_TOKEN);
        if (toStartId == -1) {
            String errorMessage = """
                        Oh no!? Did you correctly specify the "/to <end date>" of the event?
                        Example usage: event <description> /from <start date> /to <end date>""";
            throw new CommandParseException(errorMessage);
        }
        // check for invalid ordering of /from and /to
        if (toStartId < fromStartId) {
            String errorMessage = """
                        Oh no!? Did you mix up the order of /from and /to?
                        Example usage: event <description> /from <start date> /to <end date>""";
            throw new CommandParseException(errorMessage);
        }

        String description = arguments.substring(0, fromStartId).strip();
        if (description.isBlank()) {
            throw new CommandParseException("HUH? You can't have an empty Event description...");
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
            String errorMessage = """
                        Did you correctly specify the "/by <end date>" of your deadline task?
                        Example usage: deadline <description> /by <start date>""";
            throw new CommandParseException(errorMessage);
        }

        String description = arguments.substring(0, byStartId).strip();
        if (description.isBlank()) {
            throw new CommandParseException(
                    "HUH? You can't have an empty deadline task description...");
        }

        int byEndId = byStartId + BY_TOKEN.length();
        String endDateString = arguments.substring(byEndId).strip();
        LocalDateTime endDate;
        try {
            endDate = DateParser.parseInputDate(endDateString);
        } catch (DateTimeParseException e) {
            throw new CommandParseException(ERROR_DATE_FORMAT_MESSAGE);
        }


        return new DeadlineCommand(new Deadline(description, endDate, false));
    }

    private ToDoCommand setupToDo(String arguments) throws CommandParseException {
        if (arguments.isBlank()) {
            throw new CommandParseException("HUH? You can't have an empty Todo...");
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
            throw new CommandParseException(e.getMessage());
        }
    }

    private UnMarkCommand setupUnMark(String arguments) throws CommandParseException {
        try {
            int taskNumber = Integer.parseInt(arguments.strip());
            return new UnMarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new CommandParseException(e.getMessage());
        }
    }

    private DeleteCommand setupDelete(String arguments) throws CommandParseException {
        try {
            int taskNumber = Integer.parseInt(arguments.strip());
            return new DeleteCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new CommandParseException(e.getMessage());
        }
    }

    private ByeCommand setupExit() {
        return new ByeCommand();
    }

    public Command parse(String rawInput) throws CommandParseException {
        int idx = rawInput.indexOf(" ");
        CommandType commandType;
        try {
            commandType = CommandType.asCommandType(idx > -1 ? rawInput.substring(0, idx) : rawInput);
        } catch (UnsupportedCommandException e) {
            throw new CommandParseException();
        }
        String argumentString = idx > -1 ? rawInput.substring(idx) : rawInput;

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
