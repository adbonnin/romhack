package fr.adbonnin.romhack.collect;

import java.util.Iterator;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class ByteObjectIterator implements ByteIterator {

    private final Iterator<Byte> itr;

    public ByteObjectIterator(Iterator<Byte> itr) {
        this.itr = requireNonNull(itr);
    }

    @Override
    public boolean hasNext() {
        return itr.hasNext();
    }

    @Override
    public byte nextByte() {
        return itr.next();
    }

    @Override
    public int hashCode() {
        return itr.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ByteObjectIterator)) {
            return false;
        }

        final ByteObjectIterator that = (ByteObjectIterator) obj;
        return Objects.equals(itr, that.itr);
    }

    @Override
    public String toString() {
        return itr.toString();
    }
}
