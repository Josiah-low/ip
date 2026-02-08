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

        assert description != null : "Description should not be null";
        assert !description.isEmpty() : "Description should not be empty";
    }

    /**
     * Returns a string representation of a cove.Task for printing to console.
     * The format is: "[T][isDone] description" where isDone is "X" if true,
     * or a space if false.
     */
    @Override
    public String toString() {
        String result = "[T]" + super.toString();

        assert result.startsWith("[T]") : "ToDo string should start with '[T]'";

        return result;
    }

    /**
     * Returns a string representation of a cove.Task for saving to the data file.
     * The format is: "TX|description" where X is "1" if true, or "0" if false.
     */
    @Override
    public String dataString() {
        String result = "T" + super.dataString();

        assert result != null : "ToDo data string should not be null";
        assert result.startsWith("T") : "ToDo data string should start with 'T'";
        assert result.split("\\|").length == 2 : "ToDo data string should have 2 parts";

        return result;
    }
}
