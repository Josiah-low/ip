public class CoveException extends Exception {
    public CoveException(String message) {
      super(" " + message + "\n____________________________________________________________\n");
    }
}
