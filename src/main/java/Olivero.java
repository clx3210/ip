import errors.TaskParseException;
import errors.UnsupportedCommandException;

import java.text.ParseException;
import java.util.Scanner;

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
        String pluraliseTask = numTasks != 1 ? "tasks" : "task";
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + taskList.getTaskSize()
                + " " + pluraliseTask + " in the list.";
    }

    private static Event parseEventTask(String argumentString) throws TaskParseException  {
        int fromStartId = argumentString.indexOf(FROM_TOKEN);
        if (fromStartId == -1) {
            String errorMessage = """
                        Oh no!? Did you correctly specify the "/from <start date>" of the event?
                        Example usage: event <description> /from <start date> /to <end date>""";
            throw new TaskParseException(errorMessage);
        }
        int fromEndId = fromStartId + FROM_TOKEN.length();

        int toStartId = argumentString.indexOf(TO_TOKEN);
        if (toStartId == -1) {
            String errorMessage = """
                        Oh no!? Did you correctly specify the "/to <end date>" of the event?
                        Example usage: event <description> /from <start date> /to <end date>""";
            throw new TaskParseException(errorMessage);
        }
        // check for invalid ordering of /from and /to
        if (toStartId < fromStartId) {
            String errorMessage = """
                        Oh no!? Did you mix up the order of /from and /to?
                        Example usage: event <description> /from <start date> /to <end date>""";
            throw new TaskParseException(errorMessage);
        }

        int toEndId = toStartId + TO_TOKEN.length();

        String description = argumentString.substring(0, fromStartId).strip();
        String fromDate = argumentString.substring(fromEndId, toStartId).strip();
        String toDate = argumentString.substring(toEndId).strip();
        if (description.isBlank()) {
            throw new TaskParseException("HUH? You can't have an empty Event description...");
        }
        // TODO: error handling for date format in the future
        return new Event(description, fromDate, toDate, false);

    }

    private static Deadline parseDeadlineTask(String argumentString) throws TaskParseException {
        int byStartId = argumentString.indexOf(BY_TOKEN);
        if (byStartId == -1) {
            String errorMessage = """
                        Did you correctly specify the "/by <end date>" of your deadline task?
                        Example usage: deadline <description> /by <start date>""";
            throw new TaskParseException(errorMessage);
        }
        int byEndId = byStartId + BY_TOKEN.length();
        String description = argumentString.substring(0, byStartId).strip();
        String endDate = argumentString.substring(byEndId).strip();
        // TODO: error handling for date format in the future
        if (description.isBlank()) {
            throw new TaskParseException("HUH? You can't have an empty deadline task description...");
        }
        return new Deadline(description, endDate, false);
    }

    private static ToDo parseToDoTask(String argumentString) throws TaskParseException {
        if (argumentString.isBlank()) {
            throw new TaskParseException("HUH? You can't have an empty Todo...");
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
                String command = idx > -1 ? line.substring(0, idx) : line;
                String argumentString = idx > -1 ? line.substring(idx) : line;

                switch (command) {
                case "todo": {
                    Task task = parseToDoTask(argumentString);
                    taskList.addTask(task);
                    speak(generateTaskResponse(task, taskList));
                    break;
                }
                case "deadline": {
                    Task task = parseDeadlineTask(argumentString);
                    taskList.addTask(task);
                    speak(generateTaskResponse(task, taskList));
                    break;
                }
                case "event": {
                    Task task = parseEventTask(argumentString);
                    taskList.addTask(task);
                    speak(generateTaskResponse(task, taskList));
                    break;
                }
                case "list": {
                    speak("Here are the tasks in your list:\n" + taskList);
                    break;
                }
                case "mark": {
                    int taskNumber = Integer.parseInt(argumentString);
                    taskList.markTaskAt(taskNumber);
                    speak("Cool! I've marked this task as done: \n " +
                            taskList.getTaskDescription(taskNumber));

                    break;
                }
                case "unmark": {
                    int taskNumber = Integer.parseInt(argumentString);
                    taskList.unmarkTaskAt(taskNumber);
                    speak("Alright, I've un-marked this task: \n " +
                            taskList.getTaskDescription(taskNumber));
                    break;
                }
                case END_TOKEN: {
                    speak(EXIT_MESSAGE);
                    finished = true;
                    break;
                }
                default: {
                    throw new UnsupportedCommandException();
                }
                }
            } catch (TaskParseException e) {
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
