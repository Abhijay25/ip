package quacker.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.time.format.DateTimeParseException;

import quacker.tasks.*;
import quacker.fileclass.FileClass;

/**
 * Parses through given command to provide appropriate feedback to user
 * Manipulates given local file to load / save given information
 */
public class Parser {
    private TaskList toDo;
    private FileClass file;
    private static String divider = "\n----------------------------------- \n"; //35 Dashes

    /**
     * Constructor method to initialize a parser to deal with user commands
     * @param toDo TaskList Data Type that is used to store / retrieve information
     * @param file Local file to retrieve /save information for data retention
     */
    public Parser(TaskList toDo, FileClass file) {
        this.toDo = toDo;
        this.file = file;
    }

    /**
     * Parse through given command and respond appropriately
     * @param prompt The command given by the user
     */
    public String parse(String prompt) {
        prompt = prompt.toLowerCase();
        int len = toDo.size();// For case insensitive commands

        switch(prompt.split(" ")[0]) {
            // If user asks for current list, display all
            case "list":
                if (len == 0) {
                    String emptyResponse = "Nothing to display. Try giving me something to add!";
                    return emptyResponse;
                } else {
                    StringBuilder listResponse = new StringBuilder();
                    for (int i = 0; i < len; i++) {
                        Task item = toDo.get(i);
                        if (i == len - 1) {
                            listResponse.append(i + 1).append(". [").append(item.getStatusIcon()).append("]").append(item.toString());
                        } else {
                            listResponse.append(i + 1).append(". [").append(item.getStatusIcon()).append("]")
                                    .append(item.toString()).append("\n");
                        }
                    }
                    return listResponse.toString();
                }

            // Mark the task complete after checking for valid number
            case "mark":
                try {
                    String taskString = prompt.substring(5); // To check whether we're given a valid number
                    int taskNumber = Integer.parseInt(taskString);

                    if (taskNumber > toDo.size()) {
                        return "Please enter a valid task number!";
                    }

                    // Once the number is valid, prepare the response message and mark as completed
                    toDo.get(taskNumber - 1).setComplete();
                    file.save(toDo);
                    StringBuilder markResponse = new StringBuilder();
                    markResponse.append("'").append(toDo.get(taskNumber - 1).getDescription())
                            .append("' has been marked completed!");
                    return markResponse.toString();

                }
                catch (NumberFormatException e){
                    return "Please enter a valid task number!";
                }

            // Mark the task incomplete after checking for valid number
            case "unmark":
                try {
                    String taskString = prompt.substring(7); // To check whether we're given a valid number
                    int taskNumber = Integer.parseInt(taskString);

                    if (taskNumber > toDo.size()) {
                        return "Please enter a valid task number!";
                    }

                    // Once the number is valid, prepare the response message and mark as completed
                    toDo.get(taskNumber - 1).setIncomplete();
                    file.save(toDo);
                    StringBuilder unmarkResponse = new StringBuilder();
                    unmarkResponse.append("'").append(toDo.get(taskNumber - 1).getDescription())
                            .append("' has been unmarked!");
                    return unmarkResponse.toString();

                }
                catch (NumberFormatException e){
                    return "Please enter a valid task number!";
                }

            // Add Deadlines to the list
            case "deadline":
                if (!prompt.contains("/by")) {
                    return "Please use the format: deadline [description] /by [DD-MM-YYYY | 24hrs time] for deadlines";
                }
                String[] breakdown = prompt.substring(9).split("/by", 2);
                if(breakdown.length < 2 || breakdown[0].trim().isEmpty() || breakdown[1].trim().isEmpty()) {
                    return "Please use the format: deadline [description] /by [DD-MM-YYYY | 24hrs time] for deadlines";
                } else {
                    try {
                        String description = breakdown[0].trim();
                        String cutoff = breakdown[1].trim();
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("d-M-yyyy HHmm");
                        LocalDateTime deadlineDateTime = LocalDateTime.parse(cutoff, format);
                        String finalDeadline = deadlineDateTime.format(DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a"));

                        // Add to list and provide visual confirmation
                        toDo.add(new Deadline(description, finalDeadline));
                        file.save(toDo);
                        
                        return "'" + description + "' By " + finalDeadline + " has been added to the list!";
                    } catch (DateTimeParseException e) {
                        return "Please use the format: deadline [description] /by [DD-MM-YYYY | 24hrs time] for deadlines";
                    }
                }

            // Add todos to the list
            case "todo":
                if (prompt.length() <= 5) {
                    return "Please use the format: todo [description] for todos";
                } else {
                    String taskString = prompt.substring(5);
                    toDo.add(new ToDo(taskString));
                    file.save(toDo);
                    
                    return "'" + taskString + "' has been added to the list!";
                }

            // Add events to the list
            case "event":
                if (!prompt.contains("/from") || !prompt.contains("/to")) {
                    return "Please use the format: event [description] /from [time/date] /to [time/date] for events";
                }
                String[] msgParts = prompt.substring(6).split("/from", 2);
                if (msgParts.length < 2){
                    return "Please use the format: event [description] /from [time/date] /to [time/date] for events";
                } else {
                    String desc = msgParts[0];
                    String[] timings = msgParts[1].split("/to", 2);
                    if (timings.length < 2 || timings[0].trim().isEmpty()|| timings[1].trim().isEmpty()) {
                        return "Please use the format: event [description] /from [time/date] /to [time/date] for events";
                    } else {
                        toDo.add(new Event(msgParts[0], timings[0], timings[1]));
                        file.save(toDo);
                        
                        return "'" + msgParts[0] + "' From " + timings[0] + " To " + timings[1] +
                                " has been added to the list!";
                    }
                }

            case "delete":
                try {
                    String taskString = prompt.substring(7); 
                    int taskNumber = Integer.parseInt(taskString); // To check whether we're given a valid number

                    if (taskNumber > toDo.size()) {
                        return "Please enter a valid task number!";
                    }

                    // Once the number is valid, prepare the deletion message
                    String task = toDo.get(taskNumber - 1).toString();
                    StringBuilder deleteResponse = new StringBuilder();
                    deleteResponse.append("The following task has been removed from your list: \n").append(task);

                    toDo.remove(taskNumber - 1); // Removes the specified task from the ArrayList
                    file.save(toDo);

                    return deleteResponse.toString();

                }
                catch (NumberFormatException e){
                    return "Please enter a valid task number!";
                }
                
            case "find":
                String findString = prompt.substring(5);
                StringBuilder findResponse = new StringBuilder("Is This What You're Looking For? \n");
                for (int i = 0; i < len; i++) {
                    Task item = toDo.get(i);
                    if (item.getDescription().contains(findString)) {
                        findResponse.append(i + 1).append(". [").append(item.getStatusIcon()).append("]")
                                .append(item.toString());
                    }
                }
                return findResponse.toString();
                
            case "bye":
                String goodbye = "Goodbye! Hopefully I see you again... *sad quack*";
                return goodbye;

            default:
                return "Sorry, that is not a valid command. PLease try again!";
        }
    }
}