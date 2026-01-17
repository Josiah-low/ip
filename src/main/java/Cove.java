import java.util.Scanner;

public class Cove {
    private static Task[] tasks = new Task[100];

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
                        if (!userInput.trim().equals("bye")) throw new CoveException("'bye' command does not accept any parameters.");
                        printExit();
                        return;

                    case "list":
                        if (!userInput.trim().equals("list")) throw new CoveException("'list' command does not accept any parameters.");
                        printTaskList();
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
                        String description = userInput.split("deadline ")[1];
                        description = description.split(" /by ")[0];
                        String by = userInput.split(" /by ")[1];
                        tasks[Task.getNumOfTasks()] = new Deadline(description, by.stripTrailing());
                        printTaskAdded();
                        break;
                    }

                    case "event": {
                        String description = userInput.split("event ")[1];
                        description = description.split(" /from ")[0];
                        String start = userInput.split(" /from ")[1];
                        start = start.split(" /to ")[0];
                        String end = userInput.split(" /to ")[1];
                        tasks[Task.getNumOfTasks()] = new Event(description, start, end.stripTrailing());
                        printTaskAdded();
                        break;
                    }

                    default: {
                        System.out.println("Unknown input");
                        printLongLine();
                        System.out.println();
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

    public static void printTaskAdded() {
        System.out.println(" Got it. I've added this task:");
        System.out.println(" " + tasks[Task.getNumOfTasks() - 1].toString());
        printNumOfTasks();
        printLongLine();
        System.out.println();
    }

    // command handling

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
        String description = userInput.split("todo ", 2)[1];
        if (description.replaceAll("\\s+","").isEmpty()) {
            throw new CoveException("OOPS! The description of a todo cannot be empty.");
        }
        tasks[Task.getNumOfTasks()] = new ToDo(description);
        printTaskAdded();
    }

}
