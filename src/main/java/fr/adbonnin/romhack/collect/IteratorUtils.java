package fr.adbonnin.romhack.collect;

import java.util.Iterator;

public class IteratorUtils {

    public static long size(Iterator<?> iterator) {
        long count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    private IteratorUtils() { /* Cannot be instantiated */ }
}
