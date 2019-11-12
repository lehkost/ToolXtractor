package eu.dariah.ToolXtractor;

import eu.dariah.ToolXtractor.model.DHAbstract;
import eu.dariah.ToolXtractor.model.LinkData;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static java.lang.System.exit;

/**
 * ToolXtractor
 * @author Yoann
 * @version 1.0
 */
public class ToolXtractor {
    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("dir", true, "Provide path to directory to parse");
        options.addOption("byAbstract", false, "Results will be a list of abstracts with each tool used");
        options.addOption("byTool", false, "Results will be a list of tools with each abstract they are " +
                "mentioned in");
        options.addOption("inputTools", true, "A txt file with list of the tools (1 tool per line)");
        options.addOption("ignoreCase", false, "By default, we use case for matching tool names, by using this " +
                "option, we will ignore the case");
        options.addOption("stopwords", true, "A txt file with list of the stopwords (toolnames that you don't want to" +
                " use) (1 tool per line)");
        options.addOption("reverse", false, "Reverse the order of the result (by default ascending order)");
        options.addOption("verbose", false, "Add extra verbose information");


        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        if((cmd.hasOption("byAbstract") && cmd.hasOption("byTool")) || (!cmd.hasOption("byAbstract") && !cmd.hasOption("byTool"))) {
            printUsage(options);
            System.err.println("You should either have option byAbstract or byTool");
            exit(1);
        }
        if(! cmd.hasOption("inputTools")) {
            printUsage(options);
            System.err.println("The option inputTools is mandatory");
            exit(1);
        }

        long startFull = System.currentTimeMillis();
        if(cmd.hasOption("dir")) {
            File dir = new File(cmd.getOptionValue("dir"));
            if(dir.exists() && dir.isDirectory()) {
                long start = System.currentTimeMillis();
                System.out.println("Start parsing the directories");
                ParseDirectory parseDirectory = new ParseDirectory(dir);
                parseDirectory.parseDirectory();
                List<DHAbstract> dhAbstracts = parseDirectory.getDhAbstractList();
                System.out.println("Finished parsing the directories (in " + (System.currentTimeMillis() - start) + "ms)");
                start = System.currentTimeMillis();
                System.out.println("Start searching in the files");
                SearchInAbstract searchInAbstract = new SearchInAbstract(cmd.hasOption("verbose"));
                searchInAbstract.search(dhAbstracts, cmd.getOptionValue("inputTools"),
                        cmd.getOptionValue("stopwords"), cmd.hasOption("ignoreCase"));
                System.out.println("Finish searching in the files (in " + (System.currentTimeMillis() - start) + "ms)");
                if(cmd.hasOption("byAbstract"))
                    printResults(searchInAbstract.getLinkAbstractToolList(), cmd.hasOption("reverse"));
                if(cmd.hasOption("byTool"))
                    printResults(searchInAbstract.getLinkToolAbstractList(), cmd.hasOption("reverse"));
            } else {
                throw new RuntimeException("The directory used must exist (and be a directory, not a file)");
            }
        } else {
            throw new RuntimeException("The option dir must exist");
        }
        System.out.println("Total time " + (System.currentTimeMillis() - startFull) + "ms");
    }

    private static void printUsage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "java -jar ToolXtractor-full.jar", options );
    }

    private static void printResults(List<LinkData> linkDataList, boolean reverse) {
        linkDataList.sort(new LinkData(""));
        if(reverse)
            Collections.reverse(linkDataList);
        System.out.println("There are " + linkDataList.size() + " items listed below, and each have their own linked items");
        for(LinkData linkData : linkDataList) {
            if(linkData.getMentioned().size() > 0) {
                System.out.println("====================================================================================");
                System.out.println("Identifier: " + linkData.getIdentifier() + " (" + linkData.getMentioned().size() + ")");
                for (String mentioned : linkData.getMentioned()) {
                    System.out.println("-- " + mentioned);
                }
            }
        }
    }
}