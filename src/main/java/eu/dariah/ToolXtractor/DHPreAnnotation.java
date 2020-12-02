package eu.dariah.ToolXtractor;

import eu.dariah.ToolXtractor.model.LinkData;
import eu.dariah.ToolXtractor.sax.DHSAXParser;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.Collection;
import java.util.List;

public class DHPreAnnotation {
    private static Logger logger = Logger.getLogger(DHPreAnnotation.class);

    public void preAnnotate(List<LinkData> linkAbstractToolList, boolean ignoreCase) throws IOException, SAXException, ParserConfigurationException {
        for(LinkData linkAbstractTool : linkAbstractToolList) {
            File xmlFile = new File(linkAbstractTool.getDhAbstract().getFilepath());
            if(! new File("./output_annotation/").exists()) {
                new File("./output_annotation/").mkdir();
            }
            File outputXmlFile = new File("./output_annotation/" + xmlFile.getName());
            preAnnotateXmlFile(xmlFile, outputXmlFile, linkAbstractTool.getMentioned(), ignoreCase);
        }
    }

    public void preAnnotateXmlFile(File xmlFile, File xmlOutputFile, Collection<String> tools, boolean ignoreCase) throws ParserConfigurationException, IOException, SAXException {
        DHSAXParser dhsaxParser = new DHSAXParser();
        OutputStream outputStream = new FileOutputStream(xmlOutputFile);
        dhsaxParser.rewriteXml(new FileInputStream(xmlFile),
                new OutputStreamWriter(outputStream), tools, ignoreCase);
        outputStream.close();
    }
}
