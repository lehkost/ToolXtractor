package eu.dariah.ToolXtractor;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileReader;

public class DHPreAnnotationXslt {
    private static Logger logger = Logger.getLogger(DHPreAnnotation.class);

    public void transform(File xmlFile, File xslFile, File annotatedXmlFile) {
        System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            TransformerFactory tf = TransformerFactory.newInstance();
            logger.info("TransformerFactory: " + tf);
            FileReader stylesheetFileReader = new FileReader(xslFile);
            StreamSource stylesheetStreamSource = new StreamSource(stylesheetFileReader);
            Transformer t = tf.newTransformer(stylesheetStreamSource);
            Source source = new DOMSource(doc);
            Result result = new StreamResult(annotatedXmlFile);
            t.transform(source, result);
        } catch (Exception e) {
            logger.error("Exception!", e);
        }
    }
}
