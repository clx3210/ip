package olivero;

import olivero.commands.Command;
import olivero.common.Responses;
import olivero.exceptions.CommandParseException;
import olivero.exceptions.StorageLoadException;
import olivero.parsers.Parser;
import olivero.storage.Storage;
import olivero.tasks.TaskList;
import olivero.ui.Ui;

/**
 * Represents the main entry point for the program.
 */
public class Olivero {
    private TaskList taskList;
    private final Storage storage;

    private final Ui textUi;

    private final Parser commandParser;

    /**
     * Constructs an {@code Olivero} object with the provide file path
     * for saving data.
     *
     * @param storagePath The path to save data files to.
     */
    public Olivero(String storagePath) {
        this.storage = new Storage(storagePath);
        this.textUi = new Ui();
        this.commandParser = new Parser();
    }

    /**
     * Starts the entire program.
     *
     * @param args Optional command line arguments passed into the program.
     */
    public static void main(String[] args) {
        new Olivero("data/tasks.txt").run();
    }

    /**
     * Tries to load previously saved tasks from disk if present,
     * otherwise a new task list is used.
     */
    public void setupResources() {
        try {
            this.taskList = new TaskList(storage.load());
        } catch (StorageLoadException e) {
            this.taskList = new TaskList();

            switch (e.getReason()) {
            case DATA_CORRUPT:
                textUi.displayError(Responses.RESPONSE_SAVE_FILE_CORRUPT);
                break;
            case DATA_MISSING:
                textUi.displayError(Responses.RESPONSE_SAVE_FILE_NOT_FOUND);
                break;
            default:
                break;
            }
        }
    }

    /**
     * Setups the necessary resources for the bot,
     * then starts the bot in a loop.
     */
    public void run() {
        textUi.displayGreetingMessage();
        setupResources();
        loop();
    }

    /**
     * Loops repeatedly to read user input, parse and execute supported commands
     * until user enters an exit command.
     */
    public void loop() {
        boolean isExit = false;
        while (!isExit) {
            try {
                String rawCommand = textUi.readCommand();
                Command command = commandParser.parse(rawCommand);
                command.execute(taskList, textUi, storage);
                isExit = command.isExit();
            } catch (CommandParseException e) {
                textUi.displayError(e.getMessage());
            }
        }
    }
}
