package eu.dariah.ToolXtractor;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ListRetrieverFromFile
 * @author Yoann
 * @version 1.0
 */
public class ListRetrieverFromFile {
    public static Set<String> getListWordsFromResources(String filename) throws IOException {
        InputStream inputStream = ListRetrieverFromFile.class.getResourceAsStream("/" + filename);
        return getListWords(inputStream);
    }

    public static Set<String> getListWordsFromFile(String filename) throws IOException {
        InputStream inputStream = new FileInputStream(filename);
        return getListWords(inputStream);
    }

    private static Set<String> getListWords(InputStream inputStream) throws IOException {
        String stopwords = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining(
                "\n"));
        inputStream.close();
        return new HashSet<String>(Arrays.asList(stopwords.split("\n")));
    }
}
