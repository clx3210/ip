public class Deadline extends Task {
    private String endDate;
    public Deadline(String description, String endDate, boolean isDone) {
        super(description, isDone);
        this.endDate = endDate;
    }

    @Override
    public String toFormattedString() {
       int doneStatus = isDone() ? 1 : 0;
       return "D" + " | " + doneStatus + " | " + getDescription() + " | " + endDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + endDate + ")";
    }
}
