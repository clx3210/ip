import errors.TaskParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TaskParser {
    public static final String SEPARATOR = " | ";
    public static TaskList parseFile(String path) throws TaskParseException, FileNotFoundException {
        TaskList taskList = new TaskList();

        File f = new File(path);
        Scanner scanner = new Scanner(f);
        // file -> task parsing
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] args = line.split(Pattern.quote(SEPARATOR));
            if (args.length == 0) {
                throw new IllegalArgumentException();
            }
            switch (args[0]) {
            case "T": {
                if (args.length != 3) {
                    throw new TaskParseException("Invalid arguments length for ToDo task.");
                }
                boolean isDone = Integer.parseInt(args[1]) == 1;
                String description = args[2];
                taskList.addTask(new ToDo(description, isDone));
                break;
            }
            case "D": {
                if (args.length != 4) {
                    throw new TaskParseException("Invalid arguments length for Deadline task.");
                }
                boolean isDone = Integer.parseInt(args[1]) == 1;
                String description = args[2];
                String endDate = args[3];
                taskList.addTask(new Deadline(description, endDate, isDone));
                break;
            }
            case "E": {
                if (args.length != 5) {
                    throw new TaskParseException("Invalid arguments length for Event task.");
                }
                boolean isDone = Integer.parseInt(args[1]) == 1;
                String description = args[2];
                String startDate = args[3];
                String endDate = args[4];
                taskList.addTask(new Event(description, startDate, endDate, isDone));
                break;
            }
            default: {
                throw new TaskParseException("Unsupported task type.");
            }
            }
        }
        return taskList;

    }
}
