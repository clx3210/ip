package olivero.commands;

import olivero.storage.Storage;
import olivero.tasks.TaskList;
import olivero.ui.Ui;

public class FindCommand extends Command {

    private static final String RESPONSE_PREFIX = "Here are the matching tasks in your list:";

    public static final String MESSAGE_EMPTY_DESCRIPTION = "Search description can't be empty...";

    public static final String MESSAGE_USAGE = "Usage: find <non-empty description>";

    private final String keyword;


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
