package fr.adbonnin.romhack.collect

import spock.lang.Specification

import static java.util.Arrays.asList

class ByteListIteratorSpec extends Specification {

    void "doit retourner chaque valeurs de l'iterateur de bytes"() {
        given:
        byte[] bytes = [1, 2]

        when:
        def itr = new ByteListIterator(asList(bytes).iterator())

        then:
        itr.hasNext()
        itr.nextByte() == 1 as Byte

        then:
        itr.hasNext()
        itr.next() == 2 as Byte

        and:
        !itr.hasNext()

        when:
        itr.next()

        then:
        thrown(NoSuchElementException)
    }

    void "doit retourner plusieurs valeurs d'un iterateur de bytes"() {
        given:
        def itr = new ByteListIterator(asList(bytes).iterator())

        expect:
        itr.hasNext()

        when:
        itr.nextBytes(result, off, len)

        then:
        result == expectedResult

        where:
        result      | off | len || expectedResult
        new byte[2] | 1   | 1   || [0, 1] as byte[]
        new byte[5] | 2   | 2   || [0, 0, 1, 2, 0] as byte[]
        new byte[4] | 0   | 4   || [1, 2, 3, 0] as byte[]

        bytes = [1, 2, 3]
    }
}
