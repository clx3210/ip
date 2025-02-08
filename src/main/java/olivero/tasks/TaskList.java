package olivero.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Constructs an empty list of {@code Task} objects.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Constructs a task list object by copying from an existing provided
     * {@code TaskList} object.
     *
     * @param other The {@code TaskList} to be copied from.
     */
    public TaskList(TaskList other) {
        tasks = new ArrayList<>(other.tasks);
    }

    private TaskList(List<? extends Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a new task into the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the description of the task at the given task number.
     *
     * @param taskNumber The task number associated with some task in the list.
     * @return Task description.
     * @throws IllegalArgumentException If no tasks in the list have that task number.
     */
    public String getTaskDescription(int taskNumber) throws IllegalArgumentException {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException();
        }
        return tasks.get(taskNumber - 1).toString();
    }

    /**
     * Removes the task with the given task number from the task list and
     * returns it.
     *
     * @param taskNumber The task number associated with the task in the list to be removed.
     * @return The removed {@code Task} object.
     * @throws IllegalArgumentException If no tasks in the list have that task number.
     */
    public Task removeTaskAt(int taskNumber) throws IllegalArgumentException {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException();
        }
        return tasks.remove(taskNumber - 1);
    }

    /**
     * Marks the task with the given task number from the task list as completed.
     *
     * @param taskNumber The task number associated with the task in the list to be marked.
     * @throws IllegalArgumentException If no tasks in the list have that task number.
     */
    public void markTaskAt(int taskNumber) throws IllegalArgumentException {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException();
        }
        tasks.get(taskNumber - 1).setDone(true);
    }

    /**
     * Marks the task with the given task number from the task list as incomplete.
     *
     * @param taskNumber The task number associated with the task in the list to be unmarked.
     * @throws IllegalArgumentException If no tasks in the list have that task number.
     */
    public void unmarkTaskAt(int taskNumber) throws IllegalArgumentException {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException();
        }
        tasks.get(taskNumber - 1).setDone(false);
    }

    /**
     * Returns the current number of tasks in the task list.
     *
     * @return Number of tasks.
     */
    public int getTaskSize() {
        return tasks.size();
    }

    /**
     * Returns the list of tasks in their formatted string form
     * delimited with {@link System#lineSeparator()}.
     *
     * @return Delimited formatted task strings.
     */
    public String serialiseTasks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Task task : tasks) {
            stringBuilder
                    .append(task.toFormattedString())
                    .append(System.lineSeparator());
        }
        return stringBuilder.toString().strip();
    }

    /**
     * Returns the list of tasks in their string representations
     * delimited with {@link System#lineSeparator()}.
     *
     * @return Delimited task strings.
     */
    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        for (int i = 1; i <= tasks.size(); i++) {
            message.append(i)
                    .append(". ")
                    .append(tasks.get(i - 1))
                    .append(System.lineSeparator());
        }
        return message.toString().strip();
    }

    /**
     * Returns a task list of elements filtered to satisfy the given predicate.
     *
     * @param predicate The filter predicate.
     * @return A {@code TaskList} object.
     */
    public TaskList filter(Predicate<? super Task> predicate) {
        // Use streams to simplify the filtering process for any predicate
        List<Task> filtered = tasks
                .stream()
                .filter(predicate)
                .collect(Collectors.toCollection(ArrayList::new));
        return new TaskList(filtered);
    }

}
