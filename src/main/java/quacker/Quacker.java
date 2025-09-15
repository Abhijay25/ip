package quacker;

import java.util.Objects;
import java.util.logging.Logger;

import quacker.parser.Parser;
import quacker.tasks.TaskList;
import quacker.fileclass.FileClass;


/**
 * Entry Point Class for the Quacker Chatbot
 */
public class Quacker {
    private TaskList toDo;
    private FileClass file;
    private Parser parser;
    private static final Logger logger = Logger.getLogger(Quacker.class.getName());
    

    /**
     * Constructor method to initiate the Chatbot
     * Initialises the file, list and parser
     */
    public Quacker() {
        this.file = new FileClass("src/main/data/Quacker.txt");
        this.toDo = new TaskList(file.load());
        this.parser = new Parser(toDo, file);
    }
    
    /**
     * Generates a response for the user's chat message
     */
    public String getResponse(String input) {
       logger.info("Received input: " + input);
       String response = parser.parse(input);

        try {
            file.save(toDo);
            logger.info("Saved tasks successfully.");
        } catch (Exception e) {
            logger.severe("Failed to save tasks: " + e.getMessage());
        }

        return Objects.requireNonNull(response, "Parser returned null");
    }
}