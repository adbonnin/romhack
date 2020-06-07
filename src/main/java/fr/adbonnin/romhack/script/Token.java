package fr.adbonnin.romhack.script;

public class Token {

    private final byte[] bytes;

    private final int off;

    private final int len;

    private final Type type;

    private final String value;

    public Token(byte[] bytes, Type type, String value) {
        this(bytes, 0, bytes.length, type, value);
    }

    public Token(byte[] bytes, int off, int len, Type type, String value) {
        this.bytes = bytes;
        this.off = off;
        this.len = len;
        this.type = type;
        this.value = value;
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

    public enum Type {
        LITERAL, CODE, BLOCK_SEPARATOR
    }
}
