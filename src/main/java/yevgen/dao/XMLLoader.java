package main.java.yevgen.dao;

import main.java.yevgen.util.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class XMLLoader extends AbstractLoader {
    private Document loadDocument;

    @Override
    public List<Task> load(File file) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            loadDocument = dBuilder.parse(file);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        loadDocument.getDocumentElement().normalize();

        exportToList();

        return taskList;
    }

    private void exportToList() {
        NodeList nList = loadDocument.getElementsByTagName("task");
        String name;
        int processID;
        long memorySize;
        try {
            for (int i = 0; i < nList.getLength(); i++) {

                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    processID = Integer.parseInt(eElement.getElementsByTagName("PID").item(0).getTextContent());
                    memorySize = Long.parseLong(eElement.getElementsByTagName("memory").item(0).getTextContent());

                    taskList.add(new Task(name, processID, memorySize));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

