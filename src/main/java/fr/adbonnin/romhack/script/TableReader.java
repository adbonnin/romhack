package fr.adbonnin.romhack.script;

import fr.adbonnin.romhack.bytes.BytesUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static java.util.Objects.requireNonNull;

public class TableReader {

    public static final char SEPARATOR = '=';

    public Table read(Reader reader) throws IOException {
        requireNonNull(reader);

        final Table table = new Table();

        final BufferedReader buf = new BufferedReader(reader);
        String line;
        while ((line = buf.readLine()) != null) {

            if (line.trim().startsWith("#")) {
                continue;
            }

            final int s = line.indexOf(SEPARATOR);
            if (s == -1) {
                continue;
            }

            final byte[] bytes = BytesUtils.toBytes(line.trim().substring(0, s));
            final String value = line.substring(s + 1);

            final TokenParser.TokenVisitor visitor = new TokenParser.TokenVisitor() {

                private Token done;

                @Override
                public void visitLiteral(char c) {
                    addToken(Token.newLiteral(bytes, c));
                }

                @Override
                public void visitCode(byte b) {
                    throw new IllegalArgumentException("Code are not allowed in table");
                }

                @Override
                public void visitEntity(String str) {
                    addToken(Token.newEntity(bytes, str));
                }

                @Override
                public void visitBlockSeparator() {
                    throw new IllegalArgumentException("Block separator are not allowed in table");
                }

                private void addToken(Token token) {
                    if (done == null) {
                        done = token;
                        table.add(token);
                    }
                    else {
                        throw new IllegalArgumentException("A token already exists on this line; " +
                                "value: " + done.getValue());
                    }
                }
            };

            TokenParser.parse(new StringReader(value), visitor);
        }

        return table;
    }
}
