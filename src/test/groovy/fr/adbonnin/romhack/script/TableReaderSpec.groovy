package fr.adbonnin.romhack.script

import spock.lang.Specification

class TableReaderSpec extends Specification {

    void "doit lire une table"() {
        given:
        def tableReader = new TableReader()

        expect:
        tableReader.read(new StringReader(str)) == expectedTable

        where:
        str          || expectedTable
        "00=A"       || new Table().add(Token.newLiteral(0 as byte, 'A' as char))
        "00=A\n01=B" || new Table().add(Token.newLiteral(0 as byte, 'A' as char)).add(Token.newLiteral(1 as byte, 'B' as char))
        "02=<test>"  || new Table().add(Token.newEntity([02] as byte[], "test"))
    }

    void "doit lever une exception lors de la lecture d'une table"() {
        given:
        def tableReader = new TableReader()

        when:
        tableReader.read(new StringReader(str))

        then:
        def e = thrown(IllegalArgumentException)
        e.message == expectedException.message

        where:
        str        || expectedException
        "00=[00]"  || new IllegalArgumentException("Code are not allowed in table")
        "00=[---]" || new IllegalArgumentException("Block separator are not allowed in table")
        "00=AB"    || new IllegalArgumentException("A token already exists on this line; value: A")
    }
}
