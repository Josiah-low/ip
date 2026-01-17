public class Deadline extends Task {

    private String by;

    public Deadline(String description, String deadline) {
        super(description);
        this.by = deadline;
    }

    @Override
    public String toString() {
        if (this.getIsDone()) {
            return "[D][X] " + this.getDescription() + " (by: " + this.by + ")";
        } else {
            return "[D][ ] " + this.getDescription() + " (by: " + this.by + ")";
        }
    }
}
