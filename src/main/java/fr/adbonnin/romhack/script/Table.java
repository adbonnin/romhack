package fr.adbonnin.romhack.script;

import fr.adbonnin.romhack.bytes.BytesFunction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Table implements BytesFunction<Iterator<Token>> {

    private final List<Token> tokens = new ArrayList<>();

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
        throw new UnsupportedOperationException();
    }
}
