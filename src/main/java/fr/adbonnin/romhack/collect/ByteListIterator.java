package fr.adbonnin.romhack.collect;

import java.util.Iterator;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class ByteListIterator implements ByteIterator {

    private final Iterator<Byte> itr;

    public ByteListIterator(Iterator<Byte> itr) {
        this.itr = requireNonNull(itr);
    }

    @Override
    public byte nextByte() {
        return itr.next();
    }

    @Override
    public boolean hasNext() {
        return itr.hasNext();
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

        if (!(obj instanceof ByteListIterator)) {
            return false;
        }

        final ByteListIterator that = (ByteListIterator) obj;
        return Objects.equals(itr, that.itr);
    }

    @Override
    public String toString() {
        return itr.toString();
    }
}
