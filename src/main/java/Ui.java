import java.util.ArrayList;
import java.util.Scanner;

public class Ui {

    /** Scanner to read user input */
    private Scanner scanner;

    /**
     * Creates a new Ui for console printing and reading functionality.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints long horizontal line separator to the console.
     */
    public void printLongLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints greeting message from Cove to the console.
     */
    public void printGreeting() {
        printLongLine();
        System.out.println(" Hello! I'm Cove");
        System.out.println(" What can I do for you?");
        printLongLine();
        System.out.println();
    }

    /**
     * Prints exit message from Cove to the console.
     */
    public void printExit() {
        System.out.println(" Bye. Hope to see you again soon!");
        printLongLine();
    }

    /**
     * Prints the user's task list to the console.
     * Prints information about each task's task type, isDone status, description,
     * deadline date (for Deadline tasks), start and end dates (for Event tasks).
     *
     * @param tasks The user's task list.
     */
    public void printTaskList(ArrayList<Task> tasks) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 1; i <= tasks.size(); i++) {
            System.out.printf(" %d.%s\n", i, tasks.get(i - 1).toString());
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
        return scanner.nextLine();
    }

}
