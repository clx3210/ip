public class Ui {

    private static final String GREETING_MESSAGE = "Howdy-do! I'm Olivero, What can I do for you?";
    private static final String DIVIDER = "____________________________________________________________";
    private static final String EXIT_MESSAGE = "Bye-bye. See you soon!";
    public void displayGreetingMessage() {
        displayMessage(GREETING_MESSAGE);
    }

    public void displayExitMessage() {
        displayMessage(EXIT_MESSAGE);
    }

    // TODO: remove tabbing output
    public void displayMessage(String message) {
        System.out.println("\t" + DIVIDER);
        // add tabs after new line chars
        String formattedMessage = message.replace("\n", "\n\t");
        System.out.println("\t" + formattedMessage + "\n");
        System.out.println("\t" + DIVIDER + "\n");
    }

}
