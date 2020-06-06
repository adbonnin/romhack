package fr.adbonnin.romhack.bytes

import spock.lang.Specification

class BytesLimiterSpec extends Specification {

    void "doit limiter le nombre de bytes lus"() {
        given:
        def next = Mock(BytesFunction)
        def limiter = new BytesLimiter(next, limitLength)

        when:
        limiter.apply(b, off, len)

        then:
        1 * next.apply(b, off, expectedLen)

        where:
        limitLength || expectedLen
        -4          || -4
        4           || 4
        10          || 7

        b = new byte[10]
        off = 3
        len = 7
    }
}
