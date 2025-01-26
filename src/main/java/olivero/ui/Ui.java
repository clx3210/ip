package olivero.ui;

import olivero.tasks.TaskList;
import olivero.common.Responses;
import olivero.tasks.Task;

import java.util.Scanner;

/**
 * Represents the User Interface for displaying and receiving
 * user inputs to and from respectively.
 */
public class Ui {

    private static final String DIVIDER = "____________________________________________________________";
    private static final String EXIT_MESSAGE = "Bye-bye. See you soon!";

    private final Scanner reader;

    /**
     * Constructs a {@code Ui} object that takes user input from {@link System#in}
     * and displays output to {@link System#out}.
     */
    public Ui() {
        reader = new Scanner(System.in);
    }

    /**
     * Displays a greeting message to the user.
     */
    public void displayGreetingMessage() {
        displayMessage(Responses.GREETING_MESSAGE);
    }

    /**
     * Displays an exit message to the user.
     */
    public void displayExitMessage() {
        displayMessage(EXIT_MESSAGE);
    }

    /**
     * Displays the response to a task command from the user.
     *
     * @param task The task to be used in the display response.
     * @param taskList The task list to be used in the display response.
     */
    public void displayTaskResponse(Task task, TaskList taskList) {
        displayMessage(getTaskResponse(task, taskList));
    }

    /**
     * Displays the response to a delete task command from the user.
     *
     * @param task The task to be used in the display response.
     * @param taskList The task list to be used in the display response.
     */
    public void displayDeleteTaskResponse(Task task, TaskList taskList) {
        displayMessage("OK, I've removed this task: \n "
                + task
                + "\nNow you have "
                + taskList.getTaskSize() + " task(s) in the list.");
    }

    /**
     * Displays the response to a 'list' command to the user.
     *
     * @param taskList The task list to be used in the display response.
     */
    public void displayListResponse(TaskList taskList) {
        displayMessage("Here are the tasks in your list:\n" + taskList);
    }

    /**
     * Displays the response to a mark task command from the user.
     *
     * @param taskList The task list to be used in the display response.
     * @param taskNumber The task number associated the marked task.
     */
    public void displayMarkResponse(TaskList taskList, int taskNumber) {
        displayMessage("Cool! I've marked this task as done: \n " +
                taskList.getTaskDescription(taskNumber));
    }

    /**
     * Displays the response to an unmark task command from the user.
     *
     * @param taskList The task list to be used in the display response.
     * @param taskNumber The task number associated the unmarked task.
     */
    public void displayUnMarkResponse(TaskList taskList, int taskNumber) {
        displayMessage("Alright, I've un-marked this task: \n " +
                taskList.getTaskDescription(taskNumber));
    }

    private String getTaskResponse(Task task, TaskList taskList) {
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + taskList.getTaskSize()
                + " " + "task(s) in the list.";
    }

    /**
     * Displays error messages to the user.
     *
     * @param message The message to be displayed.
     */
    public void displayError(String message) {
        displayMessage("Oh dear-o! " + message);
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to be displayed to the user.
     */
    public void displayMessage(String message) {
        System.out.println("\t" + DIVIDER);
        // add tabs after new line chars
        String formattedMessage = message.replace("\n", "\n\t");
        System.out.println("\t" + formattedMessage + "\n");
        System.out.println("\t" + DIVIDER + "\n");
    }

    /**
     * Returns the entered user input.
     *
     * @return User input.
     */
    public String readCommand() {
        return reader.nextLine();
    }
}
