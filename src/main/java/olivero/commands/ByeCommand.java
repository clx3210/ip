package olivero.commands;

import olivero.storage.Storage;
import olivero.tasks.TaskList;
import olivero.ui.Ui;

public class ByeCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.displayExitMessage();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
