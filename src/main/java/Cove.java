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
            String command = userInput.split(" ")[0];
            if (Objects.equals(userInput, "bye")) {
                break;
            } else if (Objects.equals(userInput, "list")) {
                printTaskList();
            } else if (Objects.equals(command, "mark")) {
                String[] words = userInput.split(" ");
                int taskIndex = Integer.parseInt(words[1]);
                markTaskAsDone(taskIndex);
            } else if (Objects.equals(command, "unmark")) {
                String[] words = userInput.split(" ");
                int taskIndex = Integer.parseInt(words[1]);
                unmarkTaskAsDone(taskIndex);
            } else {
                // Add task
                System.out.println(" Got it. I've added this task:");
                if (Objects.equals(command, "todo")) {
                    String description = userInput.split("todo ")[1];
                    tasks[Task.getNumOfTasks()] = new ToDo(description);
                } else if (Objects.equals(command, "deadline")) {
                    String description = userInput.split("deadline ")[1];
                    description = description.split(" /by ")[0];
                    String by = userInput.split(" /by ")[1];
                    tasks[Task.getNumOfTasks()] = new Deadline(description, by.stripTrailing());
                } else if (Objects.equals(command, "event")) {
                    String description = userInput.split("event ")[1];
                    description = description.split(" /from ")[0];
                    String start = userInput.split(" /from ")[1];
                    start = start.split(" /to ")[0];
                    String end = userInput.split(" /to ")[1];
                    tasks[Task.getNumOfTasks()] = new Event(description, start, end.stripTrailing());
                }
                System.out.println(" " + tasks[Task.getNumOfTasks() - 1].toString());
                printNumOfTasks();
                printLongLine();
                System.out.println();
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
        System.out.println();
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
        System.out.println();
    }

    public static void unmarkTaskAsDone(int taskIndex) {
        Task task = tasks[taskIndex - 1];
        task.markAsNotDone();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println(" " + task.toString());
        printLongLine();
        System.out.println();
    }

    public static void printTaskList() {
        System.out.println(" Here are the tasks in your list:");
        for (Task task : tasks) {
            if (task == null) {
                printLongLine();
                System.out.println();
                break;
            } else {
                System.out.printf(" %d.%s\n", task.getIndex(), task.toString());
            }
        }
    }

    public static void printNumOfTasks() {
        if (Task.getNumOfTasks() == 1) {
            System.out.println(" Now you have 1 task in the list.");
        } else {
            System.out.println(" Now you have " + Task.getNumOfTasks() + " tasks in the list.");
        }
    }

}
