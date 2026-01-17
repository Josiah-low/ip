public class Task {
    private String description;
    private int index;
    private static int numOfTasks = 0;
    private boolean isDone;

    public Task(String name) {
        this.description = name;
        numOfTasks++;
        this.index = numOfTasks;
    }

    public String getDescription() {
        return this.description;
    }

    public int getIndex() {
        return this.index;
    }

    public void printTask() {
        if (this.isDone) {
            System.out.printf(" %d.[X] %s\n", this.index, this.description);
        } else {
            System.out.printf(" %d.[ ] %s\n", this.index, this.description);
        }
    }

    public static int getNumOfTasks() {
        return numOfTasks;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public void printDoneStatus() {
        if (this.isDone) {
            System.out.println("   [X] " + this.description);
        } else {
            System.out.println("   [ ] " + this.description);
        }
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

}
