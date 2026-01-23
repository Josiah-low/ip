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

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public int size() {
        return this.tasks.size();
    }

    public void deleteTask(Task task) {
        this.tasks.remove(task);
    }

    public void markTask(Task task) {
        task.setDone(true);
    }

    public void unmarkTask(Task task) {
        task.setDone(false);
    }

}
