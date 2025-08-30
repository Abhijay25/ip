public class Deadline extends Task{
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public String toString() {
        return "[D] " + super.toString() + "(By: " + this.by + ")";
    }
    
    public String formatTask() {
        return "D | " + (this.isCompleted() ? "1" : "0") + " | " + this.getDescription() + " | "  + this.by;
    }
}