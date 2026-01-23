import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads a single task from a data string from the data file and adds it to the task list.
     * Obtains the task type specified by the first character of the data string,
     * obtains the other relevant fields for the task, then creates the task with those fields.
     * Marks it as done if the second character of the data string is '1', then adds the task
     * to the task list.
     *
     * @param dataString the string representation of a task
     * @throws CoveException if the task type is unrecognised or the data format is invalid.
     */
    private Task loadTask(String dataString) throws CoveException {
        try {
            switch (dataString.charAt(0)) {
            case 'T': {
                String description = dataString.split("\\|", 2)[1];
                Task taskToLoad = new ToDo(description);
                if (dataString.charAt(1) == '1') {
                    taskToLoad.setDone(true);
                }
                return taskToLoad;
            }
            case 'D': {
                String[] words = dataString.split("\\|", 3);
                String description = words[1];
                String by = words[2];

                Task taskToLoad = new Deadline(description, LocalDate.parse(by));
                if (dataString.charAt(1) == '1') {
                    taskToLoad.setDone(true);
                }
                return taskToLoad;
            }
            case 'E': {
                String[] words = dataString.split("\\|", 4);
                String description = words[1];
                String start = words[2];
                String end = words[3];

                Task taskToLoad = new Event(description, LocalDate.parse(start), LocalDate.parse(end));
                if (dataString.charAt(1) == '1') {
                    taskToLoad.setDone(true);
                }
                return taskToLoad;
            }
            default: {
                throw new CoveException("Error: Unrecognised task type in save file.");
            }
            }
        } catch (DateTimeParseException e) {
            throw new CoveException("Error: Corrupted date in save file.");
        }
    }

    /**
     * Loads all tasks from the data file into the task list.
     * Reads line by line from the data file for a task data string,
     * and loads each task into the task list.
     */
    private TaskList load() {
        TaskList tasks = new TaskList();

        try {
            File data = new File("./data/cove.txt");
            Scanner scanner = new Scanner(data);

            while (scanner.hasNext()) {
                tasks.addTask(loadTask(scanner.next()));
            }
            scanner.close();

        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } catch (CoveException e) {
            System.out.println(e.getMessage());
        }

        return tasks;
    }

    /**
     * Appends text to a file at the specified path.
     *
     * @param filePath Path to the file to append the text to.
     * @param text Text to append to the file.
     * @throws IOException if an I/O error occurs.
     */
    private static void appendToFile(String filePath, String text) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, true);
        fileWriter.write(text);
        fileWriter.close();
    }

    /**
     * Updates the data file to reflect the current task list.
     * Deletes the current data file, then creates it again to append each task's data
     * from the task list into the data file.
     */
    private static void save(TaskList tasks) {
        try {
            Files.delete(Paths.get("./data/cove.txt"));
            for (Task task : tasks.getTasks()) {
                appendToFile("./data/cove.txt", task.dataString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

}
