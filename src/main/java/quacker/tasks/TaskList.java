package quacker.tasks;

import java.util.List;
import java.util.ArrayList;

public class TaskList {
    private List<Task> list;
    
    public TaskList(List<Task> list) {
        this.list = list;
    }
    
    public void add(Task task) {
        this.list.add(task);
    }
    
    public void remove(int index) {
        this.list.remove(index);
    }
    
    public Task get(int index) {
        return this.list.get(index);
    }
    
    public int size() {
        return this.list.size();
    }
    
    public List<Task> list() {
        return this.list;
    }
}
