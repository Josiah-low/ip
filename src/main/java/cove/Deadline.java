package cove;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline.
 * A deadline task includes a description and a
 * deadline date by which the task should be completed.
 */
public class Deadline extends Task {

    public static final int DATA_STRING_PARTS = 3;

    /**
     * Deadline date by which task should be completed
     */
    private LocalDate by;

    /**
     * Creates a new cove.Deadline task with the specified description and deadline date.
     *
     * @param description The description of the task.
     * @param by          The deadline date for completing the task.
     */
    public Deadline(String description, LocalDate by) {
        super(description);

        assert description != null : "Description should not be null";
        assert !description.isEmpty() : "Description should not be empty";
        assert by != null : "Deadline date should not be null";

        this.by = by;
        assert this.by != null : "Deadline date should be initialised";
    }

    public void setBy(String date) throws CoveException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            this.by = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new CoveException("OOPS! Invalid date format! Your dates must be in the format of \"yyyy/mm/dd\".");
        }
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

        String result = "D" + super.dataString() + "|" + this.by.toString();

        assert result != null : "Deadline data string should not be null";
        assert result.startsWith("D") : "Deadline data string should start with 'D'";
        assert result.split("\\|").length == Deadline.DATA_STRING_PARTS : "Deadline data string should have 3 parts";

        return result;
    }

    /**
     * Updates a single field of this Deadline task based on the provided update arguments.
     * Only one of {@code /desc} or {@code /by} must be specified. The fields
     * {@code /from} and {@code /to} are not valid for Deadline tasks.
     *
     * @param updateArguments A string containing the field specifier and new value
     *                        (e.g., {@code "/desc buy groceries"} or {@code "/by 2025/03/15"}).
     * @return This Deadline task after the update has been applied.
     * @throws CoveException if {@code /from} or {@code /to} is specified, if no valid field
     *                       is specified, if more than one field is specified at once,
     *                       if the new description is empty, or if the new deadline date
     *                       is empty or not in {@code yyyy/MM/dd} format.
     */
    @Override
    public Task update(String updateArguments) throws CoveException {
        boolean hasDesc = updateArguments.contains("/desc");
        boolean hasBy = updateArguments.contains("/by");
        boolean hasFrom = updateArguments.contains("/from");
        boolean hasTo = updateArguments.contains("/to");

        if (hasFrom || hasTo) {
            // Invalid: user specifies invalid field(s)
            throw new CoveException("OOPS! Deadline tasks can only update /desc or /by.");
        }

        if (!hasDesc && !hasBy) {
            // Invalid: user does not specify any valid field
            throw new CoveException("OOPS! Deadline tasks can only update /desc or /by.");
        }

        if (hasDesc && hasBy) {
            // Invalid: user specifies more than 1 valid field
            throw new CoveException("OOPS! You can update only 1 field at a time.");
        }

        if (hasDesc) {
            // Valid: user specifies /desc field
            String[] words = updateArguments.split("/desc", 2);
            String updatedDescription = words[1].trim();

            if (updatedDescription.isEmpty()) {
                // Invalid: user does not provide new description
                throw new CoveException("OOPS! You didn't provide a new description.");
            }

            this.setDescription(updatedDescription);
            return this;
        } else {
            // Valid: user specifies /by field
            String[] words = updateArguments.split("/by", 2);
            String updatedBy = words[1].trim();

            if (updatedBy.isEmpty()) {
                // Invalid: user does not provide new deadline
                throw new CoveException("OOPS! You didn't provide a new deadline.");
            }

            this.setBy(updatedBy);
            return this;
        }
    }
}
