package fr.adbonnin.romhack.bytes;

import fr.adbonnin.romhack.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BytesUtils {

    public static boolean equalsBytes(byte[] left, int leftPos, byte[] right, int rightPos, int length) {

        for (int i = 0; i < length; i++) {
            if (left[leftPos + i] != right[rightPos + i]) {
                return false;
            }
        }

        return true;
    }

    public static byte hexToByte(String hex) {
        return (byte) NumberUtils.hexToInt(hex);
    }

    public static String printBytes(byte[] bytes, int off, int len, int byteCount) {
        final List<String> buff = new ArrayList<>();

        final int pre = off % byteCount;
        if (pre > 0) {
            buff.addAll(Collections.nCopies(pre, "  "));
        }

        final int maxLen = byteCount - pre;
        len = Math.min(maxLen, len);

        for (int i = 0; i < len; i++) {
            buff.add(NumberUtils.intToHex(bytes[off + i], 2));
        }

        final int post = Math.max(0, byteCount - pre - len);
        if (post > 0) {
            buff.addAll(Collections.nCopies(post, "  "));
        }

        return String.join(" ", buff);
    }

    private BytesUtils() { /* Cannot be instantiated */ }
}
