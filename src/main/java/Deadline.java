public class Deadline extends Task {

    private String by;

    public Deadline(String description, String deadline) {
        super(description);
        this.by = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }
}
