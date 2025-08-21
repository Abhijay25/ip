import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Quacker {

    public static void main(String[] args) {
        // Initialize a scanner to take in prompt from the user
        Scanner scan = new Scanner(System.in);
        List<String> toDo = new ArrayList<>();

        String divider = "\n----------------------------------- \n"; //35 Dashes

        String welcome = divider
                        + "Hello! I'm Quacker \n"
                        + "What can I do for you? \n"
                        + divider;
        String goodbye = "Bye. Hope to see you again soon! \n";
        String prompt = "";

        System.out.println(welcome);

        while(prompt.equals("bye") == false) {
            System.out.println("Enter task: ");
            prompt = scan.nextLine();

            // If user asks for current list, display all
            if(prompt.equals("list")) {
                int len = toDo.size();

                if (len == 0) {
                    String emptyResponse = divider + "Nothing to display" + divider;
                    System.out.println(emptyResponse);
                } else {
                    StringBuilder listResponse = new StringBuilder(divider);
                    for (int i = 0; i < len; i++) {
                        if(i == len - 1) {
                            listResponse.append(i + 1).append(" ").append(toDo.get(i));
                        } else {
                            listResponse.append(i + 1).append(" ").append(toDo.get(i)).append("\n");
                        }
                    }
                    listResponse.append(divider);
                    System.out.println(listResponse);
                }

            } else {
                // Add prompt tolist and provide visual confirmation
                toDo.add(prompt);
                String response = divider + "added " + prompt + divider;
                System.out.println(response);
            }
        }

        // Close scanner once user has said goodbye
        scan.close();
        System.out.println(goodbye);
    }
}
