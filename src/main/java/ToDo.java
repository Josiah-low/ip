public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        if (this.getIsDone()) {
            return "[T][X] " + this.getDescription();
        } else {
            return "[T][ ] " + this.getDescription();
        }
    }
}
