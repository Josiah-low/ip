package cove;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Main entry point for Cove chatbot.
 * Cove allows the user to add 3 different types of tasks to a list: cove.ToDo, cove.Deadline, and cove.Event.
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
        assert filePath != null : "File path should not be null";
        assert !filePath.isEmpty() : "File path should not  be empty";

        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = new TaskList(this.storage.load());

        assert this.ui != null : "UI should be initialised";
        assert this.storage != null : "Storage should be initialised";
        assert this.tasks != null : "TaskList should be initialised";
    }

    /**
     * Executes the command specified by the user input String and returns Cove's
     * response message as a String for the GUI.
     *
     * @param input The user input String.
     */
    public String getResponse(String input) {
        try {
            String command = Parser.parseCommand(input);
            String arguments = Parser.parseArguments(input);

            assert command != null : "Command should not be null";
            assert arguments != null : "Arguments should not be null";

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

            case "update":
                return handleUpdate(arguments);

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
     * Ensures that the bye command entered has no extra parameters.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if the userInput contains anything other than "bye".
     */
    public void handleBye(String arguments) throws CoveException {
        assert arguments != null : "Arguments should not be null";

        if (!arguments.isEmpty()) {
            throw new CoveException("OOPS! 'bye' command does not accept any parameters.");
        }
    }

    /**
     * Handles the list command to display task list.
     * Ensures that the list command entered has no extra parameters.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if the userInput contains anything other than "list".
     */
    public void handleList(String arguments) throws CoveException {
        assert arguments != null : "Arguments should not be null";

        if (!arguments.isEmpty()) {
            throw new CoveException("OOPS! 'list' command does not accept any parameters.");
        }
    }

    /**
     * Handles the mark command to mark a specified task as done.
     * Ensures that the mark command entered has only one parameter (a valid task number),
     * and marks the specified task as done, then returns marked Task.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException         if a task number is not specified or is invalid, or more than 1 parameter is provided.
     * @throws NumberFormatException if the argument provided is not a valid integer.
     */
    public Task handleMark(String arguments) throws CoveException {
        assert arguments != null : "Arguments should not be null";

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
            assert taskIndex >= 1 && taskIndex <= this.tasks.size() : "Task index should be within valid range";

            Task task = this.tasks.markTask(taskIndex);
            assert task != null : "Marked task should not be null";
            assert task.isDone() : "Task should be marked as done";

            this.storage.save(this.tasks);

            return task;

        } catch (NumberFormatException e) {
            throw new CoveException("OOPS! Task index must be a valid integer.");
        }
    }

    /**
     * Handles the unmark command to mark a specified task as not done.
     * Ensures that the mark command entered has only one parameter (a valid task number),
     * and marks the specified task as not done, then returns the unmarked task.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException         if a task number is not specified or is invalid, or more than 1 parameter is provided.
     * @throws NumberFormatException if the argument provided is not a valid integer.
     */
    public Task handleUnmark(String arguments) throws CoveException {
        assert arguments != null : "Arguments should not be null";

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
            assert taskIndex >= 1 && taskIndex <= this.tasks.size() : "Task index should be within valid range";

            Task task = this.tasks.unmarkTask(taskIndex);
            assert task != null : "Marked task should not be null";
            assert task.isDone() : "Task should be marked as done";

            this.storage.save(this.tasks);

            return task;

        } catch (NumberFormatException e) {
            throw new CoveException("OOPS! Task index must be a valid integer.");
        }
    }

    /**
     * Handles the todo command to create a new cove.ToDo task.
     * Obtains the task description from the user input, ensures that it is not empty,
     * creates a new ToDo task, adds it to the task list, and saves the updated list,
     * then returns the created Task.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if the task description is empty.
     */
    public Task handleTodo(String arguments) throws CoveException {
        assert arguments != null : "Arguments should not be null";

        String description = arguments;

        if (description.isEmpty()) {
            throw new CoveException("OOPS! The description of a todo cannot be empty.");
        }

        Task task = new ToDo(description);
        assert task != null : "Created task should not be null";

        int taskListSizeBefore = this.tasks.size();
        this.tasks.addTask(task);
        assert this.tasks.size() == taskListSizeBefore + 1 : "Task list size should increase by 1";

        this.storage.save(this.tasks);

        return task;
    }

    /**
     * Handles the deadline command to create a new cove.Deadline task.
     * Obtains the task description and deadline date from the user input, ensures that they are not empty,
     * ensures the date format entered is valid, then creates a new cove.Deadline task,
     * adds it to the task list, and saves the updated list, then returns the created Task.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if the task description or deadline is empty, or no /by separator is used.
     */
    public Task handleDeadline(String arguments) throws CoveException {
        assert arguments != null : "Arguments should not be null";

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
            assert task != null : "Created task should not be null";

            int taskListSizeBefore = this.tasks.size();
            this.tasks.addTask(task);
            assert this.tasks.size() == taskListSizeBefore + 1 : "Task list size should increase by 1";

            this.storage.save(this.tasks);

            return task;

        } catch (DateTimeParseException e) {
            throw new CoveException("OOPS! Invalid date format! Your dates must be in the format of \"yyyy/mm/dd\".");
        }
    }

    /**
     * Handles the event command to create a new cove.Event task.
     * Obtains the task description, start date, and end date from the user input,
     * ensures that they are not empty, ensures the date formats entered are valid,
     * then creates a new cove.Event task, adds it to the task list, and saves the updated list,
     * then returns the created Task.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if the task description, start, or end is empty, or no /from or /to separator is used.
     */
    public Task handleEvent(String arguments) throws CoveException {
        assert arguments != null : "Arguments should not be null";

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
            assert task != null : "Created task should not be null";

            int taskListSizeBefore = this.tasks.size();
            this.tasks.addTask(task);
            assert this.tasks.size() == taskListSizeBefore + 1 : "Task list size should increase by 1";

            this.storage.save(this.tasks);

            return task;

        } catch (DateTimeParseException e) {
            throw new CoveException("OOPS! Invalid date format! Your dates must be in the format of \"yyyy/mm/dd\".");
        }
    }

    /**
     * Handles the delete command to remove a specified task from the task list.
     * Ensures that the delete command entered has only one parameter (a valid task number),
     * and deletes the task from the task list, then returns the deleted Task.
     *
     * @param arguments Only the arguments part of the userInput string entered into the console.
     * @throws CoveException if a task number is not specified or is invalid, or more than 1 parameter is provided.
     */
    public Task handleDelete(String arguments) throws CoveException {
        assert arguments != null : "Arguments should not be null";

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

            int taskListSizeBefore = this.tasks.size();
            Task task = this.tasks.deleteTask(taskIndex);
            assert task != null : "Deleted task should not be null";
            assert this.tasks.size() == taskListSizeBefore - 1 : "Task list size should decrease by 1";

            this.storage.save(this.tasks);

            return task;

        } catch (NumberFormatException e) {
            throw new CoveException("OOPS! Task index must be a valid integer.");
        }
    }

    /**
     * Handles the find command to search for tasks whose descriptions contain a given keyword.
     * Ensures that a keyword is provided, retrieves all matching tasks from the task list,
     * prints them to the console, and returns the result as a formatted string for the GUI.
     *
     * @param arguments Only the arguments part of the userInput string, used as the search keyword.
     * @return A formatted string listing all tasks whose descriptions contain the keyword.
     * @throws CoveException if no keyword is provided, or if no matching tasks are found.
     */
    public String handleFind(String arguments) throws CoveException {
        assert arguments != null : "Arguments should not be null";

        if (arguments.isEmpty()) {
            throw new CoveException("OOPS! You didn't specify a keyword to search for.");
        }

        ArrayList<Task> matchingTasks = this.tasks.getTasksWithMatchingKeyword(arguments);
        assert matchingTasks != null : "Matching tasks list should not be null";

        if (matchingTasks.isEmpty()) {
            throw new CoveException("OOPS! None of your tasks contains the keyword.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(" Here are the matching tasks in your list:");
        for (Task task : matchingTasks) {
            sb.append("\n ").append(task.getIndex()).append(". ").append(task.toString());
        }
        return sb.toString();
    }

    /**
     * Handles the update command to modify a field of an existing task.
     * Parses the task index and update arguments from the provided string, validates the task index,
     * delegates the update to the appropriate task, saves the updated task list, and returns
     * a confirmation message for the GUI.
     * <p>
     * Supported update fields depend on the task type:
     * <ul>
     *   <li>{@code /desc} — updates the task description (all task types)</li>
     *   <li>{@code /by} — updates the deadline date (Deadline tasks only)</li>
     *   <li>{@code /from} — updates the start date (Event tasks only)</li>
     *   <li>{@code /to} — updates the end date (Event tasks only)</li>
     * </ul>
     *
     * @param arguments The arguments part of the userInput string, containing the task index
     *                  and the field to update (e.g., {@code "3 /desc new description"}).
     * @return A formatted string confirming the updated task for the GUI.
     * @throws CoveException if the task index is missing or invalid, if the field specifier
     *                       is missing or incompatible with the task type, or if the new
     *                       value is empty or improperly formatted.
     */
    public String handleUpdate(String arguments) throws CoveException {
        assert arguments != null : "Arguments should not be null";

        String[] words = arguments.split(" ", 2);

        if (words.length < 2) {
            throw new CoveException("OOPS! Please specify task number and field to update.\n" +
                    "Examples:\n" +
                    "  update 3 /desc new description\n" +
                    "  update 3 /by 2025-03-15\n" +
                    "  update 2 /from 2025-03-10");
        }

        try {
            int taskIndex = Integer.parseInt(words[0]);

            if (taskIndex < 1 || taskIndex > this.tasks.size()) {
                throw new CoveException("OOPS! The task number you provided is invalid.");
            }

            String updateArguments = words[1];

            Task task = this.tasks.updateTask(taskIndex, updateArguments);

            this.storage.save(this.tasks);

            return ui.getTaskUpdatedString(task);

        } catch (NumberFormatException e) {
            throw new CoveException("OOPS! Task index must be a valid integer.");
        } catch (CoveException e) {
            throw new CoveException(e.getMessage());
        }
    }

}