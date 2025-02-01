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
     * Setups the necessary resources for the bot.
     *
     * @param storagePath The path to save data files to.
     */
    public Olivero(String storagePath) {
        this.storage = new Storage(storagePath);
        this.commandParser = new Parser();
        loadTaskList();
    }

    /**
     * Tries to load previously saved tasks from disk if present,
     * otherwise a new task list is used.
     */
    private void loadTaskList() {
        try {
            this.taskList = new TaskList(storage.load());
        } catch (StorageLoadException e) {
            this.taskList = new TaskList();
        }
    }

    /**
     * Returns a greeting message to the user.
     * @return Greeting message.
     */
    public String getGreetingMessage() {
        return Responses.GREETING_MESSAGE;
    }

    /**
     * Returns the execution result of running a raw user command.
     * @param rawCommand The raw command string input by the user.
     * @return The result of running the command.
     * @throws CommandExecutionException If an error occurs during command execution.
     */
    public CommandResult runCommand(String rawCommand) throws CommandExecutionException {
        try {
            Command command = commandParser.parse(rawCommand);
            return command.execute(taskList, storage);
        } catch (CommandParseException e) {
            return new CommandResult(e.getMessage());
        }

    }
}
