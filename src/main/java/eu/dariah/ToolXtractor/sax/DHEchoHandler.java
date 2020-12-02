package eu.dariah.ToolXtractor.sax;

import java.io.*;
import java.util.Collection;
import java.util.regex.Pattern;

import eu.dariah.ToolXtractor.RegexPreparer;
import org.apache.log4j.Logger;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class DHEchoHandler extends DefaultHandler {
    private static Logger logger = Logger.getLogger(DHEchoHandler.class);
    private StringBuffer textBuffer;
    private OutputStreamWriter outputStreamWriter;
    private Collection<String> toolnames;
    private boolean ignoreCase;

    public DHEchoHandler(OutputStreamWriter outputStreamWriter, Collection<String> toolnames, boolean ignoreCase) {
        this.outputStreamWriter = outputStreamWriter;
        this.toolnames = toolnames;
        this.ignoreCase = ignoreCase;
    }

    public void startDocument() throws SAXException {
        emit("<?xml version='1.0' encoding='UTF-8'?>");
        nl();
    }

    public void endDocument() throws SAXException {
        try {
            nl();
            outputStreamWriter.flush();
        } catch (IOException e) {
            throw new SAXException("I/O error", e);
        }
    }

    public void startElement(String namespaceURI, String sName, String qName, Attributes attrs) throws SAXException {
        echoText();
        String eName = sName;
        if ("".equals(eName))
            eName = qName;
        emit("<" + eName);
        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                String aName = attrs.getLocalName(i); // Attr name
                if ("".equals(aName))
                    aName = attrs.getQName(i);
                emit(" ");
                emit(aName + "=\"" + attrs.getValue(i) + "\"");
            }
        }
        emit(">");
    }

    public void endElement(String namespaceURI, String sName, String qName) throws SAXException {
        echoText();
        String eName = sName;
        if ("".equals(eName))
            eName = qName;
        emit("</" + eName + ">");
    }

    public void characters(char buf[], int offset, int len) throws SAXException {
        String s = new String(buf, offset, len);
        if (textBuffer == null) {
            textBuffer = new StringBuffer(s);
        } else {
            textBuffer.append(s);
        }
    }

    private void echoText() throws SAXException {
        if (textBuffer == null)
            return;
        String s = "" + textBuffer;
        for(String toolname : toolnames) {
            logger.info(toolname);
            Pattern pattern = RegexPreparer.regexPreparation(toolname, ignoreCase);
            s = RegexPreparer.replaceStringInText(pattern, s);
        }
        emit(s);
        textBuffer = null;
    }

    private void emit(String s) throws SAXException {
        try {
            outputStreamWriter.write(s);
            outputStreamWriter.flush();
        } catch (IOException e) {
            throw new SAXException("I/O error", e);
        }
    }

    private void nl() throws SAXException {
        String lineEnd = System.getProperty("line.separator");
        try {
            outputStreamWriter.write(lineEnd);
        } catch (IOException e) {
            throw new SAXException("I/O error", e);
        }
    }
}
