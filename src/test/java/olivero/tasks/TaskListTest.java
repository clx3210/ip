package olivero.tasks;

import static olivero.tasks.TaskTestUtil.getExpectedFormattedString;
import static olivero.tasks.TaskTestUtil.getExpectedTaskListString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests for the TaskList member SUTs.
 */
public class TaskListTest {

    @Test
    public void removeTaskAt_negativeTaskNumber_exceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> new TaskList().removeTaskAt(-1));
    }

    @Test
    public void removeTaskAt_outOfBoundsTaskNumber_exceptionThrown() {
        TaskList taskList = new TaskList();
        int taskCount = 20;
        for (int i = 0; i < taskCount; i++) {
            taskList.addTask(new ToDo("Test todo item " + i, false));
        }

        assertThrows(IllegalArgumentException.class, () -> taskList.removeTaskAt(21));

        assertThrows(IllegalArgumentException.class, () -> taskList.removeTaskAt(100));
    }

    @Test
    public void removeTaskAt_validTaskNumberRemoved_success() {
        Task[] tasks = new Task[] { new ToDo("Todo 1", false), new ToDo("Todo 2", false), new Deadline("Todo 3",
                LocalDateTime.of(2025, 12, 1, 1, 1), false)
        };
        TaskList taskList = new TaskList();

        for (Task task : tasks) {
            taskList.addTask(task);
        }
        // taskList: [1, 2, 3]
        assertEquals(tasks[0], taskList.removeTaskAt(1));
        // taskList: [2, 3]
        assertEquals(tasks[2], taskList.removeTaskAt(2));
        // taskList: [2]
        assertEquals(tasks[1], taskList.removeTaskAt(1));
    }

    @Test
    public void serialiseTasks_addTodoEventDeadlineTasks_success() {
        String expected = getExpectedFormattedString();
        TaskList taskList = new TaskList();

        int numTodos = 3;
        int numEvents = 5;
        int numDeadlines = 2;

        LocalDateTime start = LocalDateTime.of(2028, 1, 25, 23, 23);
        LocalDateTime end = LocalDateTime.of(2028, 2, 25, 23, 23);

        for (int i = 1; i <= numTodos; i++) {
            taskList.addTask(new ToDo("Todo " + i, i % 2 == 0));
        }

        for (int i = 1; i <= numEvents; i++) {
            taskList.addTask(new Event("Event " + i, start, end, i % 2 == 0));
        }

        for (int i = 1; i <= numDeadlines; i++) {
            taskList.addTask(new Deadline("Deadline " + i, start, i % 2 == 0));
        }
        assertEquals(expected, taskList.serialiseTasks());
    }

    @Test
    public void serialiseTasks_emptyTaskList_success() {
        TaskList taskList = new TaskList();
        assertEquals("", taskList.serialiseTasks());
    }

    @Test
    public void toString_multipleTasksInTaskList_success() {
        String expected = getExpectedTaskListString();
        TaskList taskList = new TaskList();

        int numTodos = 3;
        int numEvents = 5;
        int numDeadlines = 6;

        LocalDateTime start = LocalDateTime.of(2028, 1, 25, 23, 23);
        LocalDateTime end = LocalDateTime.of(2028, 2, 25, 23, 23);

        for (int i = 1; i <= numTodos; i++) {
            taskList.addTask(new ToDo("Todo " + i, i % 2 == 1));
        }

        for (int i = 1; i <= numEvents; i++) {
            taskList.addTask(new Event("Event " + i, start, end, i % 2 == 1));
        }

        for (int i = 1; i <= numDeadlines; i++) {
            taskList.addTask(new Deadline("Deadline " + i, start, i % 2 == 0));
        }
        assertEquals(expected, taskList.toString());
    }

    @Test
    public void filter_tasksWithBookKeyword_success() {
        TaskList taskList = new TaskList();
        taskList.addTask(new ToDo("Test Book 1", true));
        taskList.addTask(new ToDo("Test todo 2", false));
        taskList.addTask(new Deadline("Test deadline 1",
                LocalDateTime.of(2025, 12, 1, 1, 1), false));
        taskList.addTask(new Event("Test Book 1",
                LocalDateTime.of(2025, 12, 1, 1, 1),
                LocalDateTime.of(2025, 12, 1, 1, 1), true));

        List<?> filteredTaskList = taskList.filter((taskNumber, task) ->
                task.getDescription().contains("Book"));
        assertEquals(2, filteredTaskList.size());
    }

}
