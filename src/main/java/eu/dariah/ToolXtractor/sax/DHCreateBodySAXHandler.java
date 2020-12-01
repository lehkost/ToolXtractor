package eu.dariah.ToolXtractor.sax;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.regex.MatchResult;

import static eu.dariah.ToolXtractor.util.ReversedList.reversed;

public class DHCreateBodySAXHandler implements ContentHandler {
    private static Logger logger = Logger.getLogger(DHCreateBodySAXHandler.class);

    private boolean namespaceBegin = false;
    private String currentNamespace;
    private String currentNamespaceUri;
    private Locator locator;
    private Iterable<MatchResult> findTextInTitle;
    private final PrintWriter out;
    private final StringBuilder buffer = new StringBuilder();

    public DHCreateBodySAXHandler(PrintWriter out, Iterable<MatchResult> findTextInTitle) {
        this.out = out;
        this.findTextInTitle = findTextInTitle;
    }

    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
    }

    public void startDocument() {
    }

    public void endDocument() {
    }

    public void startPrefixMapping(String prefix, String uri) {
        namespaceBegin = true;
        currentNamespace = prefix;
        currentNamespaceUri = uri;
    }

    public void endPrefixMapping(String prefix) {
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {

        // Flush buffer - needed in case of mixed content (text + elements)
        String bufStr = buffer.toString();
        logger.info("1+" + bufStr);
        for(MatchResult textFound : reversed(findTextInTitle)) {
//            textFound.start()
            bufStr = bufStr.replace(textFound.group(), "<rs type=\"software\">" + textFound.group() + "</rs>");
        }
        logger.info("2+" + bufStr);
        out.print(bufStr);
        // Prepare to collect element text content
        this.buffer.setLength(0);

        out.print("<" + qName);
        if (namespaceBegin) {
            out.print(" xmlns:" + currentNamespace + "=\"" + currentNamespaceUri + "\"");
            namespaceBegin = false;
        }
        for (int i = 0; i < atts.getLength(); i++) {
            out.print(" " + atts.getQName(i) + "=\"" + atts.getValue(i) + "\"");
        }
        out.print(">");
    }

    public void endElement(String namespaceURI, String localName, String qName) {
        // Process text content
        String bufStr = buffer.toString();
        for(MatchResult textFound : reversed(findTextInTitle)) {
            bufStr = bufStr.replace(textFound.group(), "<rs type=\"software\">" + textFound.group() + "</rs>");
        }
        out.print(bufStr);
        out.print("</" + qName + ">");
        // Reset buffer
        buffer.setLength(0);
    }

    public void characters(char[] ch, int start, int length) {
        // Store chunk of text - parser is allowed to provide text content in chunks for performance reasons
        buffer.append(Arrays.copyOfRange(ch, start, start + length));
    }

    public void ignorableWhitespace(char[] ch, int start, int length) {
        for (int i = start; i < start + length; i++)
            out.print(ch[i]);
    }

    public void processingInstruction(String target, String data) {
        out.print("<?" + target + " " + data + "?>");
    }

    public void skippedEntity(String name) {
        out.print("&" + name + ";");
    }
}
