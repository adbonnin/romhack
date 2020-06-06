package fr.adbonnin.romhack.bytes

import spock.lang.Specification

class BytesUtilsSpec extends Specification {

    void "doit transformer une chaine hexa en bytes"() {
        expect:
        BytesUtils.hexToByte(hex) == expectedByte as byte

        where:
        hex  || expectedByte
        '0'  || 0
        '5'  || 5
        'A'  || 10
        '10' || 16
        '5A' || 90
    }
}
