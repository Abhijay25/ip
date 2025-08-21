import java.util.Scanner;

public class Quacker {

//    public void promptUser() {
//        System.out.println("Enter task: ");
//        String prompt = scan.nextLine();
//    }

    public static void main(String[] args) {
        // Initialize a scanner to take in prompt from the user
        Scanner scan = new Scanner(System.in);

        String divider = "\n ----------------------------------- \n"; //35 Dashes

        String welcome = divider
                        + "Hello! I'm Quacker \n"
                        + "What can I do for you? \n"
                        + divider;
        String goodbye = "Bye. Hope to see you again soon! \n";
        String prompt = "";

        System.out.println(welcome);

        while(prompt.equals("bye") == false) {
            // Get user's prompt
            System.out.println("Enter task: ");
            prompt = scan.nextLine();

            // If user says bye, end program
            // Else continue asking for prompts
            String response = divider + " " + prompt + divider;
            System.out.println(response);
        }

        // Close scanner once user has said goodbye
        scan.close();
        System.out.println(goodbye);
    }
}
