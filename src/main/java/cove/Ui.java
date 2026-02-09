package cove;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {

    /** Scanner to read user input */
    private Scanner scanner;

    /**
     * Creates a new cove.Ui for console printing and reading functionality.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);

        assert this.scanner != null : "Scanner should be initialised";
    }

    /**
     * Prints long horizontal line separator to the console.
     */
    public void printLongLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints greeting message from cove.Cove to the console.
     */
    public void printGreeting() {
        printLongLine();
        System.out.println(" Hello! I'm Cove");
        System.out.println(" What can I do for you?");
        printLongLine();
        System.out.println();
    }

    /**
     * Prints exit message from cove.Cove to the console.
     */
    public void printExit() {
        System.out.println(" Bye. Hope to see you again soon!");
        printLongLine();
    }

    /**
     * Prints the user's task list to the console.
     * Prints information about each task's task type, isDone status, description,
     * deadline date (for cove.Deadline tasks), start and end dates (for cove.Event tasks).
     *
     * @param tasks The user's task list.
     */
    public void printTaskList(TaskList tasks) {
        assert tasks != null : "Task list should not be null";

        System.out.println(" Here are the tasks in your list:");
        for (int i = 1; i <= tasks.size(); i++) {
            String taskString = tasks.getTask(i).toString();

            assert taskString != null : "Task string should not be null";

            System.out.printf(" %d.%s\n", i, taskString);
        }
        printLongLine();
        System.out.println();
    }

    /**
     * Prints a message about the number of tasks in the user's task list to the console.
     *
     * @param numOfTasks The total number of tasks in the user's task list.
     */
    public void printNumOfTasks(int numOfTasks) {
        assert numOfTasks >= 0 : "Number of tasks should not be negative";

        if (numOfTasks == 1) {
            System.out.println(" Now you have 1 task in the list.");
        } else {
            System.out.println(" Now you have " + numOfTasks + " tasks in the list.");
        }
    }

    /**
     * Prints a confirmation message about the most recent task added to the list to the console.
     *
     * @param task The task to print as added.
     * @param numOfTasks The total number of tasks in the user's task list.
     */
    public void printTaskAdded(Task task, int numOfTasks) {
        assert task != null : "Task should not be null";
        assert numOfTasks >= 1 : "Number of tasks should be at least 1 after adding a task";

        System.out.println(" Got it. I've added this task:");
        System.out.println(" " + task.toString());
        printNumOfTasks(numOfTasks);
        printLongLine();
        System.out.println();
    }

    /**
     * Prints a confirmation message about the most recent task added to the list to the console.
     *
     * @param task The task to print as deleted.
     * @param numOfTasks The total number of tasks in the user's task list.
     */
    public void printTaskDeleted(Task task, int numOfTasks) {
        assert task != null : "Task should not be null";
        assert numOfTasks >= 0 : "Number of tasks should not be negative after deleting a task";

        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task.toString());
        printNumOfTasks(numOfTasks);
        printLongLine();
        System.out.println();
    }

    /**
     * Prints a confirmation message about the specified task being marked as done.
     *
     * @param task The task to print as marked as done.
     */
    public void printTaskMarked(Task task) {
        assert task != null : "Task should not be null";
        assert task.isDone() : "Task should be marked as done";

        System.out.println(" Nice! I've marked this task as done:");
        System.out.println(" " + task.toString());
        printLongLine();
        System.out.println();
    }

    /**
     * Prints a confirmation message about the specified task being marked as not done.
     *
     * @param task The task to print as marked as not done.
     */
    public void printTaskUnmarked(Task task) {
        assert task != null : "Task should not be null";
        assert !task.isDone() : "Task should be marked as not done";

        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println(" " + task.toString());
        printLongLine();
        System.out.println();
    }

    /**
     * Reads the user's input to the console.
     *
     * @return The string containing the user's input to the console.
     */
    public String readUserInput() {
        assert this.scanner != null : "Scanner should not be null";

        String userInput = this.scanner.nextLine();

        assert userInput != null : "User input should not be null";

        return userInput;
    }

    public void printTasksWithMatchingKeyword(ArrayList<Task> matchingTasks) {
        assert matchingTasks != null : "Tasks list should not be null";

        System.out.println(" Here are the matching tasks in your list:");

        for (int i = 1; i <= matchingTasks.size(); i++) {
            Task matchingTask = matchingTasks.get(i - 1);

            assert matchingTask != null : "Matching task should not be null";

            System.out.println(" " + matchingTask.getIndex() + "." + matchingTask.toString());
        }

        printLongLine();
    }

    // Methods for gui
    public String getGreetingAsString() {
        return " Hello! I'm Cove\n What can I do for you?";
    }

    public String getByeAsString(String arguments) {
        assert arguments != null : "Arguments should not be null";
        return " Bye. Hope to see you again soon!";
    }

    public String getTaskListAsString(TaskList tasks) {
        assert tasks != null : "Task list should not be null";

        StringBuilder sb = new StringBuilder();
        sb.append(" Here are the tasks in your list:");
        for (int i = 1; i <= tasks.size(); i++) {
            String taskString = tasks.getTask(i).toString();

            assert taskString != null : "Task string should not be null";

            sb.append("\n ").append(i).append(". ").append(taskString);
        }
        return sb.toString();
    }

    public String getMarkedTaskAsString(Task task) {
        return " Nice! I've marked this task as done:\n " + task.toString();
    }

    public String getUnmarkedTaskAsString(Task task) {
        return " OK, I've marked this task as not done yet:\n " + task.toString();
    }

    public String getNumOfTasksAsString(int numOfTasks) {
        if (numOfTasks == 1) {
            return " Now you have 1 task in the list.";
        } else {
            return " Now you have " + numOfTasks + " tasks in the list.";
        }
    }

    public String getTaskAddedString(Task task, int numOfTasks) {
        return " Got it. I've added this task:"
                + "\n "
                + task.toString()
                + " \n"
                + getNumOfTasksAsString(numOfTasks);
    }

    public String getTaskDeletedString(Task task, int numOfTasks) {
        return " Noted. I've removed this task:"
                + "\n   "
                + task.toString()
                + " \n"
                + getNumOfTasksAsString(numOfTasks);
    }

    public String getTaskUpdatedString(Task task) {
        return " Got it. I've updated this task:"
                + "\n "
                + task.toString();
    }

}
