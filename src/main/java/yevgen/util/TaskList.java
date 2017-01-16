package main.java.yevgen.util;

import main.java.yevgen.util.comparator.CompareByMemory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskList{

    private List<Task> taskList = new ArrayList<>();

    public void addTask(Task task) {
        taskList.add(task);
    }

    public void addTasks(List taskList) {
        this.taskList.addAll(taskList);
    }


    public void importTaskListFromSystem() {
        String line;
        BufferedReader input;
        String[] splitLine;
        String splitter = "\",\"";
        try {
            Process p = Runtime.getRuntime().exec("TASKLIST /FO \"CSV\" /NH");
            input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                splitLine = line.split(splitter);
                String name = splitLine[0].replace("\"", "");
                int processID = Integer.valueOf(splitLine[1]);
                String memory = splitLine[4].replaceAll("[^0-9]+", "");
                long memorySize = Long.valueOf(memory);
                Task task = new Task(name, processID, memorySize);
                addTask(task);
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
        sortListByName();
    }

    public void clear() {
        taskList.clear();
    }

    public List<Task> getTaskList(){
        return taskList;
    }

    public List<Task> removeDuplicates() {
        importTaskListFromSystem();
        sortListByName();
        for (int i = 0; i < taskList.size() - 1; ) {
            if (taskList.get(i).getName().equals(taskList.get(i + 1).getName())) {
                taskList.get(i).setMemorySize(taskList.get(i).getMemorySize() + taskList.get(i + 1).getMemorySize());
                taskList.remove(i + 1);
            } else i++;
        }
        return taskList;
    }

    public void sortListByName() {
        Comparator<Task> comparator = new CompareByMemory();
        taskList.sort(comparator);
    }
}