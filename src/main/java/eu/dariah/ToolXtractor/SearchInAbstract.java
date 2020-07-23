package eu.dariah.ToolXtractor;

import eu.dariah.ToolXtractor.model.DHAbstract;
import eu.dariah.ToolXtractor.model.LinkData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SearchInAbstract
 * @author Yoann
 * @version 1.0
 */
public class SearchInAbstract {
    public static final String REGEX_FIND_WORD = "(?i).*?\\b%s\\b.*?";
    public static final String REGEX_FIND_WORD_WITH_CASE = ".*?\\b%s\\b.*?";

    private List<LinkData> linkAbstractToolList;
    private List<LinkData> linkToolAbstractList;
    private boolean debug;

    public SearchInAbstract(boolean debug) {
        this.linkAbstractToolList = new ArrayList<>();
        this.linkToolAbstractList = new ArrayList<>();
        this.debug = debug;
    }

    public void search(List<DHAbstract> dhAbstractList, String toolFilename, String stopwordFilename,
                       boolean ignoreCase) throws IOException {
        Set<String> toolnames = ListRetrieverFromFile.getListWordsFromFile(toolFilename);
        Set<String> stopwords = new HashSet<>(0);
        if(stopwordFilename != null) {
            stopwords = ListRetrieverFromFile.getListWordsFromFile(stopwordFilename);
        }
        int size = dhAbstractList.size();
        int i = 0;
        for(DHAbstract dhAbstract : dhAbstractList) {
            if(this.debug)
                System.out.println("Searching in file " + (++i) + " / " + size);
            LinkData linkAbstractTool = new LinkData(dhAbstract.getIdentifier());
            for(String toolname : toolnames) {
                if(! stopwords.contains(toolname)) {
                    if((dhAbstract.getTitle() != null && dhAbstract.getTitle().contains(toolname)) || (dhAbstract.getDescription() != null && dhAbstract.getDescription().contains(toolname))) {
                        String regex = REGEX_FIND_WORD;
                        if(isMoreUpperCaseAsSpaces(toolname) && !ignoreCase) { //We should not ignore case
                            regex = REGEX_FIND_WORD_WITH_CASE;
                        }
                        regex = String.format(regex, Pattern.quote(toolname));
                        Pattern p = Pattern.compile(regex, Pattern.DOTALL); //DOTALL option for multiline

                        Matcher m = null;
                        if(dhAbstract.getTitle() != null) {
                            m = p.matcher(dhAbstract.getTitle());
                        }
                        if (m != null && m.matches()) {
                            linkAbstractTool.getMentioned().add(toolname);
                        } else if(dhAbstract.getDescription() != null) {
                            m = p.matcher(dhAbstract.getDescription());
                            if (m.matches()) {
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

    private boolean isMoreUpperCaseAsSpaces(String input) {
        int upperCase = 0;
        int space = 0;
        for (int k = 0; k < input.length(); k++) {
            if (Character.isUpperCase(input.charAt(k)))
                upperCase++;
            if (Character.isSpaceChar(input.charAt(k)))
                space++;
        }
        return upperCase > space;
    }
}
