package fr.adbonnin.romhack.script;

import fr.adbonnin.romhack.bytes.BytesUtils;
import fr.adbonnin.romhack.utils.NumberUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class Token {

    private final byte[] bytes;

    private final int off;

    private final int len;

    private final Type type;

    private final String value;

    private Token(byte[] bytes, Type type, String value) {
        this(bytes, 0, bytes.length, type, value);
    }

    private Token(byte[] bytes, int off, int len, Type type, String value) {
        this.bytes = bytes;
        this.off = off;
        this.len = len;
        this.type = type;
        this.value = value;
    }

    public static Token newCode(byte b) {
        return newCode(new byte[]{b}, 0);
    }

    public static Token newCode(byte[] bytes, int off) {
        return new Token(bytes, off, 1, Token.Type.CODE, NumberUtils.intToHex(bytes[off], 2));
    }

    public static Token newLiteral(byte b, char c) {
        return newLiteral(new byte[]{b}, 0, 1, c);
    }

    public static Token newLiteral(byte[] bytes, int off, int len, char c) {
        return new Token(bytes, off, len, Type.LITERAL, String.valueOf(c));
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int getOff() {
        return off;
    }

    public int getLen() {
        return len;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public Optional<Token> matches(byte[] b, int off, int len) {
        return len >= this.len && BytesUtils.equalsBytes(b, off, this.bytes, this.off, this.len)
                ? Optional.of(new Token(b, off, this.len, this.type, this.value))
                : Optional.empty();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Token)) {
            return false;
        }

        final Token that = (Token) obj;
        return Arrays.equals(bytes, that.bytes) &&
                off == that.off &&
                len == that.len &&
                Objects.equals(type, that.type) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + Arrays.hashCode(bytes);
        hashCode = 31 * hashCode + off;
        hashCode = 31 * hashCode + len;
        hashCode = 31 * hashCode + Objects.hashCode(type);
        hashCode = 31 * hashCode + Objects.hashCode(value);
        return hashCode;
    }

    public enum Type {
        LITERAL, CODE, BLOCK_SEPARATOR
    }
}
