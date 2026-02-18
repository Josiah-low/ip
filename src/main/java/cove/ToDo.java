package cove;

/**
 * Represents a simple task without any time information.
 * A cove.ToDo task is the most basic task type, which only
 * includes a description and a completion status.
 */
public class ToDo extends Task {

    public static final int DATA_STRING_PARTS = 2;

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
        assert result.split("\\|").length == ToDo.DATA_STRING_PARTS : "ToDo data string should have 2 parts";

        return result;
    }

    @Override
    public Task update(String updateArguments) throws CoveException {
        boolean hasDesc = updateArguments.contains("/desc");
        boolean hasBy = updateArguments.contains("/by");
        boolean hasFrom = updateArguments.contains("/from");
        boolean hasTo = updateArguments.contains("/to");

        if (!hasDesc) {
            // Invalid: user did not specify /desc field
            throw new CoveException("OOPS! ToDo tasks can only update /desc.");
        }

        if (hasBy || hasFrom || hasTo) {
            // Invalid: user specifies invalid field(s)
            throw new CoveException("OOPS! ToDo tasks can only update /desc.");
        }

        // Valid: user specifies /desc field only
        String[] words = updateArguments.split("/desc", 2);
        String updatedDescription = words[1].trim();

        if (updatedDescription.isEmpty()) {
            // Invalid: user did not provide a new description
            throw new CoveException("OOPS! You didn't provide a new description.");
        }

        this.setDescription(updatedDescription);
        return this;
    }
}
