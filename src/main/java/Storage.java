import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {

    private final String dataPath;

    public Storage(String dataPath) {
        this.dataPath = dataPath;
    }

    // TODO: throw custom exception instead of IOException
    public void saveData(String content) throws IOException {
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

    public String loadFromFile() throws FileNotFoundException {
        File f = new File(dataPath);
        Scanner scanner = new Scanner(f);
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.next());
        }
        return stringBuilder.toString();
    }
}
