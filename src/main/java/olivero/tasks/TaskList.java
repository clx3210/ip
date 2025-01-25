package olivero.tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    /** The copy constructor for task lists */
    public TaskList(TaskList other) {
        tasks = new ArrayList<>(other.tasks);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public String getTaskDescription(int taskNumber) throws IllegalArgumentException {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException();
        }
        return tasks.get(taskNumber - 1).toString();
    }

    public Task removeTaskAt(int taskNumber) throws IllegalArgumentException {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException();
        }
        return tasks.remove(taskNumber - 1);
    }

    public void markTaskAt(int taskNumber) throws IllegalArgumentException {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException();
        }
        tasks.get(taskNumber - 1).setDone(true);
   }

    public void unmarkTaskAt(int taskNumber) throws IllegalArgumentException {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException();
        }
        tasks.get(taskNumber - 1).setDone(false);
    }

    public int getTaskSize() {
        return tasks.size();
    }

    public String asFormattedString() {
        StringBuilder serialised = new StringBuilder();
        for (Task task : tasks) {
            serialised.append(task.toFormattedString())
                    .append(System.lineSeparator());
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
                    .append(System.lineSeparator());
        }
        return message.toString();
    }
}
