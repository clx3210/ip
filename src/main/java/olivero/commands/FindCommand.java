package olivero.commands;

import java.util.List;

import javafx.util.Pair;
import olivero.storage.Storage;
import olivero.tasks.Task;
import olivero.tasks.TaskList;
import olivero.tasks.TaskUtil;

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
    public CommandResult execute(TaskList tasks, Storage storage) {
        assert tasks != null;
        assert storage != null;
        assert keyword != null;
        List<Pair<Integer, Task>> filteredList = tasks
                .filter((index, task)-> task.getDescription().contains(keyword));
        return new CommandResult(String.format(RESPONSE_SUCCESS, TaskUtil.toDisplayString(filteredList)));
    }

}
