package main.java.yevgen.dao;

import main.java.yevgen.dao.interfaces.Loader;
import main.java.yevgen.util.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLoader implements Loader {

    protected List<Task> taskList = new ArrayList<>();

    @Override
    public abstract List<Task> load(File file);

    public static Loader getLoader(File file) {

        String line = file.getName();
        int index = line.lastIndexOf('.');
        String fileType = line.substring(index + 1);
        Loader loader = null;
        switch (fileType) {
            case "xml":
                loader = new XMLLoader();
                break;
            default:
                break;
        }
        return loader;
    }

}
