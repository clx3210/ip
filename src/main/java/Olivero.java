import java.util.Scanner;
public class Olivero {

    private static final String SEPARATOR =  "____________________________________________________________";
    private static final String GREETING_MESSAGE = "Howdy-do! I'm Olivero, What can I do for you?";
    private static final String EXIT_MESSAGE = "Bye-bye. See you soon!";
    public static void speak(String message) {
        System.out.println("\t" + SEPARATOR);
        System.out.println("\t" + message + "\n");
        System.out.println("\t" + SEPARATOR + "\n");
    }
    public static void main(String[] args) {
        speak(GREETING_MESSAGE);
        // echo user input until 'bye' is read
        String endToken = "bye";
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.equals(endToken)) {
                speak(EXIT_MESSAGE);
                break;
            } else {
                speak(line);
            }
        }
    }
}
