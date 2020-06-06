package fr.adbonnin.romhack.collect

import spock.lang.Specification
import spock.lang.Unroll

abstract class AbstractByteIteratorSpec extends Specification {

    abstract ByteIterator buildByteIterator(byte[] array)

    void "doit retourner chaque valeurs de l'iterateur de bytes"() {
        given:
        byte[] array = [1, 2]

        when:
        def itr = buildByteIterator(array)

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

    @Unroll
    void "doit retourner plusieurs valeurs d'un iterateur de bytes"() {
        given:
        def itr = buildByteIterator(array)

        expect:
        itr.hasNext()

        when:
        def bytes = new byte[resultLength]
        def result = itr.nextBytes(bytes, off, len)

        then:
        bytes == expectedBytes
        result == expectedResult

        where:
        resultLength | off | len || expectedBytes             | expectedResult
        2            | 1   | 1   || [0, 1] as byte[]          | 1
        5            | 2   | 2   || [0, 0, 1, 2, 0] as byte[] | 2
        4            | 0   | 4   || [1, 2, 3, 0] as byte[]    | 3

        array = [1, 2, 3] as byte[]
    }

    @Unroll
    void "doit retourner plusieurs valeurs d'un iterateur de bytes sans len"() {
        given:
        def itr = buildByteIterator(array)

        expect:
        itr.hasNext()

        when:
        def bytes = new byte[resultLength]
        def result = itr.nextBytes(bytes, off)

        then:
        bytes == expectedBytes
        result == expectedResult

        where:
        resultLength | off || expectedBytes             | expectedResult
        2            | 1   || [0, 1] as byte[]          | 1
        5            | 2   || [0, 0, 1, 2, 3] as byte[] | 3
        4            | 0   || [1, 2, 3, 0] as byte[]    | 3

        array = [1, 2, 3] as byte[]
    }

    @Unroll
    void "doit retourner plusieurs valeurs d'un iterateur de bytes sans off"() {
        given:
        def itr = buildByteIterator(array)

        expect:
        itr.hasNext()

        when:
        def bytes = new byte[resultLength]
        def result = itr.nextBytes(bytes)

        then:
        bytes == expectedBytes
        result == expectedResult

        where:
        resultLength || expectedBytes             | expectedResult
        2            || [1, 2] as byte[]          | 2
        5            || [1, 2, 3, 0, 0] as byte[] | 3
        4            || [1, 2, 3, 0] as byte[]    | 3

        array = [1, 2, 3] as byte[]
    }

    void "doit verifier si une exception est levee s'il reste encore des elements dans l'iterateur"() {
        given:
        def array = [1, 2] as byte[]
        def itr = buildByteIterator(array)

        expect:
        itr.hasNext()

        when:
        itr.checkHasNext()
        itr.next()

        then:
        notThrown(NoSuchElementException)
        itr.hasNext()

        when:
        itr.checkHasNext()
        itr.next()

        then:
        notThrown(NoSuchElementException)
        !itr.hasNext()

        when:
        itr.checkHasNext()

        then:
        thrown(NoSuchElementException)
    }
}
