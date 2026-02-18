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
