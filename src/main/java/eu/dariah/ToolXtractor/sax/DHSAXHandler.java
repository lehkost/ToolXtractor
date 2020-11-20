package eu.dariah.ToolXtractor.sax;

import eu.dariah.ToolXtractor.model.DHAbstract;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * DHSAXHandler
 * @author Yoann
 * @version 1.0
 */
public class DHSAXHandler extends DefaultHandler {
    private String content = "";
    private DHAbstract dhAbstract;

    public DHSAXHandler(String identifier) {
        dhAbstract = new DHAbstract(identifier);
    }

    public void startDocument() {
    }

    public void startElement(String namespaceURI, String localName, String qname, Attributes attributes){
        if(qname.equals("title")) {
            content = "";
        } else if(qname.equals("body")) {
            content = "";
        }
    }

    public void endElement(String namespaceURI, String localName, String qname){
        switch(qname){
            case "title":
                if(!StringUtils.strip(content).equals("") && (dhAbstract.getTitle() == null || dhAbstract.getTitle().equals("")))
                    dhAbstract.setTitle(content);
                content = "";
                break;
            case "body":
                if(!StringUtils.strip(content).equals("") && (dhAbstract.getDescription() == null || dhAbstract.getDescription().equals("")))
                    dhAbstract.setDescription(content);
                content = "";
                break;
        }
    }

    public void characters(char []ch, int start, int length){
        content += new String(ch, start, length);
    }

    public DHAbstract getDhAbstract() {
        return dhAbstract;
    }
}