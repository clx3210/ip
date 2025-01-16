import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public String getTaskDescription(int taskNumber) {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            // TODO: handle this edge case
            return "";
        }
        return tasks.get(taskNumber - 1).toString();
    }

    public void markTaskAt(int taskNumber) {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            // TODO: handle this edge case
            return;
        }
        tasks.get(taskNumber - 1).setDone(true);
    }

    public void unmarkTaskAt(int taskNumber) {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            // TODO: handle this edge case
            return;
        }
        tasks.get(taskNumber - 1).setDone(false);
    }

    public int getTaskSize() {
        return tasks.size();
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        for (int i = 1; i <= tasks.size(); i++) {
            message.append(i)
                    .append(". ")
                    .append(tasks.get(i - 1))
                    .append("\n");
        }
        return message.toString();
    }
}
