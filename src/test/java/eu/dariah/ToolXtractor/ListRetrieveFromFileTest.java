package eu.dariah.ToolXtractor;

import org.apache.commons.io.input.ReaderInputStream;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Set;

import static eu.dariah.ToolXtractor.ListRetrieverFromFile.*;

public class ListRetrieveFromFileTest {

    @Test(expected = NullPointerException.class)
    public void testGetListWordsFromResources_exception() throws IOException {
        Set<String> results = getListWordsFromResources("doesNotExist.txt");
    }
    @Test
    public void testGetListWordsFromResources_ok() throws IOException {
        Set<String> results = getListWordsFromResources("stopwords.txt");
        Assert.assertTrue(results.size() > 0);
    }

    @Test(expected = FileNotFoundException.class)
    public void testGetListWordsFromFile_exception() throws IOException {
        Set<String> results = getListWordsFromFile("doesNotExist.txt");
    }
    @Test
    public void testGetListWordsFromFile_ok() throws IOException {
        Set<String> results = getListWordsFromFile("src/main/resources/stopwords.txt");
        Assert.assertTrue(results.size() > 0);
    }

    @Test
    public void testGetListWords_ok() throws IOException {
        String fakeInput = "Tool1\nTool2";
        StringReader reader = new StringReader(fakeInput);
        InputStream fakeStream = new ReaderInputStream(reader);

        Set<String> results = getListWords(fakeStream);
        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.contains("Tool1"));
        Assert.assertTrue(results.contains("Tool2"));
    }
}
