package main.java.yevgen.dao;

import main.java.yevgen.dao.interfaces.Saver;
import main.java.yevgen.util.Task;

import java.io.File;
import java.util.List;

public abstract class AbstractSaver implements Saver {

    protected static List<Task> taskList;

    public AbstractSaver(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public abstract void save(File file);

    public static Saver getSaver(List<Task> taskList, File file) {

        String line = file.getName();
        int index = line.lastIndexOf('.');
        String fileType = line.substring(index + 1);
        Saver saver = null;
        switch (fileType) {
            case "xml":
                saver = new XMLSaver(taskList);
                break;
            case "xls":
                saver = new ExcelSaver(taskList);
                break;
            case "xlsx":
                saver = new ExcelSaver(taskList);
                break;
            default:
                break;
        }
        return saver;
    }

}

