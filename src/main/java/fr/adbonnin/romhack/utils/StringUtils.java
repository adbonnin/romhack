package fr.adbonnin.romhack.utils;

import java.util.Arrays;

public class StringUtils {

    public static final String EMPTY_STRING = "";

    public static String repeat(char c, int count) {

        if (count < 0) {
            throw new IllegalArgumentException("count is negative: " + count);
        }

        if (count == 1) {
            return String.valueOf(c);
        }

        if (count == 0) {
            return StringUtils.EMPTY_STRING;
        }

        final byte[] single = new byte[count];
        Arrays.fill(single, (byte) c);
        return new String(single);
    }

    private StringUtils() { /* Cannot be instantiated */}
}
