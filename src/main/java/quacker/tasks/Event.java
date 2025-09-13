package quacker.tasks;

/**
 * Child class of Task, made for Events
 */
public class Event extends Task{
    protected String from;
    protected String to;
    
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }
    
    public String toString() {
        return "[E] " + super.toString() + " (From:" + this.from + "To:" + this.to + ")";
    }

    /**
     * Formats task for local storage, with status converted to binary values
     * @return Formatted String of Event
     */
    public String formatTask() {
        String status = this.isCompleted ? "1" : "0";
        return "E | " + status + " | " + this.getDescription() + " | "  + this.from + " | " + this.to;
    }
}