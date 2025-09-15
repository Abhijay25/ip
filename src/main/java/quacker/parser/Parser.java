package quacker.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private static final String ERROR_INVALID_TASK = "Please enter a valid task number!";
    private static final String ERROR_UNKNOWN_CMD = "Sorry, that is not a valid command. Please try again!";
    private static final String GOODBYE_MSG = "Goodbye! Hopefully I see you again... *sad quack*";

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
     * @param input The command given by the user
     */
    public String parse(String input) {
        String[] parts = input.trim().split(" ", 2);
        String command = parts[0].toLowerCase(); // Case-insensitive command
        String args = (parts.length > 1) ? parts[1].trim() : ""; // Preserve original casing for arguments
        int len = toDo.size();

        switch(command) {
            // If user asks for current list, display all
            case "list":
                if (len == 0) {
                    return "Nothing to display. Try giving me something to add!";
                }
                StringBuilder listResponse = new StringBuilder();
                for (int i = 0; i < len; i++) {
                    Task item = toDo.get(i);
                    listResponse.append(i + 1).append(". [")
                            .append(item.getStatusIcon()).append("]")
                            .append(item.toString());
                    if (i < len - 1) {
                        listResponse.append("\n");
                    }
                }
                return listResponse.toString();

            // Mark the task complete after checking for valid number
            case "mark":
                try {
                    int taskNumber = Integer.parseInt(args);
                    if (taskNumber <= 0 || taskNumber > toDo.size()) {
                        return ERROR_INVALID_TASK;
                    }
                    toDo.get(taskNumber - 1).setComplete();
                    file.save(toDo);
                    return "'" + toDo.get(taskNumber - 1).getDescription()
                            + "' has been marked completed!";
                } catch (NumberFormatException e) {
                    return ERROR_INVALID_TASK;
                }

                // Mark the task incomplete after checking for valid number
            case "unmark":
                try {
                    int taskNumber = Integer.parseInt(args);
                    if (taskNumber <= 0 || taskNumber > toDo.size()) {
                        return ERROR_INVALID_TASK;
                    }
                    toDo.get(taskNumber - 1).setIncomplete();
                    file.save(toDo);
                    return "'" + toDo.get(taskNumber - 1).getDescription()
                            + "' has been unmarked!";
                } catch (NumberFormatException e) {
                    return ERROR_INVALID_TASK;
                }

                // Add Deadlines to the list
            case "deadline":
                String deadlineError = "Please use the format: deadline [description] /by [DD-MM-YYYY HHmm] for deadlines";
                if (!args.contains("/by")) {
                    return deadlineError;
                }
                String[] breakdown = args.split("/by", 2);
                if (breakdown.length < 2 || breakdown[0].trim().isEmpty() || breakdown[1].trim().isEmpty()) {
                    return deadlineError;
                }
                try {
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("d-M-yyyy HHmm");
                    String description = breakdown[0].trim();
                    String cutoff = breakdown[1].trim();

                    if (cutoff.contains(" #")) {
                        int lastHash = cutoff.lastIndexOf(" #");
                        String tag = cutoff.substring(lastHash).trim();
                        String deadline = cutoff.substring(0, lastHash).trim();

                        LocalDateTime deadlineDateTime = LocalDateTime.parse(deadline, format);
                        String finalDeadline = deadlineDateTime.format(DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a"));
                        toDo.add(new Deadline(description, finalDeadline, tag));
                        file.save(toDo);
                        return "'" + description + " By: " + finalDeadline + "' has been added to the list!";
                    } else {
                        LocalDateTime deadlineDateTime = LocalDateTime.parse(cutoff, format);
                        String finalDeadline = deadlineDateTime.format(DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a"));
                        toDo.add(new Deadline(description, finalDeadline));
                        file.save(toDo);
                        return "'" + description + " By: " + finalDeadline + "' has been added to the list!";
                    }
                } catch (DateTimeParseException e) {
                    return deadlineError;
                }

                // Add todos to the list
            case "todo":
                if (args.isEmpty()) {
                    return "Please use the format: todo [description] for todos";
                }
                if (args.contains(" #")) {
                    int lastHash = args.lastIndexOf(" #");
                    String tag = args.substring(lastHash).trim();
                    String description = args.substring(0, lastHash).trim();
                    toDo.add(new ToDo(description, tag));
                    file.save(toDo);
                    return "'" + description + "' has been added to the list!";
                } else {
                    toDo.add(new ToDo(args));
                    file.save(toDo);
                    return "'" + args + "' has been added to the list!";
                }

                // Add events to the list
            case "event":
                String eventError = "Please use the format: event [description] /from [time/date] /to [time/date] for events";
                if (!args.contains("/from") || !args.contains("/to")) {
                    return eventError;
                }
                String[] msgParts = args.split("/from", 2);
                if (msgParts.length < 2) {
                    return eventError;
                }
                String desc = msgParts[0].trim();
                String[] timings = msgParts[1].split("/to", 2);
                if (timings.length < 2 || timings[0].trim().isEmpty() || timings[1].trim().isEmpty()) {
                    return eventError;
                }

                String from = timings[0].trim();
                String toTiming = timings[1].trim();
                String returnMsg = "'" + desc + " From:" + from + " To:" + toTiming + "' has been added to the list!";

                if (toTiming.contains(" #")) {
                    int lastHash = toTiming.lastIndexOf(" #");
                    String tag = toTiming.substring(lastHash).trim();
                    String to = toTiming.substring(0, lastHash).trim();
                    toDo.add(new Event(desc, from, to, tag));
                } else {
                    toDo.add(new Event(desc, from, toTiming));
                }
                file.save(toDo);
                return returnMsg;

            // Delete a task
            case "delete":
                try {
                    int taskNumber = Integer.parseInt(args);
                    if (taskNumber <= 0 || taskNumber > toDo.size()) {
                        return ERROR_INVALID_TASK;
                    }
                    String task = toDo.get(taskNumber - 1).toString();
                    toDo.remove(taskNumber - 1);
                    file.save(toDo);
                    return "The following task has been removed from your list: \n" + task;
                } catch (NumberFormatException e) {
                    return ERROR_INVALID_TASK;
                }

                // Find tasks
            case "find":
                StringBuilder findResponse = new StringBuilder("Is This What You're Looking For? \n");
                for (int i = 0; i < len; i++) {
                    Task item = toDo.get(i);
                    if (item.getDescription().contains(args)) {
                        findResponse.append(i + 1).append(". [")
                                .append(item.getStatusIcon()).append("]")
                                .append(item.toString()).append("\n");
                    }
                }
                return findResponse.toString().trim();

            // Exit
            case "bye":
                return GOODBYE_MSG;

            // Unknown command
            default:
                return ERROR_UNKNOWN_CMD;
        }
    }
}
