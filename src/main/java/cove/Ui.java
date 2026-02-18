package cove;

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

    // Methods for gui

    /**
     * Returns the greeting message as a string for the GUI.
     *
     * @return A string containing Cove's greeting message.
     */
    public String getGreetingAsString() {
        return " Hello! I'm Cove\n What can I do for you?";
    }

    /**
     * Returns the exit message as a string for the GUI.
     *
     * @param arguments The arguments part of the bye command (unused; expected to be empty).
     * @return A string containing Cove's exit message.
     */
    public String getByeAsString(String arguments) {
        assert arguments != null : "Arguments should not be null";
        return " Bye. Hope to see you again soon!";
    }

    /**
     * Returns the full task list as a formatted string for the GUI.
     * Each task is listed on a new line with its 1-indexed position.
     *
     * @param tasks The user's task list.
     * @return A formatted string listing all tasks in the task list.
     */
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

    /**
     * Returns a confirmation message for a task marked as done, for the GUI.
     *
     * @param task The task that was marked as done.
     * @return A string confirming the task has been marked as done.
     */
    public String getMarkedTaskAsString(Task task) {
        return " Nice! I've marked this task as done:\n " + task.toString();
    }

    /**
     * Returns a confirmation message for a task marked as not done, for the GUI.
     *
     * @param task The task that was marked as not done.
     * @return A string confirming the task has been marked as not done.
     */
    public String getUnmarkedTaskAsString(Task task) {
        return " OK, I've marked this task as not done yet:\n " + task.toString();
    }

    /**
     * Returns a message stating the current number of tasks in the task list, for the GUI.
     * Uses singular or plural phrasing depending on the count.
     *
     * @param numOfTasks The total number of tasks in the task list.
     * @return A string stating how many tasks are currently in the list.
     */
    public String getNumOfTasksAsString(int numOfTasks) {
        if (numOfTasks == 1) {
            return " Now you have 1 task in the list.";
        } else {
            return " Now you have " + numOfTasks + " tasks in the list.";
        }
    }

    /**
     * Returns a confirmation message for a newly added task, for the GUI.
     * Includes the task's string representation and the updated task count.
     *
     * @param task       The task that was added.
     * @param numOfTasks The total number of tasks in the list after adding.
     * @return A formatted string confirming the task was added and stating the new task count.
     */
    public String getTaskAddedString(Task task, int numOfTasks) {
        return " Got it. I've added this task:"
                + "\n "
                + task.toString()
                + " \n"
                + getNumOfTasksAsString(numOfTasks);
    }

    /**
     * Returns a confirmation message for a deleted task, for the GUI.
     * Includes the task's string representation and the updated task count.
     *
     * @param task       The task that was deleted.
     * @param numOfTasks The total number of tasks in the list after deletion.
     * @return A formatted string confirming the task was removed and stating the new task count.
     */
    public String getTaskDeletedString(Task task, int numOfTasks) {
        return " Noted. I've removed this task:"
                + "\n   "
                + task.toString()
                + " \n"
                + getNumOfTasksAsString(numOfTasks);
    }

    /**
     * Returns a confirmation message for an updated task, for the GUI.
     * Includes the task's string representation reflecting the applied change.
     *
     * @param task The task that was updated.
     * @return A formatted string confirming the task was updated.
     */
    public String getTaskUpdatedString(Task task) {
        return " Got it. I've updated this task:"
                + "\n "
                + task.toString();
    }

}
