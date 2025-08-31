package quacker.tasks;

/**
 * Parent Task class for types of tasks handled by Quacker
 */
public class Task{
    protected String description;
    protected boolean isCompleted;

    /**
     * Constructor method to create task
     * @param description Description of Task given by user
     */
    public Task(String description) {
        this.description = description;
        this.isCompleted = false;
    }

    /**
     * Returns description of current Task
     * @return Description of Task
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets current status of completion
     * @return Status of completion
     */
    public String getStatusIcon() {
        return (this.isCompleted ? "X" : " ");
    }

    /**
     * Sets status of Task to complete
     */
    public void setComplete() {
        this.isCompleted = true;
    }

    /**
     * Sets status of Task to incomplete
     */
    public void setIncomplete() {
        this.isCompleted = false;
    }

    /**
     * Complete description of Task, including status
     * @return String of Task's description and status
     */
    public String toString() {
        return this.description;
    }

    /**
     * Formats task for local storage, with status converted to binary values
     * @return formatted String of Task
     */
    public String formatTask() {
        String status = this.isCompleted ? "1" : "0";
        return " | " + status + " | " + description; 
    }
}