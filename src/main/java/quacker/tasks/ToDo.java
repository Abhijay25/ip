package quacker.tasks;

public class ToDo extends Task{

    public ToDo(String description) {
        super(description);
    }

    public String toString() {
        return "[T] " + super.toString();
    }
    
    public String formatTask() {
        String status = this.isCompleted ? "1" : "0";
        return "T | " + status + " | " + this.getDescription();
    }
}