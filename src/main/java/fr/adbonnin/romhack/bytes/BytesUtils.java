package fr.adbonnin.romhack.bytes;

import fr.adbonnin.romhack.utils.NumberUtils;

public class BytesUtils {

    public static byte hexToByte(String hex) {
        return (byte) NumberUtils.hexToInt(hex);
    }

    private BytesUtils() { /* Cannot be instantiated */ }
}
