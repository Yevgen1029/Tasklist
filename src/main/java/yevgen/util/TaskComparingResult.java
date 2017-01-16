package main.java.yevgen.util;

import main.java.yevgen.util.interfaces.TableElement;

public class TaskComparingResult implements TableElement {
    private String currentMemoryString;
    private String loadedMemoryString;
    private String name;

    public TaskComparingResult(String currentMemoryString, String loadedMemoryString, String name) {
        this.currentMemoryString = currentMemoryString;
        this.loadedMemoryString = loadedMemoryString;
        this.name = name;
    }

    public String getCurrentMemoryString() {
        return currentMemoryString;
    }

    public void setCurrentMemoryString(String currentMemoryString) {
        this.currentMemoryString = currentMemoryString;
    }

    public String getLoadedMemoryString() {
        return loadedMemoryString;
    }

    public void setLoadedMemoryString(String loadedMemoryString) {
        this.loadedMemoryString = loadedMemoryString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
