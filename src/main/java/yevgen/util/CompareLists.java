package main.java.yevgen.util;

import java.util.ArrayList;
import java.util.List;

public class CompareLists {
    private List<Task> current;
    private List<Task> loaded;
    private List<Task> summTaskList;

    public CompareLists(List<Task> current, List<Task> loaded) {
        this.current = current;
        this.loaded = loaded;
    }

    public List getResultList() {
        List<TaskComparingResult> resultList= new ArrayList<>();
        TaskList tasks = new TaskList();
        tasks.addTasks(current);
        tasks.addTasks(loaded);
        tasks.removeDuplicates();
        summTaskList = tasks.getTaskList();
        for (int i = 0; i < summTaskList.size(); i++) {
            String name = summTaskList.get(i).getName();
            String currentMemory;
            String loadMemory;

            if (current.contains(summTaskList.get(i))) {
                currentMemory = summTaskList.get(i).getMemoryString();
            } else currentMemory = "-";

            if (loaded.contains(summTaskList.get(i))) {
                loadMemory = summTaskList.get(i).getMemoryString();
            } else loadMemory = "-";

            resultList.add( new TaskComparingResult(currentMemory, loadMemory, name));
        }
        return resultList;
    }
}
