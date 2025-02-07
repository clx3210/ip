package olivero.commands;

import olivero.exceptions.CommandExecutionException;
import olivero.storage.Storage;
import olivero.tasks.TaskList;

/**
 * Finds all tasks with description containing the specified keyword.
 */
public class FindCommand extends Command {


    public static final String MESSAGE_INVALID_FORMAT = "Your find command format is invalid...";

    /** Usage information for the find command. */
    public static final String MESSAGE_USAGE = "Usage: find <non-empty description>";

    public static final String RESPONSE_SUCCESS = "Here are the matching tasks in your list:"
            + System.lineSeparator()
            + "%s";

    private final String keyword;

    /**
     * Constructs an executable command to list all tasks with description matching the
     * provided keyword.
     *
     * @param keyword The search string to match task descriptions against.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public CommandResult execute(TaskList tasks, Storage storage) throws CommandExecutionException {
        String filteredList = tasks
                .filter(task -> task.getDescription().contains(keyword))
                .toString();
        return new CommandResult(String.format(RESPONSE_SUCCESS, filteredList));
    }
}
