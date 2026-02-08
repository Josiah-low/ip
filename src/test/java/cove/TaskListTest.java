package cove;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void addTask_singleTask() {
        TaskList tasks = new TaskList();

        Task testTask = new ToDo("test");

        tasks.addTask(testTask);

        assertEquals(1, tasks.size());
    }

    @Test
    public void addTask_multipleTasks() {
        TaskList tasks = new TaskList();

        Task testTask1 = new ToDo("test1");
        Task testTask2 = new ToDo("test2");
        Task testTask3 = new ToDo("test3");

        tasks.addTask(testTask1);
        tasks.addTask(testTask2);
        tasks.addTask(testTask3);

        assertEquals(3, tasks.size());
    }

    @Test
    public void addTask_maintainsOrder() {
        TaskList tasks = new TaskList();

        Task testTask1 = new ToDo("test1");
        Task testTask2 = new ToDo("test2");

        tasks.addTask(testTask1);
        tasks.addTask(testTask2);

        assertEquals(tasks.getTask(1), testTask1);
        assertEquals(tasks.getTask(2), testTask2);
    }

    @Test
    public void deleteTask_singleTask() {
        TaskList tasks = new TaskList();

        Task testTask1 = new ToDo("test1");
        Task testTask2 = new ToDo("test2");
        Task testTask3 = new ToDo("test3");

        tasks.addTask(testTask1);
        tasks.addTask(testTask2);
        tasks.addTask(testTask3);

        Task taskToDelete = tasks.getTask(2);
        Task taskReturned = tasks.deleteTask(2);

        assertEquals(taskToDelete, taskReturned);

        assertEquals(2, tasks.size());
    }

    @Test
    public void deleteTask_multipleTasks() {
        TaskList tasks = new TaskList();

        Task testTask1 = new ToDo("test1");
        Task testTask2 = new ToDo("test2");
        Task testTask3 = new ToDo("test3");

        tasks.addTask(testTask1);
        tasks.addTask(testTask2);
        tasks.addTask(testTask3);

        Task taskToDelete1 = tasks.getTask(1);
        Task taskReturned1 = tasks.deleteTask(1);

        assertEquals(taskToDelete1, taskReturned1);

        Task taskToDelete2 = tasks.getTask(1);
        Task taskReturned2 = tasks.deleteTask(1);

        assertEquals(taskToDelete2, taskReturned2);

        assertEquals(1, tasks.size());
    }

    @Test
    public void deleteTask_maintainsOrder() {
        TaskList tasks = new TaskList();

        Task testTask1 = new ToDo("test1");
        Task testTask2 = new ToDo("test2");
        Task testTask3 = new ToDo("test3");

        tasks.addTask(testTask1);
        tasks.addTask(testTask2);
        tasks.addTask(testTask3);

        tasks.deleteTask(2);
        assertEquals(testTask1, tasks.getTask(1));
        assertEquals(testTask3, tasks.getTask(2));
    }

}
