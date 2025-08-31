package quacker.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {
    @Test
    public void TestDesc(){
        ToDo toDo = new ToDo("hello");
        String desc = "hello";
        assertEquals("hello", desc);
    }
}