package cove;

import java.util.ArrayList;

/**
 * Represents a list of tasks.
 * Provides methods for adding, deleting, marking, unmarking tasks from the list.
 * cove.Task indices are 1-indexed but handled as 0-indexed internally.
 */
public class TaskList {

    /** List of tasks */
    private ArrayList<Task> tasks;

    /**
     * Creates a new cove.TaskList with the provided ArrayList<cove.Task>.
     *
     * @param tasks The cove.Task ArrayList to initialise the cove.TaskList with.
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Tasks should not be null";

        this.tasks = tasks;

        assert this.tasks != null : "Tasks should be initialised";
    }

    /**
     * Creates a new empty cove.TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();

        assert this.tasks != null : "Tasks should be initialised";
    }


    public ArrayList<Task> getTasks() {
        assert tasks != null : "Tasks should not be null";

        return this.tasks;
    }

    public Task getTask(int taskIndex) {
        assert taskIndex > 0 : "Task index should be positive";
        assert taskIndex <= this.tasks.size() : "Task index should not exceed task list size";

        Task task = this.tasks.get(taskIndex - 1);

        assert task != null : "Task retrieved should not be null";

        return task;
    }

    /**
     * Returns the total number of tasks in the task list.
     */
    public int size() {
        assert this.tasks != null : "Tasks list should not be null";

        int size = this.tasks.size();

        assert size >= 0 : "Size should be non-negative";

        return size;
    }

    /**
     * Adds the given task into the task list.
     *
     * @param task The task to add to the list.
     */
    public void addTask(Task task) {
        assert task != null : "Task to add should not be null";

        this.tasks.add(task);
    }

    /**
     * Removes the specified task from the task list.
     *
     * @param taskIndex The 1-indexed task index specifying which task
     * to delete from the list.
     */
    public Task deleteTask(int taskIndex) {
        assert taskIndex > 0 : "Task index should be positive";
        assert taskIndex <= this.tasks.size() : "Task index should not exceed task list size";

        return this.tasks.remove(taskIndex - 1);
    }

    /**
     * Marks the specified task as done.
     *
     * @param taskIndex The 1-indexed task index specifying which task
     * to mark as done from the list.
     */
    public Task markTask(int taskIndex) {
        assert taskIndex > 0 : "Task index should be positive";
        assert taskIndex <= this.tasks.size() : "Task index should not exceed task list size";

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
        assert taskIndex > 0 : "Task index should be positive";
        assert taskIndex <= this.tasks.size() : "Task index should not exceed task list size";

        this.tasks.get(taskIndex - 1).setDone(false);
        return this.tasks.get(taskIndex - 1);
    }

    /**
     * Returns a list of tasks whose descriptions contain the given keyword (case-insensitive).
     * Each matching task has its 1-indexed position in the full task list set via
     * {@link Task#setIndex(int)} before being added to the result list.
     *
     * @param keyword The search keyword to match against task descriptions.
     * @return An {@code ArrayList} of tasks whose descriptions contain the keyword;
     *         empty if no matches are found.
     */
    public ArrayList<Task> getTasksWithMatchingKeyword(String keyword) {
        assert keyword != null : "Keyword should not be null";
        assert !keyword.isEmpty() : "Keyword should not be empty";

        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (int i = 1; i <= this.tasks.size(); i++) {
            Task task = getTask(i);
            task.setIndex(i);
            if (taskDescriptionContainsKeyword(task, keyword)) {
                matchingTasks.add(task);
            }
        }

        return matchingTasks;
    }

    /**
     * Updates a single field of the task at the specified index.
     * Delegates to {@link Task#update(String)} on the task at the given index.
     *
     * @param taskIndex      The 1-indexed position of the task to update.
     * @param updateArguments A string containing the field specifier and new value
     *                        (e.g., {@code "/desc new description"}).
     * @return The updated task.
     * @throws CoveException if the update arguments are invalid for the task type.
     */
    public Task updateTask(int taskIndex, String updateArguments) throws CoveException {
        Task task = getTask(taskIndex);
        return task.update(updateArguments);
    }

    /**
     * Returns {@code true} if the given task's description contains the keyword,
     * ignoring case.
     *
     * @param task    The task whose description is to be checked.
     * @param keyword The keyword to search for.
     * @return {@code true} if the task description contains the keyword, {@code false} otherwise.
     */
    private boolean taskDescriptionContainsKeyword(Task task, String keyword) {
        return task.getDescription().toLowerCase().contains(keyword.toLowerCase());
    }

}
