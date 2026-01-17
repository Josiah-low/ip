import java.util.Objects;
import java.util.Scanner;

public class Cove {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] tasks = new String[100];

        printGreeting();

        while(true) {
            String userInput = scanner.nextLine();
            printLongLine();
            if (Objects.equals(userInput, "bye")) {
                break;
            } else {
                System.out.println(" " + userInput);
                printLongLine();
            }
        }

        printExit();
    }

    public static void printLongLine() {
        System.out.println("____________________________________________________________");
    }

    public static void printGreeting() {
        printLongLine();
        System.out.println(" Hello! I'm Cove");
        System.out.println(" What can I do for you?");
        printLongLine();
    }

    public static void printExit() {
        System.out.println(" Bye. Hope to see you again soon!");
        printLongLine();
    }
}
