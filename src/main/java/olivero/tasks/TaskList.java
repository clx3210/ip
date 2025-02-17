package olivero.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import javafx.util.Pair;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private List<Task> tasks;

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


    private void validateTaskNumber(int taskNumber) throws IllegalArgumentException {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new IllegalArgumentException();
        }
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
     * Removes the task with the given task number from the task list and
     * returns it.
     *
     * @param taskNumber The task number associated with the task in the list to be removed.
     * @return The removed {@code Task} object.
     * @throws IllegalArgumentException If no tasks in the list have that task number.
     */
    public Task removeTaskAt(int taskNumber) throws IllegalArgumentException {
        validateTaskNumber(taskNumber);
        return tasks.remove(taskNumber - 1);
    }

    /**
     * Removes all tasks whose task numbers are in the given set.
     *
     * @param taskNumbers The set of unique task numbers of tasks to be removed from the list.
     * @return List of deleted tasks paired with their original task number.
     * @throws IllegalArgumentException If given task numbers are out of range.
     */
    public List<Pair<Integer, Task>> removeTasksAt(Set<Integer> taskNumbers) throws IllegalArgumentException {
        for (int taskNumber : taskNumbers) {
            validateTaskNumber(taskNumber);
        }
        List<Pair<Integer, Task>> removedTasks = new ArrayList<>();
        List<Task> remainingTasks = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            if (taskNumbers.contains(i + 1)) {
                removedTasks.add(new Pair<>(i + 1, tasks.get(i)));
            } else {
                remainingTasks.add(tasks.get(i));
            }
        }
        this.tasks = remainingTasks;
        return removedTasks;
    }

    /**
     * Marks the task with the given task number from the task list as completed.
     *
     * @param taskNumber The task number associated with the task in the list to be marked.
     * @throws IllegalArgumentException If no tasks in the list have that task number.
     */
    public void markTaskAt(int taskNumber) throws IllegalArgumentException {
        validateTaskNumber(taskNumber);
        tasks.get(taskNumber - 1).setDone(true);
    }

    /**
     * Marks the task with the given task number from the task list as incomplete.
     *
     * @param taskNumber The task number associated with the task in the list to be unmarked.
     * @throws IllegalArgumentException If no tasks in the list have that task number.
     */
    public void unmarkTaskAt(int taskNumber) throws IllegalArgumentException {
        validateTaskNumber(taskNumber);
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
        return TaskUtils.toDisplayString(
                tasks.stream()
                        .map(task -> new Pair<>(tasks.indexOf(task) + 1, task))
                        .collect(Collectors.toCollection(ArrayList::new)));
    }

    /**
     * Returns a list of tasks that satisfy the given predicate.
     *
     * @param predicate The filter bi-predicate with parameter (taskNumber, task).
     * @return {@code List} of pairs of task numbers and the corresponding tasks that satisfy the predicate.
     */
    public List<Pair<Integer, Task>> filter(BiPredicate<Integer, ? super Task> predicate) {
        // Use streams to simplify the filtering process for any predicate
        return tasks
                .stream()
                .map(task -> new Pair<>(tasks.indexOf(task) + 1, task))
                .filter(pair -> predicate.test(pair.getKey(), pair.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
