import java.util.Scanner;

public class Olivero {

    private static final String SEPARATOR = "____________________________________________________________";
    private static final String GREETING_MESSAGE = "Howdy-do! I'm Olivero, What can I do for you?";
    private static final String EXIT_MESSAGE = "Bye-bye. See you soon!";
    private static final String END_TOKEN = "bye";

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

    private static Event parseEventTask(String argumentString) {
        // TODO: throw custom checked exception
        int fromStartId = argumentString.indexOf("/from");
        int fromEndId = fromStartId + 5;

        int toStartId = argumentString.indexOf("/to");
        int toEndId = toStartId + 3;
        String description = argumentString.substring(0, fromStartId).strip();
        String fromDate = argumentString.substring(fromEndId, toStartId).strip();
        String toDate = argumentString.substring(toEndId).strip();

        return new Event(description, fromDate, toDate, false);
    }

    private static Deadline parseDeadlineTask(String argumentString) {
        // TODO: throw custom checked exception
        int byStartId = argumentString.indexOf("/by");
        int byEndId = byStartId + 3;
        String description = argumentString.substring(0, byStartId).strip();
        String endDate = argumentString.substring(byEndId).strip();

        return new Deadline(description, endDate, false);
    }

    private static ToDo parseToDoTask(String argumentString) {
        // TODO: throw custom checked exception
        return new ToDo(argumentString.strip(), false);
    }

    public static void main(String[] args) {
        // initialise resources
        TaskList taskList = new TaskList();
        speak(GREETING_MESSAGE);
        // bot is ready: echo user input until 'bye' is read
        Scanner scanner = new Scanner(System.in);
        boolean finished = false;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            int idx = line.indexOf(" ");
            String command = idx > -1 ? line.substring(0, idx) : line;
            String argumentString = idx > -1 ? line.substring(idx + 1) : "";
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
                    // original behaviour
                    speak(line);
                }
            }
            if (finished) break;
        }
    }
}
