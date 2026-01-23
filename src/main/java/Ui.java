import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Ui {

    private Scanner scanner;

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
     * deadline (for Deadline tasks), start and end (for Event tasks).
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
     */
    public void printTaskDeleted(Task task, int numOfTasks) {
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task.toString());
        printNumOfTasks(numOfTasks);
        printLongLine();
        System.out.println();
    }

    /**
     * Marks task as not done and saves the updated task list.
     * Obtains the task at the user specified index to set its isDone status to true,
     * updates the changes to the data file, and prints a confirmation to the console.
     *
     * @param task Task to print as marked as done.
     */
    public void printTaskMarked(Task task) {
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
     * @param task Task to print as marked as not done.
     */
    public void printTaskUnmarked(Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println(" " + task.toString());
        printLongLine();
        System.out.println();
    }


}
