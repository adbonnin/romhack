package fr.adbonnin.romhack.script

import spock.lang.Specification
import spock.lang.Unroll

class TokenSpec extends Specification {

    @Unroll
    void "doit decoder un tableau de bytes"() {
        given:
        def token = Token.newLiteral(tokenBytes, 0, 3, value as char)

        expect:
        token.matches(bytes, off, len) == Optional.ofNullable(expectedResult)

        where:
        bytes                     | off | len | value | expectedResult
        [0, 1, 2, 3] as byte[]    | 1   | 2   | "T"   | null
        [0, 1, 2, 3] as byte[]    | 1   | 3   | "T"   | Token.newLiteral(bytes, 1, 3, value as char)
        [0, 1, 2, 3, 4] as byte[] | 1   | 4   | "T"   | Token.newLiteral(bytes, 1, 3, value as char)

        tokenBytes = [1, 2, 3] as byte[]
    }
}
