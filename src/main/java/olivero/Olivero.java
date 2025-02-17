package olivero;


import olivero.commands.Command;
import olivero.commands.CommandResult;
import olivero.common.Responses;
import olivero.exceptions.CommandExecutionException;
import olivero.exceptions.CommandParseException;
import olivero.exceptions.StorageLoadException;
import olivero.parsers.Parser;
import olivero.storage.Storage;
import olivero.tasks.TaskList;

/**
 * Represents the main entry point for the program.
 */
public class Olivero {
    private TaskList taskList;
    private final Storage storage;

    private final Parser commandParser;

    /**
     * Constructs an {@code Olivero} object with the provide file path
     * for saving data.
     *
     * @param storagePath The path to save data files to.
     */
    public Olivero(String storagePath) {
        this.storage = new Storage(storagePath);
        this.commandParser = new Parser();
    }

    private TaskList loadTaskList() throws StorageLoadException {
        return new TaskList(storage.load());
    }

    /**
     * Attempts to set up resources used by Olivero.
     * If the file is not found or is corrupted, a relevant string response
     * is returned.
     * Otherwise, the default greeting response is returned.
     *
     * @return A displayable message response after setting up.
     */
    public String setupResources() {
        try {
            this.taskList = loadTaskList();
        } catch (StorageLoadException e) {
            this.taskList = new TaskList();

            switch (e.getReason()) {
            case DATA_CORRUPT -> {
                return Responses.RESPONSE_SAVE_FILE_CORRUPT;
            }
            case DATA_MISSING -> {
                return Responses.RESPONSE_SAVE_FILE_NOT_FOUND;
            }
            default -> {
                return Responses.RESPONSE_SAVE_FILE_ERROR;
            }
            }
        }
        return Responses.GREETING_MESSAGE;
    }

    /**
     * Returns the execution result of running a raw user command.
     *
     * @param rawCommand The raw command string input by the user.
     * @return The result of running the command.
     * @throws CommandExecutionException If an error occurs during command execution.
     */
    public CommandResult runCommand(String rawCommand) throws CommandExecutionException {
        try {
            Command command = commandParser.parseCommand(rawCommand);
            return command.execute(taskList, storage);
        } catch (CommandParseException e) {
            return new CommandResult(e.getMessage());
        }

    }
}
