import java.util.Objects;
import java.util.Scanner;

public class Cove {
    private static Task[] tasks = new Task[100];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printGreeting();

        while(true) {
            String userInput = scanner.nextLine();
            printLongLine();
            if (Objects.equals(userInput, "bye")) {
                break;
            } else if (Objects.equals(userInput, "list")) {
                for (Task task : tasks) {
                    if (task == null) {
                        printLongLine();
                        break;
                    } else {
                        task.printTask();
                    }
                }
            } else {
                System.out.println(" added: " + userInput);
                tasks[Task.getNumOfTasks()] = new Task(userInput);
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
