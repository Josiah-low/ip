import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main entry point for Cove chatbot.
 * Cove allows the user to add 3 different types of tasks to a list: ToDo, Deadline, and Event.
 * Users can mark their tasks as done/not done, delete tasks, and view their task list.
 */
public class Cove {
    private static ArrayList<Task> tasks = new ArrayList<Task>();

    public static void main(String[] args) {
        // Initialise scanner and greet user
        Scanner scanner = new Scanner(System.in);
        printGreeting();

        // Create cove.txt file if it does not exist yet and load tasks into tasks
        File data = new File("./data/cove.txt");
        try {
            data.createNewFile();
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        loadTasks();

        // Run main loop
        while (true) {
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
                    handleTodo(userInput);
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

                case "delete": {
                    handleDelete(userInput);
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

    /**
     * Prints long horizontal line separator to the console.
     */
    public static void printLongLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints greeting message from Cove to the console.
     */
    public static void printGreeting() {
        printLongLine();
        System.out.println(" Hello! I'm Cove");
        System.out.println(" What can I do for you?");
        printLongLine();
        System.out.println();
    }

    /**
     * Prints exit message from Cove to the console.
     */
    public static void printExit() {
        System.out.println(" Bye. Hope to see you again soon!");
        printLongLine();
    }

    /**
     * Marks task as not done and saves the updated task list.
     * Obtains the task at the user specified index to set its isDone status to true,
     * updates the changes to the data file, and prints a confirmation to the console.
     *
     * @param taskIndex index of the task to mark as done.
     */
    public static void markTaskAsDone(int taskIndex) {
        Task task = tasks.get(taskIndex - 1);
        task.setDone(true);
        saveTasks();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println(" " + task.toString());
        printLongLine();
        System.out.println();
    }

    /**
     * Marks task as not done and saves the updated task list.
     * Obtains the task at the user specified index to set its isDone status to false,
     * updates the changes to the data file, and prints a confirmation to the console.
     *
     * @param taskIndex index of the task to mark as done.
     */
    public static void unmarkTaskAsDone(int taskIndex) {
        Task task = tasks.get(taskIndex - 1);
        task.setDone(false);
        saveTasks();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println(" " + task.toString());
        printLongLine();
        System.out.println();
    }

    /**
     * Prints the user's task list to the console.
     * Prints information about each task's task type, isDone status, description,
     * deadline (for Deadline tasks), start and end (for Event tasks).
     */
    public static void printTaskList() {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 1; i <= tasks.size(); i++) {
            System.out.printf(" %d.%s\n", i, tasks.get(i - 1).toString());
        }
        printLongLine();
        System.out.println();
    }

    /**
     * Deletes task and saves the updated task list.
     * Removes the task at the user specified index from the tasks ArrayList,
     * updates the changes to the data file, and prints a confirmation to the console.
     *
     * @param taskIndex index of the task to delete.
     */
    public static void deleteTask(int taskIndex) {
        System.out.println(" Noted. I've removed this task:");
        Task task = tasks.remove(taskIndex - 1);
        System.out.println("   " + task.toString());
        printNumOfTasks();
        printLongLine();
        System.out.println();
    }

    /**
     * Prints a message about the number of tasks in the user's task list to the console.
     */
    public static void printNumOfTasks() {
        if (tasks.size() == 1) {
            System.out.println(" Now you have 1 task in the list.");
        } else {
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        }
    }

    /**
     * Prints a confirmation message about the most recent task added to the list to the console.
     */
    public static void printTaskAdded() {
        System.out.println(" Got it. I've added this task:");
        System.out.println(" " + tasks.get(tasks.size() - 1).toString());
        printNumOfTasks();
        printLongLine();
        System.out.println();
    }

    // Command Handling Helper Methods

    /**
     * Handles the bye command to exit the application.
     * Ensures that the bye command entered has no extra parameters, then prints the exit message to the console.
     *
     * @param userInput Complete userInput string entered into the console.
     * @throws CoveException if the userInput contains anything other than "bye".
     */
    public static void handleBye(String userInput) throws CoveException {
        if (!userInput.trim().equals("bye")) {
            throw new CoveException("OOPS! 'bye' command does not accept any parameters.");
        }
        printExit();
    }

    /**
     * Handles the list command to display task list.
     * Ensures that the list command entered has no extra parameters, then prints the task list to the console.
     *
     * @param userInput Complete userInput string entered into the console.
     * @throws CoveException if the userInput contains anything other than "list".
     */
    public static void handleList(String userInput) throws CoveException {
        if (!userInput.trim().equals("list")) {
            throw new CoveException("OOPS! 'list' command does not accept any parameters.");
        }
        printTaskList();
    }

    /**
     * Handles the mark command to mark a specified task as done.
     * Ensures that the mark command entered has only one parameter (a valid task number),
     * and marks the specified task as done.
     *
     * @param userInput Complete userInput string entered into the console.
     * @throws CoveException if a task number is not specified or is invalid, or more than 1 parameter is provided.
     */
    public static void handleMark(String userInput) throws CoveException {
        String[] words = userInput.split(" ");
        if (words.length < 2) {
            throw new CoveException("OOPS! You didn't specify a task number to mark.");
        } else if (words.length > 2) {
            throw new CoveException("OOPS! 'mark' command only accepts 1 parameter.");
        }
        int taskIndex = Integer.parseInt(words[1]);
        if (taskIndex < 1 || taskIndex > tasks.size()) {
            throw new CoveException("OOPS! The task number you provided is invalid.");
        }
        markTaskAsDone(taskIndex);
    }

    /**
     * Handles the unmark command to mark a specified task as not done.
     * Ensures that the mark command entered has only one parameter (a valid task number),
     * and marks the specified task as not done.
     *
     * @param userInput Complete userInput string entered into the console.
     * @throws CoveException if a task number is not specified or is invalid, or more than 1 parameter is provided.
     */
    public static void handleUnmark(String userInput) throws CoveException {
        String[] words = userInput.split(" ");
        if (words.length < 2) {
            throw new CoveException("OOPS! You didn't specify a task number to unmark as done.");
        } else if (words.length > 2) {
            throw new CoveException("OOPS! 'unmark' command only accepts 1 parameter.");
        }
        int taskIndex = Integer.parseInt(words[1]);
        if (taskIndex < 1 || taskIndex > tasks.size()) {
            throw new CoveException("OOPS! The task number you provided is invalid.");
        }
        unmarkTaskAsDone(taskIndex);
    }

    /**
     * Handles the todo command to create a new ToDo task.
     * Obtains the task description from the user input, ensures that it is not empty,
     * creates a new ToDo task, adds it to the task list, and saves the updated list.
     *
     * @param userInput Complete userInput string entered into the console.
     * @throws CoveException if the task description is empty.
     */
    public static void handleTodo(String userInput) throws CoveException {
        String description = userInput.split("todo", 2)[1].trim();
        if (description.isEmpty()) {
            throw new CoveException("OOPS! The description of a todo cannot be empty.");
        }
        tasks.add(new ToDo(description));
        saveTasks();
        printTaskAdded();
    }

    /**
     * Handles the deadline command to create a new Deadline task.
     * Obtains the task description and deadline from the user input, ensure that they are not empty,
     * creates a new Deadline task, adds it to the task list, and saves the updated list.
     *
     * @param userInput Complete userInput string entered into the console.
     * @throws CoveException if the task description or deadline is empty, or no /by separator is used.
     */
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

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            tasks.add(new Deadline(description, LocalDate.parse(by, formatter)));
        } catch (DateTimeParseException e) {
            throw new CoveException("OOPS! Invalid date format! Your dates must be in the format of \"yyyy/mm/dd\".");
        }

        saveTasks();
        printTaskAdded();
    }

    /**
     * Handles the event command to create a new Event task.
     * Obtains the task description, start, and end from the user input, ensure that they are not empty,
     * creates a new Event task, adds it to the task list, and saves the updated list.
     *
     * @param userInput Complete userInput string entered into the console.
     * @throws CoveException if the task description, start, or end is empty, or no /from or /to separator is used.
     */
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

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            tasks.add(new Event(description, LocalDate.parse(start, formatter),
                    LocalDate.parse(end, formatter)));
        } catch (DateTimeParseException e) {
            throw new CoveException("OOPS! Invalid date format! Your dates must be in the format of \"yyyy/mm/dd\".");
        }

