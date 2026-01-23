/**
 * Represents a task with a start and end date/time.
 * An Event task includes a description and a
 * start date/time at which the task begins,
 * and an end date/time at which the task ends.
 */
public class Event extends Task {

    /** Date/time at which the task begins */
    private String start;
    /** Date/time at which the task ends */
    private String end;

    /**
     * Creates a new Event task with the specified description and by deadline.
     *
     * @param description The description of the task.
     * @param start The date/time at which the task begins.
     * @param end The date/time at which the task ends.
     */
    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a string representation of an Event task for printing to console.
     * The format is: "[E][isDone] description (from: start to: end)" where start and end
     * are the start date/time and end date/time respectively, and isDone is "X"
     * if true, or a space if false.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }

    /**
     * Returns a string representation of an Event task for saving to the data file.
     * The format is: "EX|description|start|end" where X is "1" if true,
     * or "0" if false.
     */
    @Override
    public String dataString() {
        return "E" +  super.dataString() + "|" + this.start + "|" + this.end;
    }
}
