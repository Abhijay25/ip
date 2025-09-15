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
    public FileClass(String filePath) {
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
            logger.severe("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Stores given tasks to local file
     * @param tasks Task to be stored within the locally stored list
     */
    public void save(TaskList tasks) {
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
     * Handles optional fields safely to avoid crashes from malformed save files
     * @param line Line of Task information within the local file
     * @return Task from current line on local file
     */
    private Task getTask(String line) {
        if (!file.exists()) {
            throw new IllegalStateException("File does not exist: " + file.getPath());
        }

        String[] desc = line.split(" \\| ");
        if (desc.length < 3) {
            logger.warning("Invalid task line in save file: " + line);
            return null;
        }

        String type = desc[0];
        boolean isComplete = desc[1].equals("1");
        String description = desc[2];

        switch (type) {
            case "T": // ToDo
                String todoTag = (desc.length > 3) ? desc[3] : "";
                ToDo todo = new ToDo(description, todoTag);
                if (isComplete) todo.setComplete();
                return todo;

            case "D": // Deadline
                String deadlineTag = (desc.length > 3) ? desc[3] : "";
                String deadlineBy = (desc.length > 4) ? desc[4] : "";
                Deadline deadline = new Deadline(description, deadlineBy, deadlineTag);
                if (isComplete) deadline.setComplete();
                return deadline;

            case "E": // Event
                String eventTag = (desc.length > 3) ? desc[3] : "";
                String eventFrom = (desc.length > 4) ? desc[4] : "";
                String eventTo = (desc.length > 5) ? desc[5] : "";
                Event event = new Event(description, eventFrom, eventTo, eventTag);
                if (isComplete) event.setComplete();
                return event;

            default:
                logger.warning("Unknown task type in save file: " + line);
                return null;
        }
    }
}
