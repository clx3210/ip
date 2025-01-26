package olivero.tasks;

public class ToDo extends Task {
    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String toFormattedString() {
        int doneStatus = isDone() ? 1 : 0;
        return "T" + " | " + doneStatus + " | " + getDescription();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
