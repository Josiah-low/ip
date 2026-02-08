package cove;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {

    @Test
    public void toString_notDoneFormat() {
        ToDo todo = new ToDo("this description is a test");
        todo.setDone(false);
        String string = todo.toString();
        assertEquals("[T][ ] this description is a test", string);
    }

    @Test
    public void toString_doneFormat() {
        ToDo todo = new ToDo("this description is a test");
        todo.setDone(true);
        String string = todo.toString();
        assertEquals("[T][X] this description is a test", string);
    }

    @Test
    public void toString_trimsDescription() {
        ToDo todo = new ToDo("     this description is a test     ");
        todo.setDone(true);
        String string = todo.toString();
        assertEquals("[T][X] this description is a test", string);
    }

    @Test
    public void dataString_notDoneFormat() {
        ToDo todo = new ToDo("this description is a test");
        todo.setDone(false);
        String dataString = todo.dataString();
        assertEquals("T0|this description is a test", dataString);
    }

    @Test
    public void dataString_doneFormat() {
        ToDo todo = new ToDo("this description is a test");
        todo.setDone(true);
        String dataString = todo.dataString();
        assertEquals("T1|this description is a test", dataString);
    }

    @Test
    public void dataString_trimsDescription() {
        ToDo todo = new ToDo("     this description is a test     ");
        todo.setDone(true);
        String dataString = todo.dataString();
        assertEquals("T1|this description is a test", dataString);
    }
}
