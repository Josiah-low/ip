import java.util.Scanner;
import java.util.ArrayList;

public class Cove {
    private static ArrayList<Task> tasks = new ArrayList<Task>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printGreeting();

        while(true) {
            try {
                String userInput = scanner.nextLine();
                printLongLine();
                String command = userInput.split(" ")[0];

                switch (command) {
                    case "bye":
                        handleBye(userInput);
                        return;

                    case "list":
                        handleList(userInput);
                        break;

                    case "mark": {
                        handleMark(userInput);
                        break;
                    }

                    case "unmark": {
                        handleUnmark(userInput);
                        break;
                    }

                    case "todo": {
                        handleToDo(userInput);
                        break;
                    }

                    case "deadline": {
                        handleDeadline(userInput);
                        break;
                    }

                    case "event": {
                        handleEvent(userInput);
                        break;
                    }

                    default: {
                        handleUnknownCommand();
                        break;
                    }
                }
            } catch (CoveException e) {
                System.out.println(e.getMessage());
            }

        }

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
        Task task = tasks.get(taskIndex - 1);
        task.markAsDone();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println(" " + task.toString());
        printLongLine();
        System.out.println();
    }

    public static void unmarkTaskAsDone(int taskIndex) {
        Task task = tasks.get(taskIndex - 1);
        task.markAsNotDone();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println(" " + task.toString());
        printLongLine();
        System.out.println();
    }

    public static void printTaskList() {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 1; i <= tasks.size(); i++) {
            System.out.printf(" %d.%s\n", i, tasks.get(i - 1).toString());
        }
        printLongLine();
        System.out.println();
    }

    public static void printNumOfTasks() {
        if (tasks.size() == 1) {
            System.out.println(" Now you have 1 task in the list.");
        } else {
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        }
    }

    public static void printTaskAdded() {
        System.out.println(" Got it. I've added this task:");
        System.out.println(" " + tasks.get(tasks.size() - 1).toString());
        printNumOfTasks();
        printLongLine();
        System.out.println();
    }

    // Command Handling

    public static void handleBye(String userInput) throws CoveException {
        if (!userInput.trim().equals("bye")) throw new CoveException("OOPS! 'bye' command does not accept any parameters.");
        printExit();
    }

    public static void handleList(String userInput) throws CoveException {
        if (!userInput.trim().equals("list")) throw new CoveException("OOPS! 'list' command does not accept any parameters.");
        printTaskList();
    }

    public static void handleMark(String userInput) throws CoveException {
        String[] words = userInput.split(" ");
        if (words.length < 2) {
            throw new CoveException("OOPS! You didn't specify a task number to mark.");
        } else if (words.length > 2) {
            throw new CoveException("OOPS! 'mark' command only accepts 1 parameter.");
        }
        int taskIndex = Integer.parseInt(words[1]);
        markTaskAsDone(taskIndex);
    }

    public static void handleUnmark(String userInput) throws CoveException {
        String[] words = userInput.split(" ");
        if (words.length < 2) {
            throw new CoveException("OOPS! You didn't specify a task number to unmark as done.");
        } else if (words.length > 2) {
            throw new CoveException("OOPS! 'unmark' command only accepts 1 parameter.");
        }
        int taskIndex = Integer.parseInt(words[1]);
        unmarkTaskAsDone(taskIndex);
    }

    public static void handleToDo(String userInput) throws CoveException {
        String description = userInput.split("todo", 2)[1].trim();
        if (description.isEmpty()) {
            throw new CoveException("OOPS! The description of a todo cannot be empty.");
        }
        tasks.add(new ToDo(description));
        printTaskAdded();
    }

    public static void handleDeadline(String userInput) throws CoveException {
        String description = userInput.split("deadline", 2)[1];
        if (!userInput.contains("/by")) {
            throw new CoveException("OOPS! Please specify a deadline with /by.");
        }
        description = description.split("/by", 2)[0].trim();
        if (description.isEmpty()) {
            throw new CoveException("OOPS! The description of a deadline cannot be empty.");
        }
        String by = userInput.split("/by", 2)[1].trim();
        if (by.isEmpty()) {
            throw new CoveException("OOPS! You didn't specify the deadline.");
        }
        tasks.add(new Deadline(description, by));
        printTaskAdded();
    }

    public static void handleEvent(String userInput) throws CoveException {
        String description = userInput.split("event", 2)[1];
        if (!userInput.contains("/from") || !userInput.contains("/to")) {
            throw new CoveException("OOPS! Please specify a start date/time with /from and an end date/time with /to.");
        }
        description = description.split("/from", 2)[0].trim();
        if (description.isEmpty()) {
            throw new CoveException("OOPS! The description of an event cannot be empty.");
        }
        String start = userInput.split("/from")[1];
        start = start.split("/to", 2)[0].trim();
        if (start.isEmpty()) {
            throw new CoveException("OOPS! You didn't provide a start date/time.");
        }
        String end = userInput.split("/to", 2)[1].trim();
        if (end.isEmpty()) {
            throw new CoveException("OOPS! You didn't provide a end date/time.");
        }
        tasks.add(new Event(description, start, end));
        printTaskAdded();
    }

    public static void handleUnknownCommand() throws CoveException {
        throw new CoveException("OOPS! I don't understand what you mean!");
    }
}
