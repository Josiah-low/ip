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
        assert description != null : "Description should not be null";
        assert !description.isEmpty() : "Description should not be empty";

        this.description = description.trim();

        assert this.description != null : "Description should be initialised";
        assert !this.description.isEmpty() : "Trimmed description should not be empty";
    }

    public String getDescription() {
        assert this.description != null : "Description should not be null";

        return this.description;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public int getIndex() {
        assert this.index > 0 : "Index should be positive";

        return this.index;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void setIndex(int index) {
        assert index > 0 : "Index should be positive";

        this.index = index;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of a Task for printing to console.
     * The format is: "[isDone] description" where isDone is "X" if true,
     * or a space if false.
     */
    @Override
    public String toString() {
        assert this.description != null : "Description should not be null";

        String result;
        if (this.isDone) {
            result = "[X] " + this.description;
            assert result.startsWith("[X]") : "Completed task should start with [X]";
        } else {
            result = "[ ] " + this.description;
            assert result.startsWith("[ ]") : "Incomplete task should start with [ ]";
        }

        assert result != null : "String should not be null";
        assert result.contains(this.description) : "Result should contain task description";

        return result;
    }

    /**
     * Returns a string representation of a Task for saving to the data file.
     * The format is: "X|description" where X is "1" if true, or "0" if false.
     */
    public String dataString() {
        assert this.description != null : "Description should not be null";

        String result;
        if (this.isDone) {
            result = "1|" + this.description;
            assert result.startsWith("1|") : "Completed task data should start with 1|";
        } else {
            result = "0|" + this.description;
            assert result.startsWith("0|") : "Incomplete task data should start with 0|";
        }

        assert result != null : "Data string should not be null";
        assert result.contains("|") : "Data string should contain '|' separator";
        assert result.split("\\|").length == 2 : "Data string should have 2 parts";

        return result;
    }

    public abstract Task update(String updateArguments) throws CoveException;

}
