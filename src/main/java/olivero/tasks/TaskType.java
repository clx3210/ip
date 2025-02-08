package olivero.tasks;

public enum TaskType {
    TODO,
    DEADLINE,
    EVENT;

    public static TaskType fromString(String taskType) {
        return switch (taskType) {
        case "T" -> TODO;
        case "D" -> DEADLINE;
        case "E" -> EVENT;
        default -> throw new IllegalArgumentException("Invalid task type: " + taskType);
        };
    }
}
