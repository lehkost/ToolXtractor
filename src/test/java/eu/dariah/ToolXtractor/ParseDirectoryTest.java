package eu.dariah.ToolXtractor;

import eu.dariah.ToolXtractor.model.DHAbstract;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class ParseDirectoryTest {
    private ParseDirectory parseDirectory;

    @Before
    public void before() {
        parseDirectory = new ParseDirectory(new File("src/test/resources/data/"));
    }

    @Test
    public void testParseDirectory() {
        parseDirectory.parseDirectory();
    }

    @Test
    public void testGetDhAbstractList() {
        parseDirectory.parseDirectory();
        List<DHAbstract> dhAbstracts = parseDirectory.getDhAbstractList();
        dhAbstracts.forEach(s -> System.out.println(s.getFilepath()));
        Assert.assertEquals(3, dhAbstracts.size());
    }
}
