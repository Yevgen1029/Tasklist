package main.java.yevgen.dao.interfaces;

import main.java.yevgen.util.Task;

import java.io.File;
import java.util.List;

public interface Loader {
    List<Task> load(File file);
}
