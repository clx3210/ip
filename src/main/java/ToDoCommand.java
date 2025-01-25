import java.io.IOException;

public class ToDoCommand extends Command {
    private static final CommandType TAG = CommandType.TODO;

    private final ToDo toDo;

    public ToDoCommand(ToDo toDo) {
        this.toDo = toDo;
    }

    // TODO: handle code duplication for saving tasks (?)
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            tasks.addTask(toDo);
            storage.saveData(tasks.asFormattedString());
            ui.displayTaskResponse(toDo, tasks);
        } catch (IOException e) {
            ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
