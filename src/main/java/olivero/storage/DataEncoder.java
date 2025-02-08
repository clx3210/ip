package olivero.storage;

import olivero.tasks.TaskList;

public class DataEncoder {

    public String encode(TaskList taskList) {
        return taskList.serialiseTasks();
    }
}
