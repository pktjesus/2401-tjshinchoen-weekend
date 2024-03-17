package com.test1.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

    public static String removeTags(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }

        Matcher m = REMOVE_TAGS.matcher(string);
        return m.replaceAll("");
    }
}
