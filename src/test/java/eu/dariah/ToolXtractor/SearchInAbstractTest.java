package eu.dariah.ToolXtractor;

import eu.dariah.ToolXtractor.model.DHAbstract;
import eu.dariah.ToolXtractor.model.LinkData;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class SearchInAbstractTest {

    @Test
    public void searchTest() {
        SearchInAbstract searchInAbstract = new SearchInAbstract(true);
        DHAbstract dhAbstract = new DHAbstract("test1.xml");
        dhAbstract.setTitle("Title");
        dhAbstract.setDescription("A test with Spacy.");
        searchInAbstract.search(Collections.singletonList(dhAbstract), Collections.singleton("spaCy"),
                Collections.emptySet(), true, false);
        List<LinkData> linkDataList = searchInAbstract.getLinkAbstractToolList();
        Assert.assertEquals(linkDataList.size(), 1);
        Assert.assertEquals(linkDataList.get(0).getMentioned().size(), 1);
    }
}
