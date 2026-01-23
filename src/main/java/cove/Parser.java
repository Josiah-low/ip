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
        return userInput.split(" ", 2)[0];
    }

    /**
     * Returns only the arguments part of the full user input string.
     *
     * @param userInput The full user input by the user into the console.
     * @return A string containing only the arguments part of the full user input string.
     */
    public static String parseArguments(String userInput) {
        String[] words = userInput.trim().split(" ", 2);
        if (words.length > 1) {
            return words[1].trim();
        } else {
            return "";
        }
    }

}
