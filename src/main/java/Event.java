public class Event extends Task {
    private String startDate;
    private String endDate;
    public Event(String description, String startDate, String endDate, boolean isDone) {
        super(description, isDone);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDate
                + " to: " + endDate + ")";
    }
}