        saveTasks();
        printTaskAdded();
    }

    /**
     * Handles unrecognised commands.
     * Throws an exception to inform the user that the command entered is not recognised by Cove.
     *
     * @throws CoveException always.
     */
    public static void handleUnknownCommand() throws CoveException {
        throw new CoveException("OOPS! I don't understand what you mean!");
    }

    /**
     * Handles the delete command to remove a specified task from the task list.
     * Ensures that the delete command entered has only one parameter (a valid task number),
     * and deletes the task from the task list.
     *
     * @param userInput Complete userInput string entered into the console.
     * @throws CoveException if a task number is not specified or is invalid, or more than 1 parameter is provided.
     */
    public static void handleDelete(String userInput) throws CoveException {
        String[] words = userInput.split(" ");
        if (words.length < 2) {
            throw new CoveException("OOPS! You didn't specify a task number to delete.");
        } else if (words.length > 2) {
            throw new CoveException("OOPS! 'delete' command only accepts 1 parameter.");
        }
        int taskIndex = Integer.parseInt(words[1]);
        if (taskIndex < 1 || taskIndex > tasks.size()) {
            throw new CoveException("OOPS! The task number you provided is invalid.");
        }
        deleteTask(taskIndex);
        saveTasks();
    }

    // Loading And Saving Helper Methods

    /**
     * Loads a single task from a data string from the data file and adds it to the task list.
     * Obtains the task type specified by the first character of the data string,
     * obtains the other relevant fields for the task, then creates the task with those fields.
     * Marks it as done if the second character of the data string is '1', then adds the task
     * to the task list.
     *
     * @param dataString the string representation of a task
     * @throws CoveException if the task type is unrecognised or the data format is invalid.
     */
    private static void loadTask(String dataString) throws CoveException {
        switch (dataString.charAt(0)) {
        case 'T': {
            String description = dataString.split("\\|", 2)[1];
            Task taskToLoad = new ToDo(description);
            if (dataString.charAt(1) == '1') {
                taskToLoad.setDone(true);
            }
            tasks.add(taskToLoad);
            break;
        }
        case 'D': {
            String[] words = dataString.split("\\|", 3);
            String description = words[1];
            String by = words[2];

            Task taskToLoad = new Deadline(description, LocalDate.parse(by));
            if (dataString.charAt(1) == '1') {
                taskToLoad.setDone(true);
            }
            tasks.add(taskToLoad);
            break;
        }
        case 'E': {
            String[] words = dataString.split("\\|", 4);
            String description = words[1];
            String start = words[2];
            String end = words[3];

            Task taskToLoad = new Event(description, LocalDate.parse(start), LocalDate.parse(end));
            if (dataString.charAt(1) == '1') {
                taskToLoad.setDone(true);
            }
            tasks.add(taskToLoad);
            break;
        }
        default: {
            throw new CoveException("Unrecognised task type.");
        }
        }
    }

    /**
     * Loads all tasks from the data file into the task list.
     * Reads line by line from the data file for a task data string,
     * and loads each task into the task list.
     */
    private static void loadTasks() {
        try {
            File data = new File("./data/cove.txt");
            Scanner scanner = new Scanner(data);

            while (scanner.hasNext()) {
                loadTask(scanner.next());
            }
            scanner.close();

        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } catch (CoveException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Appends text to a file at the specified path.
     *
     * @param filePath Path to the file to append the text to.
     * @param text     Text to append to the file.
     * @throws IOException if an I/O error occurs.
     */
    private static void appendToFile(String filePath, String text) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, true);
        fileWriter.write(text);
        fileWriter.close();
    }

    /**
     * Updates the data file to reflect the current task list.
     * Deletes the current data file, then creates it again to append each task's data
     * from the task list into the data file.
     */
    private static void saveTasks() {
        try {
            Files.delete(Paths.get("./data/cove.txt"));
            for (Task task : tasks) {
                appendToFile("./data/cove.txt", task.dataString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

}
