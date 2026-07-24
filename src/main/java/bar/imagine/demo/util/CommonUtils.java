package bar.imagine.demo.util;

public class CommonUtils {
    public static final String ALL_WHITESPACE_REGEX = "\\s";


    public static String removeWhitespacesFromString(String s) {
        return s.replaceAll(ALL_WHITESPACE_REGEX, "");
    }

    private CommonUtils() {}
}
