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
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a string representation of an cove.Event task for printing to console.
     * The format is: "[E][isDone] description (from: start to: end)"
     * where start and end are the start date and end date respectively, in the format
     * MMM dd yyyy, and isDone is "X" if true, or a space if false.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[E]" + super.toString() + " (from: " + this.start.format(formatter) + " to: " + this.end.format(formatter) + ")";
    }

    /**
     * Returns a string representation of an cove.Event task for saving to the data file.
     * The format is: "EX|description|start|end" where X is "1" if true,
     * or "0" if false. start and end are in the format yyyy-MM-dd.
     */
    @Override
    public String dataString() {
        return "E" +  super.dataString() + "|" + this.start + "|" + this.end.toString();
    }
}
