public class Task {
    private String name;
    private int index;
    private static int numOfTasks = 0;
    private boolean isDone;

    public Task(String name) {
        this.name = name;
        numOfTasks++;
        this.index = numOfTasks;
    }

    public String getName() {
        return this.name;
    }

    public int getIndex() {
        return this.index;
    }

    public void printTask() {
        if (this.isDone) {
            System.out.printf(" %d.[X] %s\n", this.index, this.name);
        } else {
            System.out.printf(" %d.[ ] %s\n", this.index, this.name);
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
            System.out.println("   [X] " + this.name);
        } else {
            System.out.println("   [ ] " + this.name);
        }
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

}
