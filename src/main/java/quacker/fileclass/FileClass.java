package quacker.fileclass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import quacker.tasks.*;

/**
 * List of type 'Task' 
 * Used by Quacker Chatbot to store To-Do List locally
 */
public class FileClass {
    private final File file;

    /**
     * Constructor method to setup a local .txt file for storage 
     * @param filePath Location of .txt file on local computer
     */
    public FileClass (String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Returns the locally stored file containing list of Tasks within To-Do List
     * @return List of Tasks within .txt file
     */
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

    /**
     * Stores given tasks to local file
     * @param tasks Task to be stored within the locally stored list
     */
    public void save(TaskList tasks) {
        try (FileWriter fw = new FileWriter(file)) {
            for (Task t : tasks.list()) {
                fw.write(t.formatTask() + System.lineSeparator()); // Using lineSeperator instead of \n for safety
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: ");
        }
    }

    /**
     * Retrieve task from local file and returns respective child of 'Task'
     * @param line Line of Task information within the local file
     * @return Task from current line on local file
     */
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