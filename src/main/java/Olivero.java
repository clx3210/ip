import errors.CommandParseException;
import errors.TaskParseException;

import java.io.FileNotFoundException;

public class Olivero {
    private TaskList taskList;
    private final Storage storage;

    private final Ui textUi;

    private final TaskParser taskParser;
    private final Parser commandParser;


    public Olivero(String storagePath) {
        this.storage = new Storage(storagePath);
        this.taskParser = new TaskParser();
        this.textUi = new Ui();
        this.commandParser = new Parser();
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
        new Olivero("data/tasks.txt").run();
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
        boolean isExit = false;
        while (!isExit) {
            try {
                String rawCommand = textUi.readCommand();
                Command command = commandParser.parse(rawCommand);
                command.execute(taskList, textUi, storage);
                isExit = command.isExit();
            } catch (CommandParseException e) {
                textUi.displayMessage(e.getMessage());
            } catch (IllegalArgumentException e) {
                textUi.displayMessage("Oh dear-o! " + e.getMessage());
            }
        }
    }
}
