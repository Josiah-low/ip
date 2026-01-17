public class Deadline extends Task {

    private String deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        if (this.getIsDone()) {
            return "[D][X] " + this.getDescription() + " (by: " + this.deadline + ")";
        } else {
            return "[D][ ] " + this.getDescription() + " (by: " + this.deadline + ")";
        }
    }
}
