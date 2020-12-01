package eu.dariah.ToolXtractor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RegexPreparer {
    public static final String REGEX_FIND_WORD = "(?i)(\\b%s\\b)";
    public static final String REGEX_FIND_WORD_WITH_CASE = "(\\b%s\\b)";

    public static Pattern regexPreparation(String toolname, boolean ignoreCase) {
        String regex = REGEX_FIND_WORD;
        if (isMoreUpperCaseAsSpaces(toolname) && !ignoreCase) { //We should not ignore case
            regex = REGEX_FIND_WORD_WITH_CASE;
        }
        regex = String.format(regex, Pattern.quote(toolname));
//        Pattern p = Pattern.compile(regex, Pattern.DOTALL); //DOTALL option for multiline
        return Pattern.compile(regex, Pattern.DOTALL); //DOTALL option for multiline
    }
    public static List<String> findStringInText(Pattern p, String text, boolean replaceStringInText) {
        if(replaceStringInText) {
            text = p.matcher(text).replaceAll("<rs type=\"software\">$1</rs>");
            System.out.println(text);
        }
        List<String> allMatches = new ArrayList<>();

        if(text != null) {
            Matcher m = p.matcher(text);
            while (m.find()) {
                allMatches.add(m.group());
            }
        }
        return allMatches;
    }

    private static boolean isMoreUpperCaseAsSpaces(String input) {
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
