package eu.dariah.ToolXtractor;

import eu.dariah.ToolXtractor.sax.DHSAXParser;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Collections;

public class DHSAXParserTest {

    @Test
    public void testIgnoreCase() throws IOException, SAXException, ParserConfigurationException {
        DHSAXParser dhsaxParser = new DHSAXParser();
        OutputStream outputStream = new FileOutputStream("src/test/resources/data/annotated_stax_3.xml");
        dhsaxParser.rewriteXml(new FileInputStream("src/test/resources/data/3.xml"),
                new OutputStreamWriter(outputStream), Collections.singleton("Spacy"), true);
        outputStream.close();
    }
}
