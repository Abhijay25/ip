package quacker.fileclass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import quacker.tasks.*;

/**
 * List of type 'Task' 
 * Used by Quacker Chatbot to store To-Do List locally
 */
public class FileClass {
    private final File file;
    private static final Logger logger = Logger.getLogger(FileClass.class.getName());

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

            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNextLine()) {
                    Task currentTask = getTask(sc.nextLine());
                    if (currentTask != null) {
                        tasks.add(currentTask);
                    }
                }
            }
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
        // Error handling for missing file
        if (!file.exists()) {
            throw new IllegalStateException("File does not exist: " + file.getPath());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task t : tasks.list()) {
                writer.write(t.formatTask());
                writer.newLine();
            }
            logger.info("Tasks saved successfully.");
        } catch (IOException e) {
            logger.severe("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Retrieve task from local file and returns respective child of 'Task'
     * @param line Line of Task information within the local file
     * @return Task from current line on local file
     */
    private Task getTask(String line) {
        // Error handling for missing file
        if (!file.exists()) {
            throw new IllegalStateException("File does not exist: " + file.getPath());
        }
        
        String[] desc = line.split(" \\| ");
        if (desc.length < 3) {
            System.err.println("Invalid task line: " + line);
            return null;
        }
        
        String type = desc[0];
        boolean isComplete = desc[1].equals("1");
        String description = desc[2];
        
        switch(type) {
            case "T":
                ToDo todo = new ToDo(description, desc[3]);
                if (isComplete) todo.setComplete();
                return todo;

            case "D":
                Deadline deadline = new Deadline(description, desc[4], desc[3]);
                if (isComplete) deadline.setComplete();
                return deadline;
                
            case "E":
                Event event = new Event(description, desc[4], desc[5], desc[3]);
                if (isComplete) event.setComplete();
                return event;
        }
        return null;
    }
}