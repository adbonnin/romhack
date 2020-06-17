package fr.adbonnin.romhack.script

import fr.adbonnin.romhack.Fixtures
import spock.lang.Shared
import spock.lang.Specification

class TableSpec extends Specification {

    @Shared
    def buff = Fixtures.newTestBuff(32)

    void "doit decoder un tableau de bytes"() {
        given:
        def table = new Table()
                .add(Token.newLiteral(65 as byte, 'A' as char))
                .add(Token.newCode(66 as byte))
                .add()

        expect:
        table.decode(buff, off, len) == expectedToken

        where:
        off | len || expectedToken
        0   | 1   || Token.newLiteral(buff, 0, 1, 'A' as char)
        1   | 1   || Token.newCode(buff, 1)
    }
}
