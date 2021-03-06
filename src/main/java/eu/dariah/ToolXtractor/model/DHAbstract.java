package eu.dariah.ToolXtractor.model;

/**
 * DHAbstract
 * @author Yoann
 * @version 1.0
 */
public class DHAbstract {
    private String identifier;
    private String filepath;
    private String title;
    private String description;

    public DHAbstract(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
