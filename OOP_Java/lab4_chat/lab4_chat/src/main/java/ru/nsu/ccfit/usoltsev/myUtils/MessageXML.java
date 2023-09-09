package ru.nsu.ccfit.usoltsev.myUtils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import lombok.extern.log4j.Log4j2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Log4j2
public class MessageXML {
       public static String createXml(Message<?> message) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.newDocument();

            Element dataType = document.createElement("dataType");
            dataType.setTextContent(message.getContent().getClass().getSimpleName());
            document.appendChild(dataType);

            Element rootElement = document.createElement("command");
            rootElement.setAttribute("name", message.getTypeMessage());
            dataType.appendChild(rootElement);

            if (message.getContent().getClass().getSimpleName().equals("String")) {
                Element messageElement = document.createElement("message");
                messageElement.setTextContent((String) message.getContent());
                rootElement.appendChild(messageElement);
            } else if (message.getContent().getClass().getSimpleName().equals("LinkedList")) {
                log.info(message.getContent().getClass().getSimpleName());
                creteList((Message<List<Message<String>>>) message, document, rootElement);
            }

            return documentToString(document);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void creteList(Message<List<Message<String>>> message, Document document, Element rootElement) {
        for (Message<String> val : message.getContent()) {
            Element messageElement = document.createElement("message");
            messageElement.setTextContent(val.getContent());
            rootElement.appendChild(messageElement);
        }
    }

    private static String documentToString(Document document) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            String xml = writer.toString();
            System.out.println(xml);
            return xml;
        } catch (TransformerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Message<?> xmlToMessage(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            InputSource is = new InputSource(new StringReader(xml));
            Document document = builder.parse(is);

            Element rootElement = document.getDocumentElement();
            String dataType = rootElement.getTextContent().trim();

            List<String> list = new LinkedList<>();
            String commandName = null;

            NodeList commandList = rootElement.getElementsByTagName("command");
            if (commandList.getLength() > 0) {
                Element commandElement = (Element) commandList.item(0);

                commandName = commandElement.getAttribute("name");

                NodeList messageList = commandElement.getElementsByTagName("message");
                for (int i = 0; i < messageList.getLength(); i++) {
                    Element messageElement = (Element) messageList.item(i);
                    String message = messageElement.getTextContent();
                    list.add(message);
                }
            }
            if (dataType.equals("String")) {
                return new Message<>(commandName, list.get(0));
            } else {
                return new Message<>(commandName, list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getTextContent(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return "";
    }
}

