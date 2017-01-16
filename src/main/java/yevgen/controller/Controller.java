package main.java.yevgen.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.yevgen.dao.AbstractLoader;
import main.java.yevgen.dao.AbstractSaver;
import main.java.yevgen.dao.interfaces.Loader;
import main.java.yevgen.dao.interfaces.Saver;
import main.java.yevgen.util.Task;
import main.java.yevgen.util.TaskList;
import main.java.yevgen.util.CompareLists;
import main.java.yevgen.util.interfaces.TableElement;
import resources.Main;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Controller {

    @FXML
    private TableView<TableElement> tableView;
    @FXML
    private TableColumn<TableElement, String> current;
    @FXML
    private TableColumn<TableElement, String> name;
    @FXML
    private TableColumn<TableElement, String> processID;
    @FXML
    private TableColumn<TableElement, String> memoryString;
    @FXML
    private TableColumn<TableElement, String> load;
    private TaskList taskList = new TaskList();


    public void initialize() {
        createView(false);

        taskList.importTaskListFromSystem();
        tableView.getItems().setAll(taskList.getTaskList());
    }

    public void refreshView() {
        createView(false);

        taskList.clear();
        taskList.importTaskListFromSystem();
        tableView.getItems().setAll(taskList.getTaskList());
    }

    public void createView(Boolean visible) {

        current.setVisible(visible);
        load.setVisible(visible);
        processID.setVisible(!visible);
        memoryString.setVisible(!visible);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        processID.setCellValueFactory(new PropertyValueFactory<>("processID"));
        memoryString.setCellValueFactory(new PropertyValueFactory<>("memoryString"));
        if (visible) {
            current.setCellValueFactory(new PropertyValueFactory<>("currentMemoryString"));
            load.setCellValueFactory(new PropertyValueFactory<>("loadedMemoryString"));
        }
    }

    public void removeDuplicates() {
        createView(false);
        taskList.clear();
        tableView.getItems().setAll(taskList.removeDuplicates());
    }

    public void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser = addFilter(fileChooser, "Excel files (*.xls)", "*.xls");
        fileChooser = addFilter(fileChooser, "Excel files (*.xlsx)", "*.xlsx");
        fileChooser = addFilter(fileChooser, "XML files (*.xml)", "*.xml");

        Stage stage = new Main().getStage();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            taskList.removeDuplicates();
            Saver saver = AbstractSaver.getSaver(taskList.getTaskList(), file);
            saver.save(file);
        }
    }

    public void load() {
        File file = getLoadedFile();
        List<Task> exportedTaskList = getLoadedTaskList(file);
        if (file != null) {
            createView(false);
            tableView.getItems().setAll(exportedTaskList);
        }

    }

    private FileChooser addFilter(FileChooser fileChooser, String description, String... extensions) {
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(description, extensions);
        fileChooser.getExtensionFilters().add(filter);
        return fileChooser;
    }

    public void exitApplication() {
        System.exit(1);
    }


    private File getLoadedFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser = addFilter(fileChooser, "XML files (*.xml)", "*.xml");

        Stage stage = new Main().getStage();
        return fileChooser.showOpenDialog(stage);
    }

    public void compare() throws IOException {
        File file = getLoadedFile();
        List<Task> exportedTaskList = getLoadedTaskList(file);

        CompareLists compareList = new CompareLists(this.taskList.getTaskList(), exportedTaskList);

        if (file != null) {
            createView(true);
            tableView.getItems().setAll(compareList.getResultList());
        }

    }


    private List<Task> getLoadedTaskList(File file) {
        List<Task> exportedTaskList = null;
        if (file != null) {
            Loader loader = AbstractLoader.getLoader(file);
            exportedTaskList = loader.load(file);
        }
        return exportedTaskList;
    }

}
