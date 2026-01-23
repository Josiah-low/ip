package cove;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 * A deadline task includes a description and a
 * deadline date by which the task should be completed.
 */
public class Deadline extends Task {

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
        this.by = by;
    }

    /**
     * Returns a string representation of a cove.Deadline task for printing to console.
     * The format is: "[D][isDone] description (by: MMM dd yyyy)" where isDone is "X"
     * if true, or a space if false.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[D]" + super.toString() + " (by: " + this.by.format(formatter) + ")";
    }

    /**
     * Returns a string representation of a cove.Deadline task for saving to the data file.
     * The format is: "DX|description|yyyy-MM-dd" where X is "1"
     * if true, or "0" if false.
     */
    @Override
    public String dataString() {
        return "D" +  super.dataString() + "|" + this.by.toString();
    }
}
