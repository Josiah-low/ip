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
                printTaskList();
            } else if (userInput.startsWith("mark ")) {
                String[] words = userInput.split(" ");
                int taskIndex = Integer.parseInt(words[1]);
                markTaskAsDone(taskIndex);
            } else if (userInput.startsWith("unmark ")) {
                String[] words = userInput.split(" ");
                int taskIndex = Integer.parseInt(words[1]);
                unmarkTaskAsDone(taskIndex);
            }else {
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

    public static void markTaskAsDone(int taskIndex) {
        Task task = tasks[taskIndex - 1];
        task.markAsDone();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println(" " + task.toString());
        printLongLine();
    }

    public static void unmarkTaskAsDone(int taskIndex) {
        Task task = tasks[taskIndex - 1];
        task.markAsNotDone();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println(" " + task.toString());
        printLongLine();
    }

    public static void printTaskList() {
        System.out.println(" Here are the tasks in your list:");
        for (Task task : tasks) {
            if (task == null) {
                printLongLine();
                break;
            } else {
                System.out.printf(" %d.%s\n", task.getIndex(), task.toString());
            }
        }
    }

}
