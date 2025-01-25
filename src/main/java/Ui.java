import java.util.Scanner;

public class Ui {

    private static final String DIVIDER = "____________________________________________________________";
    private static final String EXIT_MESSAGE = "Bye-bye. See you soon!";

    private Scanner reader;

    public Ui() {
        reader = new Scanner(System.in);
    }
    public void displayGreetingMessage() {
        displayMessage(Responses.GREETING_MESSAGE);
    }

    public void displayExitMessage() {
        displayMessage(EXIT_MESSAGE);
    }

    /**
     * Prints the bot response to a task command.
     * @param task
     * @param taskList
     */
    public void displayTaskResponse(Task task, TaskList taskList) {
        displayMessage(getTaskResponse(task, taskList));
    }

    public void displayDeleteTaskResponse(Task task, TaskList taskList) {
        displayMessage("OK, I've removed this task: \n "
                + task
                + "\nNow you have "
                + taskList.getTaskSize() + " task(s) in the list.");
    }

    /**
     * Prints the bot response to a 'list' command.
     * @param taskList
     */
    public void displayListResponse(TaskList taskList) {
        displayMessage("Here are the tasks in your list:\n" + taskList);
    }

    public void displayMarkResponse(TaskList taskList, int taskNumber) {
        displayMessage("Cool! I've marked this task as done: \n " +
                taskList.getTaskDescription(taskNumber));
    }

    public void displayUnMarkResponse(TaskList taskList, int taskNumber) {
        displayMessage("Alright, I've un-marked this task: \n " +
                taskList.getTaskDescription(taskNumber));
    }

    private String getTaskResponse(Task task, TaskList taskList) {
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + taskList.getTaskSize()
                + " " + "task(s) in the list.";
    }

    public void displayError(String message) {
        displayMessage("Oh dear-o! " + message);
    }

    // TODO: remove tabbing output
    public void displayMessage(String message) {
        System.out.println("\t" + DIVIDER);
        // add tabs after new line chars
        String formattedMessage = message.replace("\n", "\n\t");
        System.out.println("\t" + formattedMessage + "\n");
        System.out.println("\t" + DIVIDER + "\n");
    }



    public boolean hasNextCommand() {
        return reader.hasNextLine();
    }

    public String readCommand() {
        return reader.nextLine();
    }

}
