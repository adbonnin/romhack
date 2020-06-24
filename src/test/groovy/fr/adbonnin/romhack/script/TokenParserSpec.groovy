package fr.adbonnin.romhack.script

import fr.adbonnin.romhack.Fixtures
import spock.lang.Specification
import spock.lang.Unroll

class TokenParserSpec extends Specification {

    static def testBuff = Fixtures.newTestBuff(10)

    static Token testLiteral(char c) {
        return Token.newLiteral(testBuff[0], c)
    }

    static Token testCode(byte b) {
        return Token.newCode(b)
    }

    static Token testEntity(String str) {
        return Token.newEntity(testBuff, str)
    }

    @Unroll
    void "should parse literals"() {
        given:
        def visitor = new ListTokenParser()

        when:
        TokenParser.parse(new StringReader(str), visitor)

        then:
        visitor.tokens == expectedValues.collect { testLiteral(it as char) }

        where:
        str            || expectedValues
        'ab'           || ['a', 'b']
        'a\rb'         || ['a', 'b']
        'a\nb'         || ['a', 'b']

        'a#comment\n'  || ['a']
        'a#comment\nb' || ['a', 'b']

        'a\\\\'        || ['a', '\\']
        'a\\#'         || ['a', '#']

        'a\\<'         || ['a', '<']
        'a>'           || ['a', '>']

        'a\\['         || ['a', '[']
        'a]'           || ['a', ']']
    }

    @Unroll
    void "should parse codes"() {
        given:
        def visitor = new ListTokenParser()

        when:
        TokenParser.parse(new StringReader(str), visitor)

        then:
        visitor.tokens == expectedValues.collect { testCode(it as byte) }

        where:
        str                || expectedValues
        '[0A]'             || [10]
        '[\\0\\A]'         || [10]
        '[\\0\\A]#comment' || [10]
    }

    @Unroll
    void "should parse entity"() {
        given:
        def visitor = new ListTokenParser()

        when:
        TokenParser.parse(new StringReader(str), visitor)

        then:
        visitor.tokens == expectedValues.collect { testEntity(it) }

        where:
        str          || expectedValues
        '<test>'     || ['test']

        '<test\\\\>' || ['test\\']
        '<test#>'    || ['test#']

        '<test<>'    || ['test<']
        '<test\\>>'  || ['test>']

        '<test[>'    || ['test[']
        '<test]>'    || ['test]']
    }

    @Unroll
    void "should parse block separator"() {
        given:
        def visitor = new ListTokenParser()

        when:
        TokenParser.parse(new StringReader(str), visitor)

        then:
        visitor.tokens == expectedValues

        where:
        str       || expectedValues
        '[---]'   || [Token.BLOCK_SEPARATOR]
        '[-\\--]' || [Token.BLOCK_SEPARATOR]
    }

    @Unroll
    void "should throw an exception"() {
        given:
        def reader = new StringReader(str)
        def visitor = Mock(TokenParser.TokenVisitor)

        when:
        TokenParser.parse(reader, visitor)

        then:
        thrown(IllegalStateException)

        where:
        str        || _
        "[test\n]" || _
        "[test#"   || _
    }

    class ListTokenParser implements TokenParser.TokenVisitor {

        List<Token> tokens = []

        @Override
        void visitLiteral(char c) {
            tokens.add(testLiteral(c))
        }

        @Override
        void visitCode(byte b) {
            tokens.add(testCode(b))
        }

        @Override
        void visitEntity(String str) {
            tokens.add(testEntity(str))
        }

        @Override
        void visitBlockSeparator() {
            tokens.add(Token.BLOCK_SEPARATOR)
        }
    }
}
