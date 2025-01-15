import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Olivero {

    private static final String SEPARATOR =  "____________________________________________________________";
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
    public static void main(String[] args) {
        // initialise resources
        TaskList taskList = new TaskList();
        speak(GREETING_MESSAGE);
        // bot is ready: echo user input until 'bye' is read
        Scanner scanner = new Scanner(System.in);
        boolean finished = false;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] arguments = line.split("\\s+", 2);
            switch(arguments[0]) {
                case "read", "return" : {
                    Task task = new Task(line, false);
                    taskList.addTask(task);
                    speak("added: " + task);
                    break;
                }
                case "list": {
                    speak(taskList.toString());
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
