public class Task{
    protected String description;
    protected boolean isCompleted;


    public Task(String description) {
        this.description = description;
        this.isCompleted = false;
    }

    // Get the description of the task so far
    public String getDescription() {
        return this.description;
    }

    // Get the status icon to display for each task
    public String getStatusIcon() {
        return (this.isCompleted ? "X" : " ");
    }
    
    // Flip the completed status for task
    public void setComplete() {
        this.isCompleted = true;
    }

    public void setIncomplete() {
        this.isCompleted = false;
    }

    public String toString() {
        return this.description;
    }
    
    public String formatTask() {
        String status = this.isCompleted ? "1" : "0";
        return " | " + status + " | " + description; 
    }
}