package fr.adbonnin.romhack.collect;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface ByteIterator extends Iterator<Byte> {

    byte nextByte();

    default void checkHasNext() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
    }

    @Override
    default Byte next() {
        return nextByte();
    }

    default int nextBytes(byte[] bytes, int off, int len) {
        final int end = off + len;

        int pos = off;
        while (pos < end && hasNext()) {
            bytes[pos] = nextByte();
            ++pos;
        }

        return pos - off;
    }

    default int nextBytes(byte[] bytes, int off) {
        return nextBytes(bytes, off, bytes.length - off);
    }

    default int nextBytes(byte[] bytes) {
        return nextBytes(bytes, 0);
    }
}
