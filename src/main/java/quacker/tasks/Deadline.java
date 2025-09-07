package quacker.tasks;

/**
 * Child class of Task, made for deadline tasks
 */
public class Deadline extends Task{
    protected String by;

    /**
     * Constructor method for Deadline Tasks
     * @param description String Description of Task
     * @param by String respresentation of Task's Deadline
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Complete description of Task, including status
     * @return String of Task's description and Deadline
     */
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