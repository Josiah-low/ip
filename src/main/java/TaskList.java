import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public int size() {
        return this.tasks.size();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void deleteTask(int taskIndex) {
        this.tasks.remove(taskIndex - 1);
    }

    public void markTask(int taskIndex) {
        this.tasks.get(taskIndex - 1).setDone(true);
    }

    public void unmarkTask(int taskIndex) {
        this.tasks.get(taskIndex - 1).setDone(false);
    }

}
