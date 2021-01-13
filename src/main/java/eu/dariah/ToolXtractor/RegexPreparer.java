package eu.dariah.ToolXtractor;

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

    /**
     *
     * @param p The REGEX Pattern used to search
     * @param text The String in which we search
     * @return Return true if the Pattern was found, else otherwise. Will also only return false if the String is null
     */
    public static boolean findStringInText(Pattern p, String text) {
        if(text != null) {
            Matcher m = p.matcher(text);
            return m.find();
        }
        return false;
    }
    public static String replaceStringInText(Pattern p, String text) {
        Matcher m = p.matcher(text);
        text = m.replaceAll("<rs type=\"software\">$1</rs>");
        return text;
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
