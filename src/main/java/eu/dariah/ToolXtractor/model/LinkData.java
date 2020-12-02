package eu.dariah.ToolXtractor.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * LinkData
 * @author Yoann
 * @version 1.0
 */
public class LinkData implements Comparator<LinkData>, Comparable<LinkData> {
    private String identifier;
    private DHAbstract dhAbstract;
    private List<String> mentioned;

    public LinkData(String identifier, DHAbstract dhAbstract) {
        this.identifier = identifier;
        this.dhAbstract = dhAbstract;
        this.mentioned = new ArrayList<>();
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<String> getMentioned() {
        return mentioned;
    }

    public void setMentioned(List<String> mentioned) {
        this.mentioned = mentioned;
    }

    public DHAbstract getDhAbstract() {
        return dhAbstract;
    }

    public void setDhAbstract(DHAbstract dhAbstract) {
        this.dhAbstract = dhAbstract;
    }

    @Override
    public int compareTo(LinkData linkData) {
        return (this.identifier).compareTo(linkData.identifier);
    }

    @Override
    public int compare(LinkData linkData1, LinkData linkData2) {
        return linkData1.mentioned.size() - linkData2.mentioned.size();
    }
}
