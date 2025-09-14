package quacker.tasks;

/**
 * Child class of Task, made for ToDos
 */
public class ToDo extends Task{
    
    public ToDo(String description) {
        super(description);
    }
    
    public String toString() {
        assert (description != null);
        return "[T] " + super.toString();
    }

    /**
     *  Formats task for local storage, with status converted to binary values
     * @return Format String of ToDo
     */
    public String formatTask() {
        assert (description != null);
        String status = this.isCompleted ? "1" : "0";
        return "T | " + status + " | " + this.getDescription();
    }
}