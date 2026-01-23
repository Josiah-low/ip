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

    public Task getTask(int taskIndex) {
        return this.tasks.get(taskIndex - 1);
    }

    public int size() {
        return this.tasks.size();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Task deleteTask(int taskIndex) {
        return this.tasks.remove(taskIndex - 1);
    }

    public Task markTask(int taskIndex) {
        this.tasks.get(taskIndex - 1).setDone(true);
        return this.tasks.get(taskIndex - 1);
    }

    public Task unmarkTask(int taskIndex) {
        this.tasks.get(taskIndex - 1).setDone(false);
        return this.tasks.get(taskIndex - 1);
    }

}
