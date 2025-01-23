import errors.CommandParseException;
import errors.UnsupportedCommandException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Olivero {

    private static final String GREETING_MESSAGE = "Howdy-do! I'm Olivero, What can I do for you?";
    private static final String EXIT_MESSAGE = "Bye-bye. See you soon!";

    private static final String SEPARATOR = "____________________________________________________________";
    private static final String END_TOKEN = "bye";
    private static final String BY_TOKEN = " /by ";
    private static final String FROM_TOKEN = " /from ";
    private static final String TO_TOKEN = " /to ";

    private static final String ERROR_MESSAGE = "W-WHAT?! I do not understand what you just said :(";


    public static void speak(String message) {
        System.out.println("\t" + SEPARATOR);
        // add tabs after new line chars
        String formattedMessage = message.replace("\n", "\n\t");
        System.out.println("\t" + formattedMessage + "\n");
        System.out.println("\t" + SEPARATOR + "\n");
    }

    public static String generateTaskResponse(Task task, TaskList taskList) {
        int numTasks = taskList.getTaskSize();
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + taskList.getTaskSize()
                + " " + "task(s) in the list.";
    }

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

        int toEndId = toStartId + TO_TOKEN.length();

        String description = argumentString.substring(0, fromStartId).strip();
        String fromDate = argumentString.substring(fromEndId, toStartId).strip();
        String toDate = argumentString.substring(toEndId).strip();
        if (description.isBlank()) {
            throw new CommandParseException("HUH? You can't have an empty Event description...");
        }
        // TODO: error handling for date format in the future
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
        int byEndId = byStartId + BY_TOKEN.length();
        String description = argumentString.substring(0, byStartId).strip();
        String endDate = argumentString.substring(byEndId).strip();
        // TODO: error handling for date format in the future
        if (description.isBlank()) {
            throw new CommandParseException("HUH? You can't have an empty deadline task description...");
        }
        return new Deadline(description, endDate, false);
    }

    private static ToDo parseToDoCommand(String argumentString) throws CommandParseException {
        if (argumentString.isBlank()) {
            throw new CommandParseException("HUH? You can't have an empty Todo...");
        }
        return new ToDo(argumentString.strip(), false);
    }

    public static void main(String[] args) {
        // initialise resources
        TaskList taskList = new TaskList();
        speak(GREETING_MESSAGE);
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
                    speak(generateTaskResponse(task, taskList));
                    break;
                }
                case DEADLINE: {
                    Task task = parseDeadlineCommand(argumentString);
                    taskList.addTask(task);
                    speak(generateTaskResponse(task, taskList));
                    break;
                }
                case EVENT: {
                    Task task = parseEventCommand(argumentString);
                    taskList.addTask(task);
                    speak(generateTaskResponse(task, taskList));
                    break;
                }
                case LIST: {
                    speak("Here are the tasks in your list:\n" + taskList);
                    break;
                }
                case MARK: {
                    int taskNumber = Integer.parseInt(argumentString.strip());
                    taskList.markTaskAt(taskNumber);
                    speak("Cool! I've marked this task as done: \n " +
                            taskList.getTaskDescription(taskNumber));
                    break;
                }
                case UNMARK: {
                    int taskNumber = Integer.parseInt(argumentString.strip());
                    taskList.unmarkTaskAt(taskNumber);
                    speak("Alright, I've un-marked this task: \n " +
                            taskList.getTaskDescription(taskNumber));
                    break;
                }
                case DELETE: {
                    int taskNumber = Integer.parseInt(argumentString.strip());
                    Task removedTask = taskList.removeTaskAt(taskNumber);
                    // TODO: merge below into generateTaskResponse
                    speak("OK, I've removed this task: \n "
                            + removedTask
                            + "\nNow you have "
                            + taskList.getTaskSize() + " task(s) in the list.");
                    break;
                }
                case BYE: {
                    speak(EXIT_MESSAGE);
                    finished = true;
                    break;
                }
                }
            } catch (CommandParseException e) {
                speak(e.getMessage());
            } catch (UnsupportedCommandException e) {
                speak(ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                speak("Did you pass in a valid integer? Your input: "
                        + e.getMessage());
            } catch (IllegalArgumentException e) {
                speak("Oh dear-o! " + e.getMessage());
            }
            if (finished) break;
        }
    }
}
