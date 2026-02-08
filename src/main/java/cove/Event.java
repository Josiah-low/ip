package cove;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a start and end date.
 * An Event task includes a description and a start date on which the task begins,
 * and an end date on which the task ends.
 */
public class Event extends Task {

    /** Date on which the task begins */
    private LocalDate start;
    /** Date on which the task ends */
    private LocalDate end;

    /**
     * Creates a new cove.Event task with the specified description, start and end dates.
     *
     * @param description The description of the task.
     * @param start The date/time at which the task begins.
     * @param end The date/time at which the task ends.
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

        String result = "E" +  super.dataString() + "|" + this.start + "|" + this.end.toString();

        assert result != null : "Event data string should not be null";
        assert result.startsWith("E") : "Event data string should start with 'E'";
        assert result.split("\\|").length == 4 : "Event data string should have 4 parts";

        return result;
    }
}
