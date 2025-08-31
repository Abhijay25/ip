package quacker.tasks;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void TestSize(){
        ToDo toDo = new ToDo("hello");
        List<Task> list = new ArrayList<>();
        list.add(toDo);
        TaskList testList = new TaskList(list);
        
        
        assertEquals(1, testList.size());
    }
    
    @Test 
    public void TestGet() {
        ToDo toDo = new ToDo("hello");
        List<Task> list = new ArrayList<>();
        list.add(toDo);
        TaskList testList = new TaskList(list);
        
        assertEquals(toDo, testList.get(0));
    }
}