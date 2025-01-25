import java.io.IOException;

public class EventCommand extends Command {

    private final Event event;

    public EventCommand(Event event) {
        this.event = event;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            tasks.addTask(event);
            storage.saveData(tasks.asFormattedString());
            ui.displayTaskResponse(event, tasks);
        } catch (IOException e) {
            ui.displayMessage(Responses.RESPONSE_SAVE_FILE_FAILED);
        }
    }
}
