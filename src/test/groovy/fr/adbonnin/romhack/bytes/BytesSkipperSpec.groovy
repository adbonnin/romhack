package fr.adbonnin.romhack.bytes

import spock.lang.Specification

class BytesSkipperSpec extends Specification {

    void "doit passer un nombre de bytes"() {
        given:
        def next = Mock(BytesFunction)
        def skipper = new BytesSkipper(next, numberToSkip)

        when:
        skipper.apply(b, off, len)

        then:
        1 * next.apply(b, expectedOff, expectdLen)

        where:
        numberToSkip || expectedOff | expectdLen
        2            || 5           | 5
        7            || 10          | 0
        10           || 10          | 0

        b = new byte[10]
        off = 3
        len = 7
    }
}
