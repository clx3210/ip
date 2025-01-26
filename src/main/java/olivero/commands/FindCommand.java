package olivero.commands;

import olivero.storage.Storage;
import olivero.tasks.TaskList;
import olivero.ui.Ui;

/**
 * Finds all tasks with description containing the specified keyword.
 */
public class FindCommand extends Command {


    /** Display message for an empty find command description. */
    public static final String MESSAGE_EMPTY_DESCRIPTION = "Search description can't be empty...";

    /** Usage information for the find command. */
    public static final String MESSAGE_USAGE = "Usage: find <non-empty description>";

    private static final String RESPONSE_PREFIX = "Here are the matching tasks in your list:";

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
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        String filteredList = tasks
                .filter(task -> task.getDescription().contains(keyword))
                .toString();
        ui.displayMessage(RESPONSE_PREFIX, filteredList);
    }
}
