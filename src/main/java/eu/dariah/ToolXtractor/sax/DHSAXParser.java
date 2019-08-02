package eu.dariah.ToolXtractor.sax;

import eu.dariah.ToolXtractor.DHAbstract;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * DHSAXParser
 * @author Yoann
 * @version 1.0
 */
public class DHSAXParser {

    public DHAbstract parseXml(InputStream inputStream, String identifier) throws ParserConfigurationException, SAXException,
            IOException {
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
//        parserFactor.setNamespaceAware(true);
        SAXParser parser = parserFactor.newSAXParser();
        DHSAXHandler handler = new DHSAXHandler(identifier);
        XMLReader xmlReader = parser.getXMLReader();
        xmlReader.setContentHandler(handler);

        InputSource source = new InputSource(inputStream);
        xmlReader.parse(source);
        return handler.getDhAbstract();
    }
}
