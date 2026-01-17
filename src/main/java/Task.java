public class Task {
    private String description;
    private int index;
    private static int numOfTasks = 0;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        numOfTasks++;
        this.index = numOfTasks;
    }

    public String getDescription() {
        return this.description;
    }

    public int getIndex() {
        return this.index;
    }

    public static int getNumOfTasks() {
        return numOfTasks;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        if (this.isDone) {
            return "[X] " + this.description;
        } else {
            return "[ ] " + this.description;
        }
    }

}
