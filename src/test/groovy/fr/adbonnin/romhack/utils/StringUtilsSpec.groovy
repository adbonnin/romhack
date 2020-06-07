package fr.adbonnin.romhack.utils

import spock.lang.Specification

class StringUtilsSpec extends Specification {

    void "doit repeter un character"() {
        expect:
        StringUtils.repeat(c, count) == expectedResult

        where:
        count || expectedResult
        1     || 'a'
        3     || 'aaa'

        c = 'a' as char
    }
}
