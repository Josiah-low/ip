/**
 * Represents a task with a deadline.
 * A deadline task includes a description and a
 * deadline by which the task should be completed.
 */
public class Deadline extends Task {

    /** Date/time by which task should be completed */
    private String by;

    /**
     * Creates a new Deadline task with the specified description and by deadline.
     *
     * @param description The description of the task.
     * @param by The deadline date/time for completing the task.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string representation of a Deadline task for printing to console.
     * The format is: "[D][isDone] description (by: deadline)" where isDone is "X"
     * if true, or a space if false.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }

    /**
     * Returns a string representation of a Deadline task for saving to the data file.
     * The format is: "DX|description|deadline" where X is "1"
     * if true, or "0" if false.
     */
    @Override
    public String dataString() {
        return "D" +  super.dataString() + "|" + this.by;
    }
}
