package fr.adbonnin.romhack.bytes;

public interface BytesFunction<R> {

    R apply(byte[] b, int off, int len);
}
