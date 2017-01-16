package main.java.yevgen.util;

import main.java.yevgen.util.interfaces.TableElement;

public class Task implements TableElement{
    private String name;
    private int processID;
    private long memorySize;
    private String memoryString;

    public Task(String name, int processID, long memorySize) {
        this.name = name;
        this.processID = processID;
        this.memorySize = memorySize;
        this.memoryString = memorySize + " K";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public long getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(long memorySize) {
        this.memorySize = memorySize;
        this.memoryString = memorySize + " K";
    }

    public String getMemoryString() {
        return memoryString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return name.equals(task.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
