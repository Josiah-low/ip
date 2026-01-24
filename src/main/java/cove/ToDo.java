package cove;

/**
 * Represents a simple task without any time information.
 * A cove.ToDo task is the most basic task type, which only
 * includes a description and a completion status.
 */
public class ToDo extends Task {

    /**
     * Creates a new cove.ToDo task with the specified description.
     *
     * @param description The description of the task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of a cove.Task for printing to console.
     * The format is: "[T][isDone] description" where isDone is "X" if true,
     * or a space if false.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a string representation of a cove.Task for saving to the data file.
     * The format is: "TX|description" where X is "1" if true, or "0" if false.
     */
    @Override
    public String dataString() {
        return "T" + super.dataString();
    }
}
