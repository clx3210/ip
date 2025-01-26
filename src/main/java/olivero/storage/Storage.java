package olivero.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import olivero.exceptions.StorageLoadException;
import olivero.exceptions.StorageSaveException;
import olivero.exceptions.TaskParseException;
import olivero.parsers.TaskParser;
import olivero.tasks.TaskList;

/**
 * Handles the saving and loading of {@code TaskList} into and from disk respectively.
 */
public class Storage {

    private final String dataPath;

    private final TaskParser taskParser;

    /**
     * Constructs a storage instance to handle saving and loading of tasks.
     *
     * @param dataPath the path to the file to be used for storing tasks.
     */
    public Storage(String dataPath) {
        this.dataPath = dataPath;
        taskParser = new TaskParser();
    }

    /**
     * Saves a {@code TaskList} into disk at the location
     * specified by the file path.
     *
     * @param tasks TaskList object to be saved to disk.
     * @throws StorageSaveException When the file present is a directory, or cannot be created.
     */
    public void save(TaskList tasks) throws StorageSaveException {
        try {
            saveData(tasks.asFormattedString());
        } catch (IOException e) {
            throw new StorageSaveException(e.getMessage());
        }
    }

    /**
     * Loads a {@code TaskList} from disk at the location specified
     * by the file path.
     *
     * @return TaskList object stored in the disk.
     * @throws StorageLoadException When the data file at the specified
     *                              location does not exist or is corrupted.
     */
    public TaskList load() throws StorageLoadException {
        TaskList taskList;
        try {
            String contents = loadFromFile();
            taskList = taskParser.parse(contents);
        } catch (FileNotFoundException e) {
            throw new StorageLoadException(
                    e.getMessage(),
                    StorageLoadException.Reason.DATA_MISSING);
        } catch (TaskParseException e) {
            throw new StorageLoadException(
                    e.getMessage(),
                    StorageLoadException.Reason.DATA_CORRUPT);
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
