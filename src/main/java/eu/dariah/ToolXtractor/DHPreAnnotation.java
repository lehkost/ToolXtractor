package eu.dariah.ToolXtractor;

import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class DHPreAnnotation {
    private static Logger logger = Logger.getLogger(DHPreAnnotation.class);

    public void preAnnotateXmlFile(File xmlFile, File xmlOutputFile, Collection<String> tools, boolean ignoreCase) throws ParserConfigurationException,
            TransformerException, IOException, SAXException {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);

        Node title = document.getElementsByTagName("title").item(0);
        Node body = document.getElementsByTagName("body").item(0);

        for(String tool : tools) {
            Node newTitle = replaceWithSAX(title, tool, ignoreCase);
            Node newBody = replaceWithSAX(body, tool, ignoreCase);
            replaceNode(newTitle, title);
            replaceNode(newBody, body);
        }

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(xmlOutputFile);
        transformer.transform(source, result);
    }

    private void replaceNode(Node newNode, Node oldNode) {
        Node parent = oldNode.getParentNode();
        parent.replaceChild(newNode, oldNode);
    }

    private Node replaceWithSAX(Node node, String tool, boolean ignoreCase) {
        String nodeTxt = node.getTextContent();
        Pattern p = RegexPreparer.regexPreparation(tool, ignoreCase);
        String replacedTextInNode = RegexPreparer.replaceStringInText(p, nodeTxt);
        node.setTextContent(replacedTextInNode);
        logger.info(node.getTextContent());
        return node;
//        logger.info(replacedTextInNode);
//
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        try {
//            //Create DocumentBuilder with default configuration
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            //Parse the content to Document object
//            Document doc = builder.parse(new InputSource(new StringReader(replacedTextInNode)));
//            logger.info(doc.getFirstChild());
//        }
//        catch (Exception e) {
//            logger.error("Error", e);
//        }

//        try {
//            StringWriter writer = new StringWriter();
//            Transformer transformer = TransformerFactory.newInstance().newTransformer();
//            transformer.transform(new DOMSource(node), new StreamResult(writer));
//            String xmlStr = writer.toString();
//
//            SAXParserFactory spf = SAXParserFactory.newInstance();
//            SAXParser parser = spf.newSAXParser();
//            XMLReader reader = parser.getXMLReader();
////            reader.setErrorHandler(new MyErrorHandler());
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            PrintWriter out = new PrintWriter(baos);
//            DHCreateBodySAXHandler duper = new DHCreateBodySAXHandler(out, findTextInTitle);
//            reader.setContentHandler(duper);
//            InputSource is = new InputSource(new StringReader(xmlStr));
//            reader.parse(is);
//            out.close();
//            System.out.println(baos);
//        } catch (Exception e) {
//            logger.error("Error", e);
////            System.out.println("ERROR");
////            System.out.println(e.getMessage());
//        }
    }
}
