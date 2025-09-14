package quacker;

import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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
       String response = parser.parse(input);
       file.save(toDo);
       
       assert (response != null);
       return response;
    }
}