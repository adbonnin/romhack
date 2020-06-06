package fr.adbonnin.romhack.collect

import static java.util.Arrays.asList

class ByteObjectIteratorSpec extends AbstractByteIteratorSpec {

    @Override
    ByteIterator buildByteIterator(byte[] array) {
        return new ByteObjectIterator(asList(array).iterator())
    }
}
