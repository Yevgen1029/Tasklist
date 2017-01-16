package main.java.yevgen.util.comparator;

import main.java.yevgen.util.Task;

import java.util.Comparator;

public class CompareByMemory implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {
        int result = -1;
        long t1 = task1.getMemorySize();
        long t2 = task2.getMemorySize();
        if (t1 == t2) result = 0;
        if (t1 > t2) result = 1;

        return result;
    }

}
