package olivero.storage;

import olivero.tasks.TaskList;
import olivero.exceptions.StorageLoadException;
import olivero.exceptions.StorageSaveException;
import olivero.exceptions.TaskParseException;
import olivero.parsers.TaskParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {

    private final String dataPath;

    private final TaskParser taskParser;

    public Storage(String dataPath) {
        this.dataPath = dataPath;
        taskParser = new TaskParser();
    }

    public void save(TaskList tasks) throws StorageSaveException {
        try {
            saveData(tasks.asFormattedString());
        } catch (IOException e) {
            throw new StorageSaveException(e.getMessage());
        }
    }

    public TaskList load() throws StorageLoadException {
        TaskList taskList;
        try {
            String contents = loadFromFile();
            taskList = taskParser.parse(contents);
        } catch (FileNotFoundException e) {
            throw new StorageLoadException(
                    e.getMessage(),
                    StorageLoadException.Reason.STORAGE_MISSING);
        } catch (TaskParseException e) {
            throw new StorageLoadException(
                    e.getMessage(),
                    StorageLoadException.Reason.STORAGE_CORRUPT);
        }
        return taskList;
    }

    // TODO: throw custom exception instead of IOException
    private void saveData(String content) throws IOException {
        File f = new File(dataPath);
        File parent = f.getParentFile();

        // case: parent directories do not exist but cannot be created
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IOException();
        }
        // case: file exists but is a directory
        if (f.exists() && f.isDirectory()) {
            throw new IOException();
        }
        // override or create a new file
        FileWriter fw = new FileWriter(f);
        fw.write(content);
        fw.close();
    }

    private String loadFromFile() throws FileNotFoundException {
        File f = new File(dataPath);
        Scanner scanner = new Scanner(f);
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine()).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
