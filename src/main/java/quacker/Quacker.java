package quacker;

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
        this.file = new FileClass("../data/Quacker.txt");
        this.toDo = new TaskList(file.load());
        this.parser = new Parser(toDo, file);
    }

    /**
     * Starts Chatbot and is ready for inputs from user
     * Prints Welcome message and task prompt
     */
    public void run() {
        Scanner scan = new Scanner(System.in);
        String divider = "\n----------------------------------- \n"; //35 Dashes

        String welcome = divider
                + "Hello! I'm Quacker \n"
                + "What can I do for you?"
                + divider;
        String goodbye = divider + "See you! Hopefully I see you again... *sad quack* \n";
        System.out.println(welcome);
        
        String prompt = "";
        
        while (!prompt.equals("bye")) {
            System.out.println("Enter Task:");
            prompt = scan.nextLine();
            parser.parse(prompt);
        }
        
        scan.close();
        file.save(toDo);
        System.out.println(goodbye);
    }

    /**
     * Generates a response for the user's chat message
     */
    public String getResponse(String input) {
        return "Quacker heard: " + input;
    }

    public static void main(String[] args) {
        new Quacker().run();
    }
}