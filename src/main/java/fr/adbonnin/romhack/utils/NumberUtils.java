package fr.adbonnin.romhack.utils;

public class NumberUtils {

    public static int hexToInt(String hex) {
        return Integer.valueOf(hex, 16);
    }

    public static String intToHex(int value, int leadingZero) {
        return String.format("%0" + leadingZero + "X", value);
    }

    public static int numberOfDigits(int value, int base) {
        int count = 0;

        while (value != 0) {
            value /= base;
            ++count;
        }

        return count;
    }

    private NumberUtils() { /* Cannot be instantiated */ }
}
