package cove;

/**
 * Provides static helper methods for parsing of user input strings.
 */
public class Parser {

    /**
     * Returns only the command part of the full user input string.
     *
     * @param userInput The full user input by the user into the console.
     * @return A string containing only the command part of the full user input string.
     */
    public static String parseCommand(String userInput) {
        assert userInput != null : "userInput should not be null";
        assert !userInput.isEmpty() : "userInput should not be empty";

        String command = userInput.split(" ", 2)[0];

        assert command != null : "Parsed command should not be null";
        assert !command.isEmpty() : "Parsed command should not be empty";

        return command;
    }

    /**
     * Returns only the arguments part of the full user input string.
     *
     * @param userInput The full user input by the user into the console.
     * @return A string containing only the arguments part of the full user input string.
     */
    public static String parseArguments(String userInput) {
        assert userInput != null : "userInput should not be null";
        assert !userInput.isEmpty() : "userInput should not be empty";

        String[] words = userInput.trim().split(" ", 2);

        assert words != null : "words should not be null";
        assert words.length > 0 : "words should have at least one element";

        String result;
        if (words.length > 1) {
            result = words[1].trim();
            assert result != null : "Arguments should not be null when present";
        } else {
            result = "";
        }

        assert result != null : "Result should not be null";

        return result;

    }

}
