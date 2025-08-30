public class Deadline extends Task{
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public String toString() {
        return "[D] " + super.toString() + "(By:" + this.by + ")";
    }
    
    public String formatTask() {
        String status = this.isCompleted ? "1" : "0";
        return "D | " + status + " | " + this.getDescription() + " | "  + this.by;
    }
}