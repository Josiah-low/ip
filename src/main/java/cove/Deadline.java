package cove;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 * A deadline task includes a description and a
 * deadline date by which the task should be completed.
 */
public class Deadline extends Task {

    public static final int DATA_STRING_PARTS = 3;

    /** cove.Deadline date by which task should be completed */
    private LocalDate by;

    /**
     * Creates a new cove.Deadline task with the specified description and deadline date.
     *
     * @param description The description of the task.
     * @param by The deadline date for completing the task.
     */
    public Deadline(String description, LocalDate by) {
        super(description);

        assert description != null : "Description should not be null";
        assert !description.isEmpty() : "Description should not be empty";
        assert by != null : "Deadline date should not be null";

        this.by = by;
        assert this.by != null : "Deadline date should be initialised";
    }

    /**
     * Returns a string representation of a cove.Deadline task for printing to console.
     * The format is: "[D][isDone] description (by: MMM dd yyyy)" where isDone is "X"
     * if true, or a space if false.
     */
    @Override
    public String toString() {
        assert this.by != null : "Deadline date should not be null";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String result = "[D]" + super.toString() + " (by: " + this.by.format(formatter) + ")";

        assert result != null : "String should not be null";
        assert result.startsWith("[D]") : "Deadline string should start with '[D]'";
        assert result.contains("by:") : "Deadline string should contain 'by:' indicator";

        return result;
    }

    /**
     * Returns a string representation of a cove.Deadline task for saving to the data file.
     * The format is: "DX|description|yyyy-MM-dd" where X is "1"
     * if true, or "0" if false.
     */
    @Override
    public String dataString() {
        assert this.by != null : "Deadline date should not be null";

        String result = "D" +  super.dataString() + "|" + this.by.toString();

        assert result != null : "Deadline data string should not be null";
        assert result.startsWith("D") : "Deadline data string should start with 'D'";
        assert result.split("\\|").length == Deadline.DATA_STRING_PARTS : "Deadline data string should have 3 parts";

        return result;
    }
}
