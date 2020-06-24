package fr.adbonnin.romhack.script;

import fr.adbonnin.romhack.bytes.BytesUtils;

import java.io.IOException;
import java.io.Reader;

public class TokenParser {

    public static final char CODE_START = '[';
    public static final char CODE_END = ']';

    public static final char ENTITY_START = '<';
    public static final char ENTITY_END = '>';

    public static final char ESCAPE = '\\';
    public static final char COMMENT = '#';

    public static void parse(Reader reader, TokenVisitor visitor) throws IOException {
        final StringBuilder buf = new StringBuilder();
        Token.Type stat = Token.Type.LITERAL;

        boolean escaped = false;
        boolean commented = false;

        for (; ; ) {
            final int c = reader.read();
            final boolean endOfStream = c == -1;

            final boolean newLine = c == '\r' || c == '\n' || endOfStream;
            boolean endBlock = false;
            boolean append = false;

            if (newLine) {
                escaped = false;
                commented = false;

                if (stat == Token.Type.CODE || stat == Token.Type.ENTITY) {
                    throw new IllegalStateException("Line can't end with code or entity opened; " +
                                                    "type: " + stat + ", " +
                                                    "block: " + buf.toString());
                }
                else if (endOfStream) {
                    break;
                }
            }
            else if (escaped) {
                escaped = false;
                append = true;
            }
            else if (!commented) {
                switch (c) {
                    case ESCAPE:
                        escaped = true;
                        break;

                    case COMMENT:
                        if (stat.isLiteral()) {
                            commented = true;
                        }
                        else {
                            append = true;
                        }
                        break;

                    case CODE_START:
                        if (stat.isLiteral()) {
                            stat = Token.Type.CODE;
                        }
                        else {
                            append = true;
                        }
                        break;

                    case CODE_END:
                        if (stat.isCode()) {
                            endBlock = true;
                        }
                        else {
                            append = true;
                        }
                        break;

                    case ENTITY_START:
                        if (stat.isLiteral()) {
                            stat = Token.Type.ENTITY;
                        }
                        else {
                            append = true;
                        }
                        break;

                    case ENTITY_END:
                        if (stat.isEntity()) {
                            endBlock = true;
                        }
                        else {
                            append = true;
                        }
                        break;

                    default:
                        append = true;
                        break;
                }
            }

            if (append) {
                if (Token.Type.LITERAL.equals(stat)) {
                    visitor.visitLiteral((char) c);
                }
                else {
                    buf.append((char) c);
                }
            }
            else if (endBlock) {
                final String value = buf.toString();

                if (Token.Type.ENTITY.equals(stat)) {
                    visitor.visitEntity(value);
                }
                else if (value.equals(Token.BLOCK_SEPARATOR.getValue())) {
                    visitor.visitBlockSeparator();
                }
                else if (value.length() > 2) {
                    throw new IllegalStateException("Code must have a length of 2 or less; " +
                                                    "type: " + stat + ", " +
                                                    "block: " + buf.toString());
                }
                else {
                    visitor.visitCode(BytesUtils.hexToByte(value));
                }

                buf.setLength(0);
                stat = Token.Type.LITERAL;
            }
        }
    }

    public interface TokenVisitor {

        void visitLiteral(char c);

        void visitCode(byte b);

        void visitEntity(String str);

        void visitBlockSeparator();
    }
}
