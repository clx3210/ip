package olivero.commands;

import olivero.storage.Storage;
import olivero.tasks.TaskList;
import olivero.ui.Ui;

public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    public boolean isExit() {
        return false;
    }
}
