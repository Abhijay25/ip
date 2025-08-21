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
        String goodbye = "See you! Hopefully I see you again... *sad quack* \n";
        String prompt = "";

        System.out.println(welcome);

        while(prompt.equals("bye") == false) {
            System.out.println("Enter task: ");
            prompt = scan.nextLine();
            prompt = prompt.toLowerCase(); // For case insensitive commands

            // If user asks for current list, display all
            if(prompt.equals("list")) {
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
            }

            // Mark the task complete after checking for valid number
            else if(prompt.startsWith("mark")) {
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
            }

            // Mark the task incomplete after checking for valid number
            else if(prompt.startsWith("unmark")) {
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
            }

            // Add Deadlines to the list
            else if (prompt.startsWith("deadline")) {
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
            }

            // Add todos to the list
            else if (prompt.startsWith("todo")) {
                if (prompt.length() <= 5) {
                    System.out.println(divider + "Please use the format: todo [description] for todos" + divider);
                } else {
                    String taskString = prompt.substring(5);
                    toDo.add(new ToDo(taskString));
                    String response = divider + "'" + taskString + "' has been added to the list!" + divider;
                    System.out.println(response);
                }
            }
            else if (prompt.startsWith("event")) {
                if (!prompt.contains("/from") || !prompt.contains("/to")) {
                    System.out.println(divider + "Please use the format: event [description] /from [time/date] /to [time/date] for deadlines"
                            + divider);
                }
                String[] breakdown = prompt.substring(6).split("/from", 2);
                if (breakdown.length < 2){
                    System.out.println(divider + "Please use the format: event [description] /from [time/date] /to [time/date] for deadlines"
                            + divider);
                } else {
                    String desc = breakdown[0];
                    String[] timings = breakdown[1].split("/to", 2);
                    if (timings.length < 2 || timings[0].trim().isEmpty()|| timings[1].trim().isEmpty()) {
                        System.out.println(divider + "Please use the format: event [description] /from [time/date] /to [time/date] for deadlines"
                                + divider);
                    } else {
                        toDo.add(new Event(breakdown[0], timings[0], timings[1]));
                        String response = divider + "'" + breakdown[0] + "' From " + timings[0] + " To " + timings[1] +
                                " has been added to the list!" + divider;
                        System.out.println(response);
                    }
                }
            }

            // Add prompt toDo and provide visual confirmation
            else {
                System.out.println("Please enter a valid prompt in the following format: [Task Type] [Description]");
            }
        }

        // Close scanner once user has said goodbye
        scan.close();
        System.out.println(goodbye);
    }
}
