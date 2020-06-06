package fr.adbonnin.romhack.collect;

public class ByteArrayIterator implements ByteIterator {

    private byte[] array;

    private final int end;

    private int pos;

    public ByteArrayIterator(byte... array) {
        this(array, 0, array.length);
    }

    public ByteArrayIterator(byte[] array, int off, int len) {
        this.array = array;
        this.end = off + len;
        this.pos = off;
    }

    @Override
    public boolean hasNext() {
        if (pos < end) {
            return true;
        }
        else {
            array = null;
            return false;
        }
    }

    @Override
    public byte nextByte() {
        checkHasNext();
        return array[pos++];
    }

    @Override
    public int nextBytes(byte[] bytes, int off, int len) {
        checkHasNext();
        len = Math.min(len, end - pos);
        System.arraycopy(array, pos, bytes, off, len);
        pos = pos + len;
        return len;
    }
}
