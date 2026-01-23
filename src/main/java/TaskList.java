import java.util.ArrayList;

/**
 * Represents a list of tasks.
 * Provides methods for adding, deleting, marking, unmarking tasks from the list.
 * Task indices are 1-indexed but handled as 0-indexed internally.
 */
public class TaskList {

    /** List of tasks */
    private ArrayList<Task> tasks;

    /**
     * Creates a new TaskList with the provided ArrayList<Task>.
     *
     * @param tasks The Task ArrayList to initialise the TaskList with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Creates a new empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }


    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public Task getTask(int taskIndex) {
        return this.tasks.get(taskIndex - 1);
    }

    /**
     * Returns the total number of tasks in the task list.
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Adds the given task into the task list.
     *
     * @param task The task to add to the list.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Removes the specified task from the task list.
     *
     * @param taskIndex The 1-indexed task index specifying which task
     * to delete from the list.
     */
    public Task deleteTask(int taskIndex) {
        return this.tasks.remove(taskIndex - 1);
    }

    /**
     * Marks the specified task as done.
     *
     * @param taskIndex The 1-indexed task index specifying which task
     * to mark as done from the list.
     */
    public Task markTask(int taskIndex) {
        this.tasks.get(taskIndex - 1).setDone(true);
        return this.tasks.get(taskIndex - 1);
    }

    /**
     * Marks the specified task as not done.
     *
     * @param taskIndex The 1-indexed task index specifying which task
     * to mark as not done from the list.
     */
    public Task unmarkTask(int taskIndex) {
        this.tasks.get(taskIndex - 1).setDone(false);
        return this.tasks.get(taskIndex - 1);
    }

}
