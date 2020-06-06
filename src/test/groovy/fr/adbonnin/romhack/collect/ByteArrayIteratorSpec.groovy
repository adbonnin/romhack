package fr.adbonnin.romhack.collect

class ByteArrayIteratorSpec extends AbstractByteIteratorSpec {

    @Override
    ByteIterator buildByteIterator(byte[] array) {
        return new ByteArrayIterator(array)
    }
}
