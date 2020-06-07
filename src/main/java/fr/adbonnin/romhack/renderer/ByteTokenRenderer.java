package fr.adbonnin.romhack.renderer;

import fr.adbonnin.romhack.bytes.BytesFunction;
import fr.adbonnin.romhack.bytes.BytesUtils;
import fr.adbonnin.romhack.script.Token;
import fr.adbonnin.romhack.utils.NumberUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import static java.util.Objects.requireNonNull;

public class ByteTokenRenderer implements BytesFunction<Iterator<Token>> {

    public static final String LS = System.lineSeparator();

    public static final int BYTE_COUNT_PER_OCTET = 8;

    public static final int BYTE_COUNT_PER_LINE = BYTE_COUNT_PER_OCTET * 2;

    private final BytesFunction<Iterator<? extends Token>> next;

    private final Writer writer;

    public ByteTokenRenderer(BytesFunction<Iterator<? extends Token>> next, Writer writer) {
        this.next = requireNonNull(next);
        this.writer = requireNonNull(writer);
    }

    @Override
    public Iterator<Token> apply(byte[] b, int off, int len) {
        final Iterator<? extends Token> itr = next.apply(b, off, len);
        return new RendererIterator(b, off, len, itr);
    }

    protected String renderLine(byte[] b, int off, int len, int numberOfDigits, CharSequence value) {
        final int lineOff = off % BYTE_COUNT_PER_LINE;
        final String lineNumbers = NumberUtils.intToHex(off - lineOff, numberOfDigits);

        final int maxFirstOctetLen = Math.max(0, BYTE_COUNT_PER_OCTET - lineOff);
        final int firstOctetLen = Math.min(len, maxFirstOctetLen);
        final String firstOctet = BytesUtils.printBytes(b, off, firstOctetLen, BYTE_COUNT_PER_OCTET);

        final int secondOctetLen = Math.max(0, len - firstOctetLen);
        final String secondOctet = BytesUtils.printBytes(b, off + firstOctetLen, secondOctetLen, BYTE_COUNT_PER_OCTET);

        return lineNumbers + ": " + firstOctet + "   " + secondOctet + "  " + value + LS;
    }

    private class RendererIterator implements Iterator<Token> {

        private final byte[] array;
        private int off;
        private int len;
        private final Iterator<? extends Token> itr;

        private final int numberOfDigits;
        private int byteLen;
        private final StringBuilder buf = new StringBuilder();

        public RendererIterator(byte[] array, int off, int len, Iterator<? extends Token> itr) {
            this.array = array;
            this.off = off;
            this.len = len;
            this.itr = itr;

            this.numberOfDigits = NumberUtils.numberOfDigits(array.length, BYTE_COUNT_PER_LINE);
            this.byteLen = off % BYTE_COUNT_PER_LINE;
        }

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

        @Override
        public Token next() {
            final Token token = itr.next();
            byteLen = byteLen + token.getLen();
            buf.append(token.getValue());

            for (; ; ) {
                if (byteLen < BYTE_COUNT_PER_LINE && (byteLen == 0 || hasNext())) {
                    return token;
                }

                byteLen = byteLen - (off % BYTE_COUNT_PER_LINE);

                final String str = renderLine(array, off, byteLen, numberOfDigits, buf);
                off = off + byteLen;
                len = len - byteLen;

                byteLen = 0;
                buf.setLength(0);

                try {
                    writer.write(str);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
