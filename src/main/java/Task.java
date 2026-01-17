public class Task {
    private String name;
    private int index;
    private static int numOfTasks = 0;

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
        System.out.printf(" %d. %s\n", this.index, this.name);
    }

    public static int getNumOfTasks() {
        return numOfTasks;
    }
}
