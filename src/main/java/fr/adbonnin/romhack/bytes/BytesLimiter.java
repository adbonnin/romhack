package fr.adbonnin.romhack.bytes;

import static java.util.Objects.requireNonNull;

public class BytesLimiter<R> implements BytesFunction<R> {

    private final BytesFunction<R> next;

    private final int limitLength;

    public BytesLimiter(BytesFunction<R> next, int limitLength) {
        this.next = requireNonNull(next);
        this.limitLength = limitLength;
    }

    @Override
    public R apply(byte[] b, int off, int len) {
        return next.apply(b, off, Math.min(len, limitLength));
    }
}
