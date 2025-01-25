package olivero.tasks;

public abstract class Task {
    private String description;
    private boolean isDone;

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    public abstract String toFormattedString();

    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }

}
