package fr.adbonnin.romhack.script;

import fr.adbonnin.romhack.bytes.BytesFunction;

import java.util.*;

public class Table implements BytesFunction<Iterator<Token>> {

    private final List<Token> tokens = new ArrayList<>();

    public List<Token> getTokens() {
        return tokens;
    }

    public Table add(Token token) {
        this.tokens.add(token);
        return this;
    }

    public Token decode(byte[] b, int off, int len) {

        for (Token token : tokens) {
            final Optional<Token> matched = token.matches(b, off, len);
            if (matched.isPresent()) {
                return matched.get();
            }
        }

        return Token.newCode(b, off);
    }

    @Override
    public Iterator<Token> apply(byte[] b, int off, int len) {
        return new Iterator<Token>() {

            private final int end = off + len;
            private int pos = off;

            @Override
            public boolean hasNext() {
                return pos < end;
            }

            @Override
            public Token next() {

                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                final Token token = decode(b, pos, end - pos);
                pos = pos + token.getLen();
                return token;
            }
        };
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Table)) {
            return false;
        }

        final Table that = (Table) obj;
        return tokens.equals(that.getTokens());
    }

    @Override
    public int hashCode() {
        return tokens.hashCode();
    }
}
