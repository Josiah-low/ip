public class Event extends Task {

    private String start;
    private String end;

    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        if (this.getIsDone()) {
            return "[T][X] " + this.getDescription() + "(from: " + start + " to: " + end + ")";
        } else {
            return "[T][ ] " + this.getDescription() + "(from :" + start + " to: " + end + ")";
        }
    }

}
