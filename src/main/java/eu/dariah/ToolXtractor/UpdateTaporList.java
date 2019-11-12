package eu.dariah.ToolXtractor;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * This class is used only and solely to update the list of tools from TAPoR website
 * Just launch this file in a development environment and it will update the tools_tapor.txt file from the TAPoR API
 */
public class UpdateTaporList {
    public static void main(String[] args) throws Exception {
        JSONObject json = new JSONObject(IOUtils.toString(new URL("http://tapor.ca/api/tools/by_analysis"), StandardCharsets.UTF_8));
        FileWriter writer = new FileWriter(new File("src/main/resources/tools_tapor.txt"));

        JSONArray jsonArray = json.getJSONArray("tools");
        Iterator<Object> it = jsonArray.iterator();
        while(it.hasNext()) {
            JSONObject jsonObj = (JSONObject) it.next();
            String name = (String) jsonObj.get("name");
            writer.write(name);
            if(it.hasNext()) {
                writer.write(System.lineSeparator());
            }
        }
        writer.close();
    }
}
