package cove;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a start and end date.
 * An Event task includes a description and a start date on which the task begins,
 * and an end date on which the task ends.
 */
public class Event extends Task {

    public static final int DATA_STRING_PARTS = 4;

    /**
     * Date on which the task begins
     */
    private LocalDate start;
    /**
     * Date on which the task ends
     */
    private LocalDate end;

    /**
     * Creates a new cove.Event task with the specified description, start and end dates.
     *
     * @param description The description of the task.
     * @param start       The date/time at which the task begins.
     * @param end         The date/time at which the task ends.
     */
    public Event(String description, LocalDate start, LocalDate end) {
        super(description);

        assert description != null : "Description should not be null";
        assert !description.isEmpty() : "Description should not be empty";
        assert start != null : "Start date should not be null";
        assert end != null : "End date should not be null";

        this.start = start;
        this.end = end;
        assert this.start != null : "Start date should be initialised";
        assert this.end != null : "End date should be initialised";
    }

    public void setStart(String date) throws CoveException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            this.start = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new CoveException("OOPS! Invalid date format! Your dates must be in the format of \"yyyy/mm/dd\".");
        }
    }

    public void setEnd(String date) throws CoveException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            this.end = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new CoveException("OOPS! Invalid date format! Your dates must be in the format of \"yyyy/mm/dd\".");
        }
    }

    /**
     * Returns a string representation of an cove.Event task for printing to console.
     * The format is: "[E][isDone] description (from: start to: end)"
     * where start and end are the start date and end date respectively, in the format
     * MMM dd yyyy, and isDone is "X" if true, or a space if false.
     */
    @Override
    public String toString() {
        assert this.start != null : "Start date should not be null";
        assert this.end != null : "End date should not be null";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String result = "[E]" + super.toString() + " (from: " + this.start.format(formatter)
                + " to: " + this.end.format(formatter) + ")";

        assert result != null : "String should not be null";
        assert result.startsWith("[E]") : "Event string should start with '[E]'";
        assert result.contains("from:") : "Event string should contain 'from:' indicator";
        assert result.contains("to:") : "Event string should contain 'to:' indicator";

        return result;
    }

    /**
     * Returns a string representation of an cove.Event task for saving to the data file.
     * The format is: "EX|description|start|end" where X is "1" if true,
     * or "0" if false. start and end are in the format yyyy-MM-dd.
     */
    @Override
    public String dataString() {
        assert this.start != null : "Start date should not be null";
        assert this.end != null : "End date should not be null";

        String result = "E" + super.dataString() + "|" + this.start + "|" + this.end.toString();

        assert result != null : "Event data string should not be null";
        assert result.startsWith("E") : "Event data string should start with 'E'";
        assert result.split("\\|").length == Event.DATA_STRING_PARTS : "Event data string should have 4 parts";

        return result;
    }

    @Override
    public Task update(String updateArguments) throws CoveException {
        boolean hasDesc = updateArguments.contains("/desc");
        boolean hasBy = updateArguments.contains("/by");
        boolean hasFrom = updateArguments.contains("/from");
        boolean hasTo = updateArguments.contains("/to");

        if (hasBy) {
            // Invalid: user specifies invalid field
            throw new CoveException("OOPS! Event tasks can only update /desc, /from, or /to.");
        }

        if (!hasDesc && !hasFrom && !hasTo) {
            // Invalid: user does not specify any valid field
            throw new CoveException("OOPS! Event tasks can only update /desc, /from, or /to.");
        }

        int fieldCount = (hasDesc ? 1 : 0) + (hasFrom ? 1 : 0) + (hasTo ? 1 : 0);
        if (fieldCount > 1) {
            // Invalid: user specifies more than 1 field
            throw new CoveException("OOPS! You can update only 1 field at a time.");
        }

        if (hasDesc) {
            // Valid: user specifies /desc field
            String[] words = updateArguments.split("/desc", 2);
            String updatedDescription = words[1].trim();

            if (updatedDescription.isEmpty()) {
                // Invalid: user does not provide a new description
                throw new CoveException("OOPS! You didn't provide a new description.");
            }

            this.setDescription(updatedDescription);
            return this;
        } else if (hasFrom) {
            // Valid: user specifies /from field
            String[] words = updateArguments.split("/from", 2);
            String updatedFrom = words[1].trim();

            if (updatedFrom.isEmpty()) {
                // Invalid: user does not provide a new start date
                throw new CoveException("OOPS! You didn't provide a new start date.");
            }

            this.setStart(updatedFrom);
            return this;
        } else {
            // Valid: user specifies /to field
            String[] words = updateArguments.split("/to", 2);
            String updatedTo = words[1].trim();

            if (updatedTo.isEmpty()) {
                // Invalid: user does not provide a new end date
                throw new CoveException("OOPS! You didn't provide a new end date.");
            }

            this.setEnd(updatedTo);
            return this;
        }
    }
}
