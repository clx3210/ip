package olivero.tasks;

import olivero.exceptions.UnsupportedTaskException;

/**
 * Represents the type of task.
 */
public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String taskType;

    TaskType(String taskType) {
        this.taskType = taskType;
    }

    /**
     * Parses the task type from a string.
     *
     * @param taskType
     * @return
     */
    public static TaskType parseString(String taskType) throws UnsupportedTaskException {
        return switch (taskType) {
        case "T" -> TODO;
        case "D" -> DEADLINE;
        case "E" -> EVENT;
        default -> throw new UnsupportedTaskException("Invalid task type: " + taskType);
        };
    }

    public String getValue() {
        return taskType;
    }
}
