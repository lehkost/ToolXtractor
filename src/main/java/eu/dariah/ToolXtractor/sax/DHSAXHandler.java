package eu.dariah.ToolXtractor.sax;

import eu.dariah.ToolXtractor.DHAbstract;
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
                dhAbstract.setTitle(content);
                content = "";
                break;
            case "body":
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