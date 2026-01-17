public class Cove {
    public static void main(String[] args) {
        printGreeting();

        String exit =
                " Bye. Hope to see you again soon!";

        System.out.println(exit);
        printLongLine();
    }

    public static void printLongLine() {
        System.out.println("____________________________________________________________");
    }

    public static void printGreeting() {
        printLongLine();
        System.out.println(" Hello! I'm Cove");
        System.out.println(" What can I do for you?");
        printLongLine();
    }
}
