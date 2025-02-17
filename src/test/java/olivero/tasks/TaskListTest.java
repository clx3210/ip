package olivero.tasks;

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
        for (int i = -12; i < 0; i++) {
            assertThrows(
                    IllegalArgumentException.class, () -> new TaskList().removeTaskAt(-1));
        }
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
    public void addTask_fiveTasksAdded_success() {
        TaskList taskList = new TaskList();
        int taskCount = 5;
        for (int i = 0; i < taskCount; i++) {
            taskList.addTask(new ToDo("Test todo " + i, true));
        }
        assertEquals(taskCount, taskList.getTaskSize());
    }

    private String getExpectedFormattedString() {
        return String.join(System.lineSeparator(),
                "T | 0 | Todo 1",
                "T | 1 | Todo 2",
                "T | 0 | Todo 3",
                "E | 0 | Event 1 | 2028-1-25 2323 | 2028-2-25 2323",
                "E | 1 | Event 2 | 2028-1-25 2323 | 2028-2-25 2323",
                "E | 0 | Event 3 | 2028-1-25 2323 | 2028-2-25 2323",
                "E | 1 | Event 4 | 2028-1-25 2323 | 2028-2-25 2323",
                "E | 0 | Event 5 | 2028-1-25 2323 | 2028-2-25 2323",
                "D | 0 | Deadline 1 | 2028-1-25 2323",
                "D | 1 | Deadline 2 | 2028-1-25 2323");
    }

    @Test
    public void asFormattedString_threeTodosFiveEventsTwoDeadlinesAdded_success() {
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
    public void asFormattedString_emptyTaskListReturnsEmptyFormattedString_success() {
        TaskList taskList = new TaskList();
        assertEquals("", taskList.serialiseTasks());
    }

    @Test
    public void asFormattedString_taskListWithOneTaskCorrectFormat_success() {
        String expected = "E | 1 | Event 1 | 2028-1-25 2323 | 2028-2-25 2323";

        TaskList taskList = new TaskList();

        LocalDateTime start = LocalDateTime.of(2028, 1, 25, 23, 23);
        LocalDateTime end = LocalDateTime.of(2028, 2, 25, 23, 23);

        taskList.addTask(new Event("Event 1", start, end, true));

        assertEquals(expected, taskList.serialiseTasks());
    }

    private String getExpectedTaskListString() {
        return String.join(System.lineSeparator(),
                "1. [T][X] Todo 1",
                "2. [T][ ] Todo 2",
                "3. [T][X] Todo 3",
                "4. [E][X] Event 1 (from: Jan 25 2028 2323 to: Feb 25 2028 2323)",
                "5. [E][ ] Event 2 (from: Jan 25 2028 2323 to: Feb 25 2028 2323)",
                "6. [E][X] Event 3 (from: Jan 25 2028 2323 to: Feb 25 2028 2323)",
                "7. [E][ ] Event 4 (from: Jan 25 2028 2323 to: Feb 25 2028 2323)",
                "8. [E][X] Event 5 (from: Jan 25 2028 2323 to: Feb 25 2028 2323)",
                "9. [D][ ] Deadline 1 (by: Jan 25 2028 2323)",
                "10. [D][X] Deadline 2 (by: Jan 25 2028 2323)",
                "11. [D][ ] Deadline 3 (by: Jan 25 2028 2323)",
                "12. [D][X] Deadline 4 (by: Jan 25 2028 2323)",
                "13. [D][ ] Deadline 5 (by: Jan 25 2028 2323)",
                "14. [D][X] Deadline 6 (by: Jan 25 2028 2323)");
    }

    @Test
    public void toString_taskListWithMultipleTasksCorrectString_success() {
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
