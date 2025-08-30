public class ToDo extends Task{

    public ToDo(String description) {
        super(description);
    }

    public String toString() {
        return "[T] " + super.toString();
    }
    
    public String formatTask() {
        return "T | " + (this.isCompleted() ? "1" : "0") + " | " + this.getDescription();
    }
}