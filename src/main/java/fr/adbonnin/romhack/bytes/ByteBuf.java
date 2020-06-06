package fr.adbonnin.romhack.bytes;

import fr.adbonnin.romhack.collect.ArraysUtils;
import fr.adbonnin.romhack.collect.ByteArrayIterator;
import fr.adbonnin.romhack.collect.ByteIterable;
import fr.adbonnin.romhack.collect.ByteIterator;

import java.util.Arrays;

public class ByteBuf implements ByteIterable {

    private int size = 0;

    private byte[] array;

    public ByteBuf() {
        this(ArraysUtils.BUF_SIZE);
    }

    public ByteBuf(int initialCapacity) {

        if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity must be positive");
        }

        this.array = new byte[initialCapacity];
    }

    public byte[] array() {
        return array;
    }

    public byte[] newArray() {
        return Arrays.copyOf(array, size);
    }

    public byte[] newArray(int off, int len) {

        if (len == 0) {
            return ArraysUtils.EMPTY_BYTE_ARRAY;
        }

        final byte[] copy = new byte[len];
        System.arraycopy(array, off, copy, 0, len);
        return copy;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void size(int size) {
        ensureCapacity(size);
        this.size = size;
    }

    @Override
    public ByteIterator iterator() {
        return new ByteArrayIterator(array, 0, size);
    }

    public void write(String str) {
        write(str.getBytes());
    }

    public void write(byte[] b) {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) {
        ensureIncreaseCapacity(len);
        System.arraycopy(b, off, array, size, len);
        size = size + len;
    }

    public void write(byte b) {
        ensureIncreaseCapacity(1);
        array[size] = b;
        ++size;
    }

    public void reset() {
        size = 0;
    }

    public void compact(int index) {
        if (index >= size) {
            size = 0;
        }
        else if (index > 0) {
            final int len = size - index;
            System.arraycopy(array, index, array, 0, len);
            size = len;
        }
    }

    public void ensureIncreaseCapacity(int len) {
        ensureCapacity(size + len);
    }

    public void ensureCapacity(int minCapacity) {
        final int oldCapacity = array.length;

        if (minCapacity > oldCapacity) {

            int newCapacity = (oldCapacity * 3) / 2 + 1;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }

            array = Arrays.copyOf(array, newCapacity);
        }
    }
}
