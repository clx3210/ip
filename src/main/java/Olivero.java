import errors.CommandParseException;
import errors.TaskParseException;
import errors.UnsupportedCommandException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Olivero {


    private static final String END_TOKEN = "bye";
    private static final String BY_TOKEN = " /by ";
    private static final String FROM_TOKEN = " /from ";
    private static final String TO_TOKEN = " /to ";

    private static final String ERROR_MESSAGE = "W-WHAT?! I do not understand what you just said :(";
    private static final String ERROR_DATE_FORMAT_MESSAGE = """
            Oh... Seems like you formatted your date(s) wrongly?
            Correct date format: yyyy-mm-dd HHmm (e.g. 2019-10-15 1800)
            """;

    private TaskList taskList;
    private final Storage storage;
    private final TaskParser taskParser;
    private final Ui textUi;


    public Olivero() {
        this.storage = new Storage();
        this.taskParser = new TaskParser();
        this.textUi = new Ui();
    }

    // TODO: Move each parser into their own class
    private static Event parseEventCommand(String argumentString) throws CommandParseException {
        int fromStartId = argumentString.indexOf(FROM_TOKEN);
        if (fromStartId == -1) {
            String errorMessage = """
                        Oh no!? Did you correctly specify the "/from <start date>" of the event?
                        Example usage: event <description> /from <start date> /to <end date>""";
            throw new CommandParseException(errorMessage);
        }
        int fromEndId = fromStartId + FROM_TOKEN.length();

        int toStartId = argumentString.indexOf(TO_TOKEN);
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

        String description = argumentString.substring(0, fromStartId).strip();
        if (description.isBlank()) {
            throw new CommandParseException("HUH? You can't have an empty Event description...");
        }

        int toEndId = toStartId + TO_TOKEN.length();
        String fromDateString = argumentString.substring(fromEndId, toStartId).strip();
        String toDateString = argumentString.substring(toEndId).strip();
        LocalDateTime fromDate, toDate;
        try {
            fromDate = DateParser.parseInputDate(fromDateString);
            toDate = DateParser.parseInputDate(toDateString);
        } catch (DateTimeParseException e) {
            throw new CommandParseException(ERROR_DATE_FORMAT_MESSAGE);
        }

        if (fromDate.isAfter(toDate)) {
            throw new CommandParseException("/from date CANNOT be AFTER /to date!!");
        }

        return new Event(description, fromDate, toDate, false);
    }

    private static Deadline parseDeadlineCommand(String argumentString) throws CommandParseException {
        int byStartId = argumentString.indexOf(BY_TOKEN);
        if (byStartId == -1) {
            String errorMessage = """
                        Did you correctly specify the "/by <end date>" of your deadline task?
                        Example usage: deadline <description> /by <start date>""";
            throw new CommandParseException(errorMessage);
        }

        String description = argumentString.substring(0, byStartId).strip();
        if (description.isBlank()) {
            throw new CommandParseException(
                    "HUH? You can't have an empty deadline task description...");
        }

        int byEndId = byStartId + BY_TOKEN.length();
        String endDateString = argumentString.substring(byEndId).strip();
        LocalDateTime endDate;
        try {
            endDate = DateParser.parseInputDate(endDateString);
        } catch (DateTimeParseException e) {
            throw new CommandParseException(ERROR_DATE_FORMAT_MESSAGE);
        }


        return new Deadline(description, endDate, false);
    }

    private static ToDo parseToDoCommand(String argumentString) throws CommandParseException {
        if (argumentString.isBlank()) {
            throw new CommandParseException("HUH? You can't have an empty Todo...");
        }
        return new ToDo(argumentString.strip(), false);
    }

    private TaskList setupTasks() {
        try {
            String taskContents = storage.loadFromFile();
            taskList = taskParser.parse(taskContents);
        } catch (FileNotFoundException e) {
            textUi.displayMessage(Responses.RESPONSE_SAVE_FILE_NOT_FOUND);
        } catch (TaskParseException e) {
            textUi.displayMessage(Responses.RESPONSE_SAVE_FILE_CORRUPT);
        }
        return taskList;
    }


    public static void main(String[] args) {
        new Olivero().run();
    }

    public void setupResources() {
        textUi.displayGreetingMessage();
        this.taskList = setupTasks();
    }

    public void run() {
        setupResources();
        loop();
    }

    public void loop() {
        // bot is ready: echo user input until 'bye' is read
        Scanner scanner = new Scanner(System.in);
        boolean finished = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            try {
                int idx = line.indexOf(" ");
                CommandType command = CommandType.asCommandType(idx > -1 ? line.substring(0, idx) : line);
                String argumentString = idx > -1 ? line.substring(idx) : line;

                switch (command) {
                case TODO: {
                    Task task = parseToDoCommand(argumentString);
                    taskList.addTask(task);
                    storage.saveData(taskList.asFormattedString());
                    textUi.displayTaskResponse(task, taskList);
                    break;
                }
                case DEADLINE: {
                    Task task = parseDeadlineCommand(argumentString);
                    taskList.addTask(task);
                    storage.saveData(taskList.asFormattedString());
                    textUi.displayTaskResponse(task, taskList);
                    break;
                }
                case EVENT: {
                    Task task = parseEventCommand(argumentString);
                    taskList.addTask(task);
                    storage.saveData(taskList.asFormattedString());
                    textUi.displayTaskResponse(task, taskList);
                    break;
                }
                case LIST: {
                    textUi.displayListResponse(taskList);
                    break;
                }
                case MARK: {
                    int taskNumber = Integer.parseInt(argumentString.strip());
                    taskList.markTaskAt(taskNumber);
                    storage.saveData(taskList.asFormattedString());
                    textUi.displayMarkResponse(taskList, taskNumber);
                    break;
                }
                case UNMARK: {
                    int taskNumber = Integer.parseInt(argumentString.strip());
                    taskList.unmarkTaskAt(taskNumber);
                    storage.saveData(taskList.asFormattedString());
                    textUi.displayUnMarkResponse(taskList, taskNumber);
                    break;
                }
                case DELETE: {
                    int taskNumber = Integer.parseInt(argumentString.strip());
                    Task removedTask = taskList.removeTaskAt(taskNumber);
                    storage.saveData(taskList.asFormattedString());
                    textUi.displayDeleteTaskResponse(removedTask, taskList);
                    break;
                }
                case BYE: {
                    textUi.displayExitMessage();
                    finished = true;
                    break;
                }
                }
            } catch (CommandParseException e) {
                textUi.displayMessage(e.getMessage());
            } catch (UnsupportedCommandException e) {
                textUi.displayMessage(ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                textUi.displayInvalidIntegerErrorResponse(e);
            } catch (IllegalArgumentException e) {
                textUi.displayMessage("Oh dear-o! " + e.getMessage());
            } catch (IOException e) {
                textUi.displayMessage(Responses.RESPONSE_CANNOT_SAVE_TASK);
            }
            if (finished) break;
        }
    }
}
