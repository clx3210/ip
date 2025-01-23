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
            throw new IllegalArgumentException("No task with task number "
                    + taskNumber + " exists...");
        }
        return tasks.get(taskNumber - 1).toString();
    }

    public Task removeTaskAt(int taskNumber) {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException("No task with task number "
                    + taskNumber + " exists...");
        }
        return tasks.remove(taskNumber - 1);
    }

    public void markTaskAt(int taskNumber) {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException("No task with task number "
                    + taskNumber + " exists...");
        }
        tasks.get(taskNumber - 1).setDone(true);
   }

    public void unmarkTaskAt(int taskNumber) {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException("No task with task number "
                    + taskNumber + " exists...");
        }
        tasks.get(taskNumber - 1).setDone(false);
    }

    public int getTaskSize() {
        return tasks.size();
    }

    public String asFormattedString() {
        StringBuilder serialised = new StringBuilder();
        for (Task task : tasks) {
            serialised.append(task.toFormattedString()).append('\n');
        }
        return serialised.toString();
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
