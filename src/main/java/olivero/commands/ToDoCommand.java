package olivero.commands;

import olivero.common.Responses;
import olivero.exceptions.StorageSaveException;
import olivero.storage.Storage;
import olivero.tasks.TaskList;
import olivero.tasks.ToDo;
import olivero.ui.Ui;

public class ToDoCommand extends Command {
    public static final String MESSAGE_USAGE = "Usage: todo <description>";
    public static final String MESSAGE_EMPTY_DESCRIPTION = "You can't have an empty Todo...";

    private final ToDo toDo;

    public ToDoCommand(ToDo toDo) {
        this.toDo = toDo;
    }

    // TODO: handle code duplication for saving tasks (?)
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            tasks.addTask(toDo);
            storage.save(tasks);
            ui.displayTaskResponse(toDo, tasks);
        } catch (StorageSaveException e) {
            ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
