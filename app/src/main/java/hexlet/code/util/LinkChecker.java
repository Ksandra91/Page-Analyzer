package hexlet.code.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkChecker {
    public static boolean isWebAdress(String text) throws Exception {

        Pattern pattern = Pattern.compile("(http|https)://[a-zA-Z0-9\\-.]+\\.[a-zA-Z]{2,}");

        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return true;
        } else {
            throw new Exception();
        }
    }
}
