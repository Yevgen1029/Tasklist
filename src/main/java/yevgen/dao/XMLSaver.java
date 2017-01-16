package main.java.yevgen.dao;

import main.java.yevgen.util.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class XMLSaver extends AbstractSaver {

    private Document saveDocument;

    public XMLSaver(List<Task> taskList) {
        super(taskList);
    }


    @Override
    public void save(File file) {
        createDocument();
        Source source = new DOMSource(saveDocument);
        Result result = new StreamResult(file);

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    private void createDocument() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            saveDocument = documentBuilder.newDocument();
            Element generalElement = saveDocument.createElement("tasks");
            saveDocument.appendChild(generalElement);

            for (Task task: taskList) {
                Element taskElement = saveDocument.createElement("task");
                generalElement.appendChild(taskElement);

                Element name = saveDocument.createElement("name");
                name.appendChild(saveDocument.createTextNode(task.getName()));
                taskElement.appendChild(name);

                Element processID = saveDocument.createElement("PID");
                processID.appendChild(saveDocument.createTextNode("" + task.getProcessID()));
                taskElement.appendChild(processID);

                Element memory = saveDocument.createElement("memory");
                memory.appendChild((saveDocument.createTextNode("" + task.getMemorySize())));
                taskElement.appendChild(memory);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
