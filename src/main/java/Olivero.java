import java.util.Scanner;
public class Olivero {

    private static final String SEPARATOR =  "\n____________________________________________________________";
    private static final String GREETING_MESSAGE = "Howdy-do! I'm Olivero, What can I do for you?";
    private static final String EXIT_MESSAGE = "Bye-bye. See you soon!";
    public static void speak(String message) {
        System.out.println(SEPARATOR);
        System.out.println(message + "\n");
        System.out.println(SEPARATOR + "\n");
    }
    public static void main(String[] args) {
        speak(GREETING_MESSAGE);
        speak(EXIT_MESSAGE);
        // TODO: echo user input until 'bye' is read

    }
}
