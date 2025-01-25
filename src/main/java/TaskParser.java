import errors.TaskParseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class TaskParser {
    public static final String SEPARATOR = " | ";

    public TaskList parse(String rawTasks) throws TaskParseException {
        TaskList taskList = new TaskList();
        String[] lines = rawTasks.split(System.lineSeparator());

        for (String line : lines) {
            String[] args = line.split(Pattern.quote(SEPARATOR));
            if (args.length == 0) {
                throw new IllegalArgumentException();
            }
            switch (args[0]) {
            case "T": {
                if (args.length != 3) {
                    throw new TaskParseException("Invalid arguments length for ToDo task.");
                }
                boolean isDone = Integer.parseInt(args[1]) == 1;
                String description = args[2];
                taskList.addTask(new ToDo(description, isDone));
                break;
            }
            case "D": {
                if (args.length != 4) {
                    throw new TaskParseException("Invalid arguments length for Deadline task.");
                }
                boolean isDone = Integer.parseInt(args[1]) == 1;
                String description = args[2];
                LocalDateTime endDate;
                try {
                    endDate = DateParser.parseInputDate(args[3]);
                } catch (DateTimeParseException e) {
                    throw new TaskParseException("Invalid ending date time format.");
                }
                taskList.addTask(new Deadline(description, endDate, isDone));
                break;
            }
            case "E": {
                if (args.length != 5) {
                    throw new TaskParseException("Invalid arguments length for Event task.");
                }
                boolean isDone = Integer.parseInt(args[1]) == 1;
                String description = args[2];
                LocalDateTime startDate, endDate;
                try {
                    startDate = DateParser.parseInputDate(args[3]);
                    endDate = DateParser.parseInputDate(args[4]);
                } catch (DateTimeParseException e) {
                    throw new TaskParseException("Invalid ending date time format.");
                }
                if (startDate.isAfter(endDate)) {
                    throw new TaskParseException("Start date should not be after the End date.");
                }
                taskList.addTask(new Event(description, startDate, endDate, isDone));
                break;
            }
            default: {
                throw new TaskParseException("Unsupported task type.");
            }
            }
        }
        return taskList;

    }
}
