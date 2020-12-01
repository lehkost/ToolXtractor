package eu.dariah.ToolXtractor;

import eu.dariah.ToolXtractor.model.DHAbstract;
import eu.dariah.ToolXtractor.model.LinkData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SearchInAbstract
 * @author Yoann
 * @version 1.0
 */
public class SearchInAbstract {
    private List<LinkData> linkAbstractToolList;
    private List<LinkData> linkToolAbstractList;
    private boolean debug;

    public SearchInAbstract(boolean debug) {
        this.linkAbstractToolList = new ArrayList<>();
        this.linkToolAbstractList = new ArrayList<>();
        this.debug = debug;
    }

    public void search(List<DHAbstract> dhAbstractList, String toolFilename, String stopwordFilename,
                       boolean ignoreCase, boolean printTitleInsteadOfIdentifier) throws IOException {
        Set<String> toolnames = ListRetrieverFromFile.getListWordsFromFile(toolFilename);
        Set<String> stopwords = new HashSet<>(0);
        if(stopwordFilename != null) {
            stopwords = ListRetrieverFromFile.getListWordsFromFile(stopwordFilename);
        }
        search(dhAbstractList, toolnames, stopwords, ignoreCase, printTitleInsteadOfIdentifier);
    }

    public void search(List<DHAbstract> dhAbstractList, Set<String> toolnames, Set<String> stopwords,
                       boolean ignoreCase, boolean printTitleInsteadOfIdentifier) {
        int size = dhAbstractList.size();
        int i = 0;
        for(DHAbstract dhAbstract : dhAbstractList) {
            if(this.debug)
                System.out.println("Searching in file " + (++i) + " / " + size);
            LinkData linkAbstractTool;
            if(printTitleInsteadOfIdentifier)
                linkAbstractTool = new LinkData(dhAbstract.getTitle());
            else
                linkAbstractTool = new LinkData(dhAbstract.getIdentifier());
            for(String toolname : toolnames) {
                if(! stopwords.contains(toolname)) {
                    if(dhAbstract.getTitle() != null || dhAbstract.getDescription() != null) {
                        Pattern p = RegexPreparer.regexPreparation(toolname, ignoreCase);
                        List<String> textFoundInTitle = RegexPreparer.findStringInText(p,
                                dhAbstract.getTitle(), false);
                        if(! textFoundInTitle.isEmpty()) {
                            linkAbstractTool.getMentioned().add(toolname);
                        } else {
                            List<String> textFoundInDesc = RegexPreparer.findStringInText(p,
                                    dhAbstract.getDescription(), false);
                            if(! textFoundInDesc.isEmpty()) {
                                linkAbstractTool.getMentioned().add(toolname);
                            }
                        }
                    }
                }
            }
            linkAbstractToolList.add(linkAbstractTool);
        }
    }

    public List<LinkData> getLinkAbstractToolList() {
        return linkAbstractToolList;
    }

    public List<LinkData> getLinkToolAbstractList() {
        createListLinkTookAbstract();
        return linkToolAbstractList;
    }

    private void createListLinkTookAbstract() {
        for(LinkData linkAbstractTool : linkAbstractToolList) {
            for(String toolname : linkAbstractTool.getMentioned()) {
                boolean toolExists = false;
                for(LinkData linkToolAbstract : linkToolAbstractList) {
                    if(linkToolAbstract.getIdentifier().equals(toolname)) {
                        linkToolAbstract.getMentioned().add(linkAbstractTool.getIdentifier());
                        toolExists = true;
                    }
                }
                if(! toolExists) {
                    LinkData linkToolAbstract = new LinkData(toolname);
                    linkToolAbstract.getMentioned().add(linkAbstractTool.getIdentifier());
                    linkToolAbstractList.add(linkToolAbstract);
                }
            }
        }
    }
}
