package cove;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Main entry point for Cove chatbot.
 * cove.Cove allows the user to add 3 different types of tasks to a list: cove.ToDo, cove.Deadline, and cove.Event.
 * Users can mark their tasks as done/not done, delete tasks, and view their task list.
 */
public class Cove {

    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    /**
     * Creates and initialises new Cove instance with a specified file path to store task data.
     *
     * @param filePath The path of the file to store the task data.
     */
    public Cove(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = new TaskList(this.storage.load());
    }

    /**
     * Runs the Cove program.
     */
    public void run() {
        this.ui.printGreeting();

        // Run main loop
        while (true) {
            try {
                String userInput = this.ui.readUserInput();
                this.ui.printLongLine();
                String command = Parser.parseCommand(userInput);
                String arguments = Parser.parseArguments(userInput);

                switch (command) {
                case "bye": {
                    handleBye(arguments);
                    return;
                }

                case "list": {
                    handleList(arguments);
                    break;
                }

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

                case "find": {
                    handleFind(arguments);
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

    public String getResponse(String input) {
        try {
            String command = Parser.parseCommand(input);
            String arguments = Parser.parseArguments(input);

            switch (command) {
            case "bye":
                handleBye(arguments);
                return ui.getByeAsString(arguments);

            case "list":
                handleList(arguments);
                return ui.getTaskListAsString(this.tasks);

            case "mark":
                Task markedTask = handleMark(arguments);
                return ui.getMarkedTaskAsString(markedTask);

            case "unmark":
                Task unmarkedTask = handleUnmark(arguments);
                return ui.getUnmarkedTaskAsString(unmarkedTask);

            case "todo":
                Task todoTask = handleTodo(arguments);
                return ui.getTaskAddedString(todoTask, this.tasks.size());

            case "deadline":
                Task deadlineTask = handleDeadline(arguments);
                return ui.getTaskAddedString(deadlineTask, this.tasks.size());

            case "event":
                Task eventTask = handleEvent(arguments);
                return ui.getTaskAddedString(eventTask, this.tasks.size());

            case "delete":
                Task deletedTask = handleDelete(arguments);
                return ui.getTaskDeletedString(deletedTask, this.tasks.size());

            case "find":
                return handleFind(arguments);

            default:
                return "OOPS! I don't understand what you mean!";
            }
        } catch (CoveException e) {
            return e.getMessage().trim();
        }
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
        if (!arguments.isEmpty()) {
            throw new CoveException("OOPS! 'bye' command does not accept any parameters.");
        }

        this.ui.printExit();
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

        this.ui.printTaskList(this.tasks);
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
    public Task handleMark(String arguments) throws CoveException {
        if (arguments.isEmpty()) {
            throw new CoveException("OOPS! You didn't specify a task number to mark.");
        }

        if (arguments.contains(" ")) {
            throw new CoveException("OOPS! 'mark' command only accepts 1 parameter.");
        }

        try {
            int taskIndex = Integer.parseInt(arguments);

            if (taskIndex < 1 || taskIndex > this.tasks.size()) {
                throw new CoveException("OOPS! The task number you provided is invalid.");
            }

            Task task = this.tasks.markTask(taskIndex);
            this.storage.save(this.tasks);
            this.ui.printTaskMarked(task);

            return task;

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
    public Task handleUnmark(String arguments) throws CoveException {
        if (arguments.isEmpty()) {
            throw new CoveException("OOPS! You didn't specify a task number to mark.");
        }

        if (arguments.contains(" ")) {
            throw new CoveException("OOPS! 'mark' command only accepts 1 parameter.");
        }

        try {
            int taskIndex = Integer.parseInt(arguments);

            if (taskIndex < 1 || taskIndex > this.tasks.size()) {
                throw new CoveException("OOPS! The task number you provided is invalid.");
            }

            Task task = this.tasks.unmarkTask(taskIndex);
            this.storage.save(this.tasks);
            this.ui.printTaskUnmarked(task);

            return task;

        } catch (NumberFormatException e) {
            throw new CoveException("OOPS! Task index must be a valid integer.");
        }
    }

    /**
     * Handles the todo command to create a new cove.ToDo task.
     * Obtains the task description from the user input, ensures that it is not empty,
     * creates a new cove.ToDo task, adds it to the task list, and saves the updated list.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if the task description is empty.
     */
    public Task handleTodo(String arguments) throws CoveException {
        String description = arguments;

        if (description.isEmpty()) {
            throw new CoveException("OOPS! The description of a todo cannot be empty.");
        }

        Task task = new ToDo(description);
        this.tasks.addTask(task);
        this.storage.save(this.tasks);
        this.ui.printTaskAdded(task, this.tasks.size());

        return task;
    }

    /**
     * Handles the deadline command to create a new cove.Deadline task.
     * Obtains the task description and deadline date from the user input, ensures that they are not empty,
     * ensures the date format entered is valid, then creates a new cove.Deadline task,
     * adds it to the task list, and saves the updated list.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if the task description or deadline is empty, or no /by separator is used.
     */
    public Task handleDeadline(String arguments) throws CoveException {
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
            Task task = new Deadline(description, LocalDate.parse(by, formatter));
            this.tasks.addTask(task);

            this.storage.save(this.tasks);
            this.ui.printTaskAdded(task, this.tasks.size());

            return task;

        } catch (DateTimeParseException e) {
            throw new CoveException("OOPS! Invalid date format! Your dates must be in the format of \"yyyy/mm/dd\".");
        }
    }

    /**
     * Handles the event command to create a new cove.Event task.
     * Obtains the task description, start date, and end date from the user input,
     * ensures that they are not empty, ensures the date formats entered are valid,
     * then creates a new cove.Event task, adds it to the task list, and saves the updated list.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if the task description, start, or end is empty, or no /from or /to separator is used.
     */
    public Task handleEvent(String arguments) throws CoveException {
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
            Task task = new Event(description, LocalDate.parse(start, formatter),
                    LocalDate.parse(end, formatter));
            this.tasks.addTask(task);

            this.storage.save(this.tasks);
            this.ui.printTaskAdded(this.tasks.getTask(this.tasks.size()), this.tasks.size());

            return task;

        } catch (DateTimeParseException e) {
            throw new CoveException("OOPS! Invalid date format! Your dates must be in the format of \"yyyy/mm/dd\".");
        }
    }

    /**
     * Handles the delete command to remove a specified task from the task list.
     * Ensures that the delete command entered has only one parameter (a valid task number),
     * and deletes the task from the task list.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if a task number is not specified or is invalid, or more than 1 parameter is provided.
     */
    public Task handleDelete(String arguments) throws CoveException {
        if (arguments.isEmpty()) {
            throw new CoveException("OOPS! You didn't specify a task number to delete.");
        }

        if (arguments.contains(" ")) {
            throw new CoveException("OOPS! 'delete' command only accepts 1 parameter.");
        }

        try {
            int taskIndex = Integer.parseInt(arguments);

            if (taskIndex < 1 || taskIndex > this.tasks.size()) {
                throw new CoveException("OOPS! The task number you provided is invalid.");
            }

            Task task = this.tasks.deleteTask(taskIndex);
            this.ui.printTaskDeleted(task, this.tasks.size());
            this.storage.save(this.tasks);

            return task;

        } catch (NumberFormatException e) {
            throw new CoveException("OOPS! Task index must be a valid integer.");
        }
    }

    public String handleFind(String arguments) throws CoveException {
        if (arguments.isEmpty()) {
            throw new CoveException("OOPS! You didn't specify a keyword to search for.");
        }

        ArrayList<Task> matchingTasks = this.tasks.getTasksWithMatchingKeyword(arguments);

        if (matchingTasks.isEmpty()) {
            throw new CoveException("OOPS! None of your tasks contains the keyword.");
        }

        this.ui.printTasksWithMatchingKeyword(matchingTasks);

        // For gui
        StringBuilder sb = new StringBuilder();
        sb.append(" Here are the matching tasks in your list:");
        for (Task task : matchingTasks) {
            sb.append("\n ").append(task.getIndex()).append(".").append(task.toString());
        }
        return sb.toString();
    }

    /**
     * Handles unrecognised commands.
     * Throws an exception to inform the user that the command entered is not recognised by cove.Cove.
     *
     * @throws CoveException always.
     */
    public void handleUnknownCommand() throws CoveException {
        throw new CoveException("OOPS! I don't understand what you mean!");
    }

}
