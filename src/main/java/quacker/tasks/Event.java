package quacker.tasks;

public class Event extends Task{
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }
    
    public String toString() {
        return "[E] " + super.toString() + "(From:" + this.from + "To:" + this.to + ")";
    }
    
    public String formatTask() {
        String status = this.isCompleted ? "1" : "0";
        return "E | " + status + " | " + this.getDescription() + " | "  + this.from + " | " + this.to;
    }
}