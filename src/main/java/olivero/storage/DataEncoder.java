package olivero.storage;

import olivero.tasks.TaskList;

/**
 * Represents an encoder for encoding a {@code TaskList} object into a string.
 */
public class DataEncoder {

    /**
     * Encodes a {@code TaskList} object into a string.
     *
     * @param taskList The task list to be encoded.
     * @return Encoded string representation of the task list.
     */
    public String encode(TaskList taskList) {
        return taskList.serialiseTasks();
    }
}
