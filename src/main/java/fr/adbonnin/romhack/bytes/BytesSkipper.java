package fr.adbonnin.romhack.bytes;

import static java.util.Objects.requireNonNull;

public class BytesSkipper<R> implements BytesFunction<R> {

    private final BytesFunction<R> next;

    private final int numberToSkip;

    public BytesSkipper(BytesFunction<R> next, int numberToSkip) {
        this.next = requireNonNull(next);
        this.numberToSkip = numberToSkip;
    }

    @Override
    public R apply(byte[] b, int off, int len) {
        final int minToSkip = Math.min(numberToSkip, len);
        return next.apply(b, off + minToSkip, len - minToSkip);
    }
}
