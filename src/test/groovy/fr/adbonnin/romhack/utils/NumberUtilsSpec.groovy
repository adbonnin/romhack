package fr.adbonnin.romhack.utils

import spock.lang.Specification
import spock.lang.Unroll

class NumberUtilsSpec extends Specification {

    @Unroll
    void "doit transformer une chaine hexa en nombre"() {
        expect:
        NumberUtils.hexToInt(hex) == expectedInt

        where:
        hex    || expectedInt
        '0'    || 0
        '5'    || 5
        'A'    || 10
        '10'   || 16
        '5A'   || 90
        '105A' || 4186
    }

    @Unroll
    void "doit formatter un nombre en hexa"() {
        expect:
        NumberUtils.intToHex(value, leadingZero) == expectedHexa

        where:
        value | leadingZero || expectedHexa
        5     | 1           || '5'
        10    | 2           || '0A'
        90    | 3           || '05A'
    }

    @Unroll
    void "doit retourner le nombre de numeros dans un nombre"() {
        expect:
        NumberUtils.numberOfDigits(value, base) == expectedNumber

        where:
        value                      | base || expectedNumber
        0                          | 10   || 1
        12                         | 10   || 2
        NumberUtils.hexToInt("F")  | 16   || 1
        NumberUtils.hexToInt("10") | 16   || 2
    }
}
