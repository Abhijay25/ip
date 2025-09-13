package quacker.tasks;

/**
 * Child class of Task, made for deadline tasks
 */
public class Deadline extends Task{
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }
    
    public String toString() {
        return "[D] " + super.toString() + " (By:" + this.by + ")";
    }

    /**
     * Formats task for local storage, with status converted to binary values
     * @return Formatted String of Deadline
     */
    public String formatTask() {
        String status = this.isCompleted ? "1" : "0";
        return "D | " + status + " | " + this.getDescription() + " | "  + this.by;
    }
}