package eu.dariah.ToolXtractor;

import eu.dariah.ToolXtractor.sax.DHSAXParser;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * ParseDirectory
 * @author Yoann
 * @version 1.0
 */
public class ParseDirectory {
    private File directory;
    private List<DHAbstract> dhAbstractList;

    public ParseDirectory(File directory) {
        this.directory = directory;
        this.dhAbstractList = new ArrayList<>();
    }

    public void parseDirectory() {
        parseDirectory(directory);
    }

    public void parseDirectory(File dir) {
        DHSAXParser parser = new DHSAXParser();
        for(File file : dir.listFiles()) {
            if(! file.isDirectory()) {
                if(file.getName().toLowerCase().endsWith(".xml"))
                    parse(parser, file);
            } else {
                parseDirectory(file);
            }
        }
    }

    private void parse(DHSAXParser parser, File file) {
        try {
            dhAbstractList.add(parser.parseXml(new FileInputStream(file), file.getName()));
        } catch (Exception e) {
            System.out.println("Error parsing " + file.getAbsolutePath());
        }
    }

    public List<DHAbstract> getDhAbstractList() {
        return dhAbstractList;
    }
}
