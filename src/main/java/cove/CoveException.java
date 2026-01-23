package cove;

/**
 * Custom exception class for errors in the cove.Cove chatbot.
 * This exception is thrown when user input is invalid, or
 * when other errors occur. The exception message is formatted
 * with a spacing and a separator line for consistent message
 * display in the console.
 */
public class CoveException extends Exception {
    /**
     * Creates a new cove.CoveException with the specified error message.
     * The message is automatically formatted with a space in front and
     * a separator line at the end for consistency with cove.Cove's message style.
     *
     * @param message The error message to display in the console.
     */
    public CoveException(String message) {
      super(" " + message + "\n____________________________________________________________\n");
    }
}
