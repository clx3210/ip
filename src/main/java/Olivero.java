import errors.CommandParseException;
import errors.StorageLoadException;

public class Olivero {
    private TaskList taskList;
    private final Storage storage;

    private final Ui textUi;

    private final Parser commandParser;


    public Olivero(String storagePath) {
        this.storage = new Storage(storagePath);
        this.textUi = new Ui();
        this.commandParser = new Parser();
    }

    public static void main(String[] args) {
        new Olivero("data/tasks.txt").run();
    }

    public void setupResources() {
        textUi.displayGreetingMessage();
        try {
            this.taskList = new TaskList(storage.load());
        } catch (StorageLoadException e) {
            this.taskList = new TaskList();
            textUi.displayError(Responses.RESPONSE_SAVE_FILE_CORRUPT);
        }
    }

    public void run() {
        setupResources();
        loop();
    }

    public void loop() {
        boolean isExit = false;
        while (!isExit) {
            try {
                String rawCommand = textUi.readCommand();
                Command command = commandParser.parse(rawCommand);
                command.execute(taskList, textUi, storage);
                isExit = command.isExit();
            } catch (CommandParseException e) {
                textUi.displayError(e.getMessage());
            }
        }
    }
}
