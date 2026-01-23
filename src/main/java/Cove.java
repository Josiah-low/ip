import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Main entry point for Cove chatbot.
 * Cove allows the user to add 3 different types of tasks to a list: ToDo, Deadline, and Event.
 * Users can mark their tasks as done/not done, delete tasks, and view their task list.
 */
public class Cove {

    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    public Cove(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    public void run() {
        ui.printGreeting();

        // Run main loop
        while (true) {
            try {
                String userInput = ui.readUserInput();
                ui.printLongLine();
                String command = Parser.parseCommand(userInput);
                String arguments = Parser.parseArguments(userInput);

                switch (command) {
                case "bye":
                    handleBye(arguments);
                    return;

                case "list":
                    handleList(arguments);
                    break;

                case "mark": {
                    handleMark(arguments);
                    break;
                }

                case "unmark": {
                    handleUnmark(arguments);
                    break;
                }

                case "todo": {
                    handleTodo(arguments);
                    break;
                }

                case "deadline": {
                    handleDeadline(arguments);
                    break;
                }

                case "event": {
                    handleEvent(arguments);
                    break;
                }

                case "delete": {
                    handleDelete(arguments);
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

    public static void main(String[] args) {
        new Cove("./data/cove.txt").run();
    }

    // Command handling helper methods

    /**
     * Handles the bye command to exit the application.
     * Ensures that the bye command entered has no extra parameters, then prints the exit message to the console.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if the userInput contains anything other than "bye".
     */
    public void handleBye(String arguments) throws CoveException {
        if (!arguments.equals("bye")) {
            throw new CoveException("OOPS! 'bye' command does not accept any parameters.");
        }

        ui.printExit();
    }

    /**
     * Handles the list command to display task list.
     * Ensures that the list command entered has no extra parameters, then prints the task list to the console.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if the userInput contains anything other than "list".
     */
    public void handleList(String arguments) throws CoveException {
        if (!arguments.isEmpty()) {
            throw new CoveException("OOPS! 'list' command does not accept any parameters.");
        }

        ui.printTaskList(tasks);
    }

    /**
     * Handles the mark command to mark a specified task as done.
     * Ensures that the mark command entered has only one parameter (a valid task number),
     * and marks the specified task as done.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException         if a task number is not specified or is invalid, or more than 1 parameter is provided.
     * @throws NumberFormatException if the argument provided is not a valid integer.
     */
    public void handleMark(String arguments) throws CoveException {
        if (arguments.isEmpty()) {
            throw new CoveException("OOPS! You didn't specify a task number to mark.");
        }

        if (arguments.contains(" ")) {
            throw new CoveException("OOPS! 'mark' command only accepts 1 parameter.");
        }

        try {
            int taskIndex = Integer.parseInt(arguments);

            if (taskIndex < 1 || taskIndex > tasks.size()) {
                throw new CoveException("OOPS! The task number you provided is invalid.");
            }

            Task task = tasks.markTask(taskIndex);
            storage.save(tasks);
            ui.printTaskMarked(task);

        } catch (NumberFormatException e) {
            throw new CoveException("OOPS! Task index must be a valid integer.");
        }
    }

    /**
     * Handles the unmark command to mark a specified task as not done.
     * Ensures that the mark command entered has only one parameter (a valid task number),
     * and marks the specified task as not done.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException         if a task number is not specified or is invalid, or more than 1 parameter is provided.
     * @throws NumberFormatException if the argument provided is not a valid integer.
     */
    public void handleUnmark(String arguments) throws CoveException {
        if (arguments.isEmpty()) {
            throw new CoveException("OOPS! You didn't specify a task number to mark.");
        }

        if (arguments.contains(" ")) {
            throw new CoveException("OOPS! 'mark' command only accepts 1 parameter.");
        }

        try {
            int taskIndex = Integer.parseInt(arguments);

            if (taskIndex < 1 || taskIndex > tasks.size()) {
                throw new CoveException("OOPS! The task number you provided is invalid.");
            }

            Task task = tasks.unmarkTask(taskIndex);
            storage.save(tasks);
            ui.printTaskUnmarked(task);

        } catch (NumberFormatException e) {
            throw new CoveException("OOPS! Task index must be a valid integer.");
        }
    }

    /**
     * Handles the todo command to create a new ToDo task.
     * Obtains the task description from the user input, ensures that it is not empty,
     * creates a new ToDo task, adds it to the task list, and saves the updated list.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if the task description is empty.
     */
    public void handleTodo(String arguments) throws CoveException {
        String description = arguments;

        if (description.isEmpty()) {
            throw new CoveException("OOPS! The description of a todo cannot be empty.");
        }

        tasks.addTask(new ToDo(description));
        storage.save(tasks);
        ui.printTaskAdded(tasks.getTask(tasks.size()), tasks.size());
    }

    /**
     * Handles the deadline command to create a new Deadline task.
     * Obtains the task description and deadline date from the user input, ensures that they are not empty,
     * ensures the date format entered is valid, then creates a new Deadline task,
     * adds it to the task list, and saves the updated list.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if the task description or deadline is empty, or no /by separator is used.
     */
    public void handleDeadline(String arguments) throws CoveException {
        if (!arguments.contains("/by")) {
            throw new CoveException("OOPS! Please specify a deadline with /by.");
        }

        String description = arguments.split("/by", 2)[0].trim();
        if (description.isEmpty()) {
            throw new CoveException("OOPS! The description of a deadline cannot be empty.");
        }

        String by = arguments.split("/by", 2)[1].trim();
        if (by.isEmpty()) {
            throw new CoveException("OOPS! You didn't specify the deadline.");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            tasks.addTask(new Deadline(description, LocalDate.parse(by, formatter)));

        } catch (DateTimeParseException e) {
            throw new CoveException("OOPS! Invalid date format! Your dates must be in the format of \"yyyy/mm/dd\".");
        }

        storage.save(tasks);
        ui.printTaskAdded(tasks.getTask(tasks.size()), tasks.size());
    }

    /**
     * Handles the event command to create a new Event task.
     * Obtains the task description, start date, and end date from the user input,
     * ensures that they are not empty, ensures the date formats entered are valid,
     * then creates a new Event task, adds it to the task list, and saves the updated list.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if the task description, start, or end is empty, or no /from or /to separator is used.
     */
    public void handleEvent(String arguments) throws CoveException {
        if (!arguments.contains("/from") || !arguments.contains("/to")) {
            throw new CoveException("OOPS! Please specify a start date with '/from' and an end date with '/to'.");
        }

        String description = arguments.split("/from", 2)[0].trim();
        if (description.isEmpty()) {
            throw new CoveException("OOPS! The description of an event cannot be empty.");
        }

        String start = arguments.split("/from")[1];
        start = start.split("/to", 2)[0].trim();
        if (start.isEmpty()) {
            throw new CoveException("OOPS! You didn't provide a start date/time.");
        }

        String end = arguments.split("/to", 2)[1].trim();
        if (end.isEmpty()) {
            throw new CoveException("OOPS! You didn't provide a end date/time.");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            tasks.addTask(new Event(description, LocalDate.parse(start, formatter),
                    LocalDate.parse(end, formatter)));

        } catch (DateTimeParseException e) {
            throw new CoveException("OOPS! Invalid date format! Your dates must be in the format of \"yyyy/mm/dd\".");
        }

        storage.save(tasks);
        ui.printTaskAdded(tasks.getTask(tasks.size()), tasks.size());
    }

    /**
     * Handles unrecognised commands.
     * Throws an exception to inform the user that the command entered is not recognised by Cove.
     *
     * @throws CoveException always.
     */
    public void handleUnknownCommand() throws CoveException {
        throw new CoveException("OOPS! I don't understand what you mean!");
    }

    /**
     * Handles the delete command to remove a specified task from the task list.
     * Ensures that the delete command entered has only one parameter (a valid task number),
     * and deletes the task from the task list.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if a task number is not specified or is invalid, or more than 1 parameter is provided.
     */
    public void handleDelete(String arguments) throws CoveException {
        if (arguments.isEmpty()) {
            throw new CoveException("OOPS! You didn't specify a task number to delete.");
        }

        if (arguments.contains(" ")) {
            throw new CoveException("OOPS! 'delete' command only accepts 1 parameter.");
        }

        try {
            int taskIndex = Integer.parseInt(arguments);

            if (taskIndex < 1 || taskIndex > tasks.size()) {
                throw new CoveException("OOPS! The task number you provided is invalid.");
            }

            Task task = tasks.deleteTask(taskIndex);
            ui.printTaskDeleted(task, tasks.size());
            storage.save(tasks);

        } catch (NumberFormatException e) {
            throw new CoveException("OOPS! Task index must be a valid integer.");
        }
    }
}
