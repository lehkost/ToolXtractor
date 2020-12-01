package eu.dariah.ToolXtractor;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class DHPreAnnotationTest {

    @Test
    public void testGetListWordsFromResources_exception() throws IOException, TransformerException, ParserConfigurationException, SAXException {
        DHPreAnnotation dhPreAnnotation = new DHPreAnnotation();
        File xmlInputFile = new File("src/test/resources/data/3.xml");
        File xmlOutputFile = new File("src/test/resources/data/annotated_3.xml");
        dhPreAnnotation.preAnnotateXmlFile(xmlInputFile, xmlOutputFile, Collections.singleton("spacy"), false);
//        xmlOutputFile.delete();
    }

}
