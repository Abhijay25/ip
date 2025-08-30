import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class FileClass {
    private final File file;
    
    public FileClass (String filePath) {
        this.file = new File(filePath);
    }

    // Reading a file and getting the list of tasks 
    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs(); 
                file.createNewFile();
                
                return tasks; // return empty list of tasks if file didn’t exist
            }
            
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                Task currentTask = getTask(sc.nextLine());
                if (currentTask != null) {
                    tasks.add(currentTask);
                }
            }
            
            sc.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: ");
        }
        return tasks;
    }

    // Saving a list of tasks to the file
    public void save(List<Task> tasks) {
        try (FileWriter fw = new FileWriter(file)) {
            for (Task t : tasks) {
                fw.write(t.formatTask() + System.lineSeparator()); // Using lineSeperator instead of \n for safety
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: ");
        }
    }
    
    // Get the task from line to store in output Array
    private Task getTask(String line) {
        String[] desc = line.split(" \\| ");
        String type = desc[0];
        boolean isComplete = desc[1].equals("1");
        
        switch(type) {
            case "T":
                ToDo todo = new ToDo(desc[2]);
                if (isComplete) todo.setComplete();
                return todo;

            case "D":
                Deadline deadline = new Deadline(desc[2], desc[3]);
                if (isComplete) deadline.setComplete();
                return deadline;
                
            case "E":
                Event event = new Event(desc[2], desc[3], desc[4]);
                if (isComplete) event.setComplete();
                return event;
        }
        return null;
    }
}