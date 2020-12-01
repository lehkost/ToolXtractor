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
    public static Iterable<MatchResult> findStringInText(Pattern p, String text, boolean replaceStringInText) {
        if(replaceStringInText) {
            text = p.matcher(text).replaceAll("<rs type=\"software\">$1</rs>");
            System.out.println(text);
        }
        return allMatches(p, text);
//        List<String> allMatches = new ArrayList<>();
//
//        if(text != null) {
//            Matcher m = p.matcher(text);
//            while (m.find()) {
//                allMatches.add(m.group());
//            }
//            return allMatches;
//        }
//        return null;
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

    public static Iterable<MatchResult> allMatches(final Pattern p, final CharSequence input) {
        return new Iterable<MatchResult>() {
            public Iterator<MatchResult> iterator() {
                return new Iterator<MatchResult>() {
                    // Use a matcher internally.
                    final Matcher matcher = p.matcher(input);
                    // Keep a match around that supports any interleaving of hasNext/next calls.
                    MatchResult pending;

                    public boolean hasNext() {
                        // Lazily fill pending, and avoid calling find() multiple times if the
                        // clients call hasNext() repeatedly before sampling via next().
                        if (pending == null && matcher.find()) {
                            pending = matcher.toMatchResult();
                        }
                        return pending != null;
                    }

                    public MatchResult next() {
                        // Fill pending if necessary (as when clients call next() without
                        // checking hasNext()), throw if not possible.
                        if (!hasNext()) { throw new NoSuchElementException(); }
                        // Consume pending so next call to hasNext() does a find().
                        MatchResult next = pending;
                        pending = null;
                        return next;
                    }

                    /** Required to satisfy the interface, but unsupported. */
                    public void remove() { throw new UnsupportedOperationException(); }
                };
            }
        };
    }
}
