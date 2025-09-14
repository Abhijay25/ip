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

    public Event(String description, String from, String to, String tag) {
        super(description, tag);
        this.from = from;
        this.to = to;
    }
    
    public String toString() {
        assert (from != null && to != null);
        return "[E] " + super.getDescription() + " (From:" + this.from + "To:" + this.to + ") " + super.getTag();
    }

    /**
     * Formats task for local storage, with status converted to binary values
     * @return Formatted String of Event
     */
    public String formatTask() {
        assert (from != null && to != null);
        String status = this.isCompleted ? "1" : "0";
        return "E | " + status + " | " + this.getDescription() + " | " + this.getTag() + " | "  + this.from + " | " + this.to;
    }
}