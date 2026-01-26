package cove;

/**
 * Represents an abstract task in cove.Cove's task system.
 * This is the base class for all task types. All tasks have a description
 * and a completion status. Subclasses must implement specific task types.
 */
public abstract class Task {
    /**
     * Description of the task
     */
    private String description;
    /**
     * Completion status of the task
     */
    private boolean isDone;

    private int index;

    /**
     * Creates a new cove.Task with the specified description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description.trim();
    }

    public String getDescription() {
        return this.description;
    }

    public boolean IsDone() {
        return this.isDone;
    }

    public int getIndex() {
        return this.index;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Returns a string representation of a Task for printing to console.
     * The format is: "[isDone] description" where isDone is "X" if true,
     * or a space if false.
     */
    @Override
    public String toString() {
        if (this.isDone) {
            return "[X] " + this.description;
        } else {
            return "[ ] " + this.description;
        }
    }

    /**
     * Returns a string representation of a Task for saving to the data file.
     * The format is: "X|description" where X is "1" if true, or "0" if false.
     */
    public String dataString() {
        if (this.isDone) {
            return "1|" + this.description;
        } else {
            return "0|" + this.description;
        }
    }

}
