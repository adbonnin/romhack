package fr.adbonnin.romhack.collect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorUtils {

    public static long size(Iterator<?> iterator) {
        long count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    public static <E> List<E> toList(Iterator<? extends E> itr) {
        final List<E> list = new ArrayList<>();
        itr.forEachRemaining(list::add);
        return list;
    }

    private IteratorUtils() { /* Cannot be instantiated */ }
}
