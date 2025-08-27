import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Quacker {

    public static void main(String[] args) {
        // Initialize a scanner to take in prompt from the user
        Scanner scan = new Scanner(System.in);
        List<Task> toDo = new ArrayList<>();

        String divider = "\n----------------------------------- \n"; //35 Dashes

        String welcome = divider
                        + "Hello! I'm Quacker \n"
                        + "What can I do for you?"
                        + divider;
        String goodbye = divider + "See you! Hopefully I see you again... *sad quack* \n";
        String prompt = "";

        System.out.println(welcome);

        while(prompt.equals("bye") == false) {
            System.out.println("Enter task: ");
            prompt = scan.nextLine();
            prompt = prompt.toLowerCase(); // For case insensitive commands
            
            switch(prompt.split(" ")[0]) {
                // If user asks for current list, display all
                case "list":
                    int len = toDo.size();

                    if (len == 0) {
                        String emptyResponse = divider + "Nothing to display. Try giving me something to add!" + divider;
                        System.out.println(emptyResponse);
                    } else {
                        StringBuilder listResponse = new StringBuilder(divider);
                        for (int i = 0; i < len; i++) {
                            Task item = toDo.get(i);
                            if(i == len - 1) {
                                listResponse.append(i + 1).append(". [").append(item.getStatusIcon()).append("]").append(item.toString());
                            } else {
                                listResponse.append(i + 1).append(". [").append(item.getStatusIcon()).append("]")
                                        .append(item.toString()).append("\n");
                            }
                        }
                        listResponse.append(divider);
                        System.out.println(listResponse);
                    }
                    break;

                // Mark the task complete after checking for valid number
                case "mark":
                    try {
                        String taskString = prompt.substring(5); // To check whether we're given a valid number
                        int taskNumber = Integer.parseInt(taskString);

                        if (taskNumber > toDo.size()) {
                            System.out.println(divider + "Please enter a valid task number!" + divider);
                            continue;
                        }

                        // Once the number is valid, prepare the response message and mark as completed
                        toDo.get(taskNumber - 1).setComplete();
                        StringBuilder markResponse = new StringBuilder(divider);
                        markResponse.append("'").append(toDo.get(taskNumber - 1).getDescription())
                                .append("' has been marked completed!").append(divider);
                        System.out.println(markResponse);

                    }
                    catch (NumberFormatException e){
                        System.out.println(divider + "Please enter a valid task number!" + divider);
                    }
                    break;

                // Mark the task incomplete after checking for valid number
                case "unmark":
                    try {
                        String taskString = prompt.substring(7); // To check whether we're given a valid number
                        int taskNumber = Integer.parseInt(taskString);

                        if (taskNumber > toDo.size()) {
                            System.out.println(divider + "Please enter a valid task number!" + divider);
                            continue;
                        }

                        // Once the number is valid, prepare the response message and mark as completed
                        toDo.get(taskNumber - 1).setIncomplete();
                        StringBuilder markResponse = new StringBuilder(divider);
                        markResponse.append("'").append(toDo.get(taskNumber - 1).getDescription())
                                .append("' has been unmarked!").append(divider);
                        System.out.println(markResponse);

                    }
                    catch (NumberFormatException e){
                        System.out.println(divider + "Please enter a valid task number!" + divider);
                    }
                    break;

                // Add Deadlines to the list
                case "deadline":
                    if (!prompt.contains("/by")) {
                        System.out.println(divider + "Please use the format: deadline [description] /by [time/date] for deadlines" + divider);
                    }
                    String[] breakdown = prompt.substring(9).split("/by", 2);
                    if(breakdown[0].trim().isEmpty() || breakdown[1].trim().isEmpty() || breakdown.length < 2) {
                        System.out.println(divider + "Please use the format: deadline [description] /by [time/date] for deadlines" + divider);
                    } else {
                        // Add to list and provide visual confirmation
                        toDo.add(new Deadline(breakdown[0], breakdown[1]));
                        String response = divider + "'" + breakdown[0] + "' By " + breakdown[1] + " has been added to the list!" + divider;
                        System.out.println(response);
                    }
                    break;

                // Add todos to the list
                case "todo":
                    if (prompt.length() <= 5) {
                        System.out.println(divider + "Please use the format: todo [description] for todos" + divider);
                    } else {
                        String taskString = prompt.substring(5);
                        toDo.add(new ToDo(taskString));
                        String response = divider + "'" + taskString + "' has been added to the list!" + divider;
                        System.out.println(response);
                    }
                    break;
                    
                // Add events to the list
                case "event":
                    if (!prompt.contains("/from") || !prompt.contains("/to")) {
                        System.out.println(divider + "Please use the format: event [description] /from [time/date] /to [time/date] for events"
                                + divider);
                    }
                    String[] msgParts = prompt.substring(6).split("/from", 2);
                    if (msgParts.length < 2){
                        System.out.println(divider + "Please use the format: event [description] /from [time/date] /to [time/date] for events"
                                + divider);
                    } else {
                        String desc = msgParts[0];
                        String[] timings = msgParts[1].split("/to", 2);
                        if (timings.length < 2 || timings[0].trim().isEmpty()|| timings[1].trim().isEmpty()) {
                            System.out.println(divider + "Please use the format: event [description] /from [time/date] /to [time/date] for events"
                                    + divider);
                        } else {
                            toDo.add(new Event(msgParts[0], timings[0], timings[1]));
                            String response = divider + "'" + msgParts[0] + "' From " + timings[0] + " To " + timings[1] +
                                    " has been added to the list!" + divider;
                            System.out.println(response);
                        }
                    }
                    break;

                case "delete":
                    try {
                        String taskString = prompt.substring(7); // To check whether we're given a valid number
                        int taskNumber = Integer.parseInt(taskString);

                        if (taskNumber > toDo.size()) {
                            System.out.println(divider + "Please enter a valid task number!" + divider);
                            continue;
                        }

                        // Once the number is valid, prepare the deletion message
                        String task = toDo.get(taskNumber - 1).toString();
                        StringBuilder markResponse = new StringBuilder(divider);
                        markResponse.append("The following taks has been removed from your list: \n").append(task).append(divider);
                        System.out.println(markResponse);

                        toDo.remove(taskNumber - 1); // Removes the specified task from the ArrayList

                    }
                    catch (NumberFormatException e){
                        System.out.println(divider + "Please enter a valid task number!" + divider);
                    }
                    break;
                    
                default:
                    System.out.println(divider + "Sorry, please try a valid command" + divider);
                    break;
            }
        }

        // Close scanner once user has said goodbye
        scan.close();
        System.out.println(goodbye);
    }
}
