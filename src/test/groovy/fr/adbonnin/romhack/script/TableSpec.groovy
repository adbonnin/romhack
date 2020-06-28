package fr.adbonnin.romhack.script

import fr.adbonnin.romhack.Fixtures
import fr.adbonnin.romhack.collect.IteratorUtils
import spock.lang.Shared
import spock.lang.Specification

class TableSpec extends Specification {

    @Shared
    def buff4 = Fixtures.newTestBuff(4)

    void "doit decoder un tableau de bytes"() {
        given:
        def table = new Table()
                .add(Token.newLiteral(65 as byte, 'A' as char))
                .add(Token.newCode(66 as byte))

        expect:
        table.decode(buff4, off, len) == expectedToken

        where:
        off | len || expectedToken
        0   | 1   || Token.newLiteral(buff4, 0, 1, 'A' as char)
        1   | 1   || Token.newCode(buff4, 1)
    }

    void "doit decoder complement un tableau de bytes"() {
        given:
        def buff = Fixtures.newTestBuff(len)

        def table = new Table()
                .add(Token.newLiteral(65 as byte, 'A' as char))
                .add(Token.newEntity([66, 67] as byte[], "<test>"))

        when:
        def itr = table.apply(buff, 0, len)

        then:
        IteratorUtils.toList(itr) == expectedTokens

        where:
        len = 4

        expectedTokens = [
                Token.newLiteral(buff4, 0, 1, 'A' as char),
                Token.newEntity(buff4, 1, 2, "<test>"),
                Token.newCode(buff4, 3)
        ]
    }
}
