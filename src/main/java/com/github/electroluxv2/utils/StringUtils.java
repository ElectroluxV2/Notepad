package com.github.electroluxv2.utils;

public class StringUtils {
    public static double count(final String string, final String search) {
        return string.length() - string.replace(search, "").length();
    }
}
