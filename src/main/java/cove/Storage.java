package cove;

import java.io.File;
import java.io.FileWriter;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;

import java.util.Scanner;

/**
 * Handles loading and saving tasks to data file.
 */
public class Storage {

    /** File path specifying where to save the tasks information */
    private String filePath;

    /**
     * Creates a new cove.Storage with the specified filePath.
     * Creates the data file if it does not exist.
     *
     * @param filePath The path of the file to store tasks information.
     */
    public Storage(String filePath) {
        assert filePath != null : "File path should not be null";
        assert !filePath.isEmpty() : "File path should not be empty";

        this.filePath = filePath;
        assert this.filePath != null : "File path should be initialised";

        File data = new File(filePath);
        assert data != null : "File object should be created";

        // create data directory if it doesn't exist yet
        File dataDir = data.getParentFile();
        if (dataDir != null && !dataDir.exists()) {
            dataDir.mkdirs();
        }

        try {
            data.createNewFile();
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    /**
     * Returns a single task with the details in the provided data string.
     * Obtains the task type specified by the first character of the data string,
     * obtains the other relevant fields for the task, then creates the task with those fields.
     * Marks it as done if the second character of the data string is '1', then returns the task.
     *
     * @param dataString the string representation of a task
     * @throws CoveException if the task type is unrecognised or the data format is invalid.
     */
    private Task loadTask(String dataString) throws CoveException {
        assert dataString != null : "Data string should not be null";
        assert !dataString.isEmpty() : "Data string should not be empty";

        try {
            switch (dataString.charAt(0)) {
            case 'T': {
                return loadToDoTask(dataString);
            }
            case 'D': {
                return loadDeadlineTask(dataString);
            }
            case 'E': {
                return loadEventTask(dataString);
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
     * Parses a ToDo data string from the save file and returns the corresponding ToDo task.
     * Marks the task as done if the completion flag in the data string is '1'.
     *
     * @param dataString The data string representing a ToDo task (e.g., {@code "T1|buy groceries"}).
     * @return A ToDo task reconstructed from the data string.
     */
    private Task loadToDoTask(String dataString) {
        String description = dataString.split("\\|", ToDo.DATA_STRING_PARTS)[1];
        Task taskToLoad = new ToDo(description);
        if (dataString.charAt(1) == '1') {
            taskToLoad.setDone(true);
        }
        return taskToLoad;
    }

    /**
     * Parses a Deadline data string from the save file and returns the corresponding Deadline task.
     * Marks the task as done if the completion flag in the data string is '1'.
     *
     * @param dataString The data string representing a Deadline task
     *                   (e.g., {@code "D0|submit report|2025-03-15"}).
     * @return A Deadline task reconstructed from the data string.
     */
    private Task loadDeadlineTask(String dataString) {
        String[] words = dataString.split("\\|", Deadline.DATA_STRING_PARTS);
        String description = words[1];
        String by = words[2];

        Task taskToLoad = new Deadline(description, LocalDate.parse(by));
        if (dataString.charAt(1) == '1') {
            taskToLoad.setDone(true);
        }
        return taskToLoad;
    }

    /**
     * Parses an Event data string from the save file and returns the corresponding Event task.
     * Marks the task as done if the completion flag in the data string is '1'.
     *
     * @param dataString The data string representing an Event task
     *                   (e.g., {@code "E1|team meeting|2025-03-10|2025-03-11"}).
     * @return An Event task reconstructed from the data string.
     */
    private Task loadEventTask(String dataString) {
        String[] words = dataString.split("\\|", Event.DATA_STRING_PARTS);
        String description = words[1];
        String start = words[2];
        String end = words[3];

        Task taskToLoad = new Event(description, LocalDate.parse(start), LocalDate.parse(end));
        if (dataString.charAt(1) == '1') {
            taskToLoad.setDone(true);
        }
        return taskToLoad;
    }

    /**
     * Returns an array list of tasks as specified by the data file.
     * Reads line by line from the data file for a task data string,
     * and adds them to the tasks array list before returning it.
     *
     * @return The cove.Task array list containing tasks from the data file
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        assert tasks != null : "Tasks list should be initialised";

        try {
            File data = new File("./data/cove.txt");
            Scanner scanner = new Scanner(data);
            assert scanner != null : "Scanner should be initialised";

            while (scanner.hasNext()) {
                Task loadedTask = loadTask(scanner.next());
                assert loadedTask != null : "Loaded task should not be null";
                tasks.add(loadedTask);
            }
            scanner.close();

        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } catch (CoveException e) {
            System.out.println(e.getMessage());
        }

        assert tasks != null : "Tasks list should not be null";
        return tasks;
    }

    /**
     * Helper method that appends text to a file at the specified path.
     *
     * @param filePath Path to the file to append the text to.
     * @param text Text to append to the file.
     * @throws IOException if an I/O error occurs.
     */
    private static void appendToFile(String filePath, String text) throws IOException {
        assert filePath != null : "File path should not be null";
        assert !filePath.isEmpty() : "File path should not be empty";
        assert text != null : "Text should not be null";

        FileWriter fileWriter = new FileWriter(filePath, true);
        assert fileWriter != null : "FileWriter should be initialised";

        fileWriter.write(text);
        fileWriter.close();
    }

    /**
     * Updates the data file to reflect the current task list.
     * Deletes the current data file, then creates it again to append each task's data
     * from the task list into the data file.
     *
     * @param tasks The task list to save into the data file.
     */
    public void save(TaskList tasks) {
        assert tasks != null : "Task list should not be null";

        try {
            Files.delete(Paths.get("./data/cove.txt"));
            for (Task task : tasks.getTasks()) {
                assert task != null : "Task in list should not be null";
                String dataString = task.dataString();
                assert dataString != null : "Task data string should not be null";
                assert !dataString.isEmpty() : "Task data string should not be empty";

                appendToFile("./data/cove.txt", dataString + "\n");
            }
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

}
