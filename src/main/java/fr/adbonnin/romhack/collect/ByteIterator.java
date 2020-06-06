package fr.adbonnin.romhack.collect;

import java.util.Iterator;

public interface ByteIterator extends Iterator<Byte> {

    byte nextByte();

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
}
