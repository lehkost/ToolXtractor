package eu.dariah.ToolXtractor;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class DHPreAnnotationXsltTest {
    @Test
    public void testPreAnnotateWithXslt() throws IOException, TransformerException, ParserConfigurationException,
            SAXException {
        DHPreAnnotationXslt dhPreAnnotationXslt = new DHPreAnnotationXslt();
        dhPreAnnotationXslt.transform(new File("src/test/resources/data/3.xml"), new File("src/main/resources" +
                "/annotate.xsl"), new File("src/test/resources/data/annotated_xslt_3.xml"));
    }
}
