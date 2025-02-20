package olivero.tasks;

import java.util.List;

import javafx.util.Pair;

/**
 * Provides utility methods for tasks.
 */
public final class TaskUtil {

    /**
     * Returns the list of tasks in their string representations
     * delimited with {@link System#lineSeparator()}.
     *
     * @param tasks List of tasks paired with their corresponding task numbers.
     * @return Delimited task strings.
     */
    public static String toDisplayString(List<Pair<Integer, Task>> tasks) {
        StringBuilder message = new StringBuilder();
        for (int i = 1; i <= tasks.size(); i++) {
            Pair<Integer, Task> taskPair = tasks.get(i - 1);
            message.append(taskPair.getKey())
                    .append(". ")
                    .append(taskPair.getValue())
                    .append(System.lineSeparator());
        }
        return message.toString().strip();
    }
}
