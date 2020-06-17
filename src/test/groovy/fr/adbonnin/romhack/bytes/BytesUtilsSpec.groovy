package fr.adbonnin.romhack.bytes

import fr.adbonnin.romhack.Fixtures
import spock.lang.Specification

class BytesUtilsSpec extends Specification {

    void "doit verifier que deux tableaux de bytes sont egaux"() {
        expect:
        BytesUtils.equalsBytes(left, leftPos, right, rightPos, length) == expectedEquals

        where:
        left                      | leftPos | right                     | rightPos | length || expectedEquals
        [1, 2, 3] as byte[]       | 0       | [0, 0, 1, 2, 3] as byte[] | 2        | 3      || true
        [0, 0, 1, 2, 3] as byte[] | 2       | [1, 2, 3] as byte[]       | 0        | 3      || true
    }

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

    void "doit mettre en forme des bytes"() {
        given:
        def bytes = Fixtures.newTestBuff(buffSize)

        expect:
        BytesUtils.printBytes(bytes, off, len, byteCount) == expectedResult

        where:
        off | len | byteCount || expectedResult
        0   | 3   | 4         || "41 42 43   "
        8   | 8   | 4         || "49 4A 4B 4C"
        2   | 3   | 4         || "      43 44"
        2   | 4   | 8         || "      43 44 45 46      "
        4   | 8   | 8         || "            45 46 47 48"

        buffSize = 256
    }
}
