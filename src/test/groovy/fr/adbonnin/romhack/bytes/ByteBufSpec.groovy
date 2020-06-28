package fr.adbonnin.romhack.bytes

import spock.lang.Specification

class ByteBufSpec extends Specification {

    void "doit creer un buffer avec une taille initiale"() {
        given:
        def buf = new ByteBuf(initialCapacity)

        expect:
        buf.isEmpty()
        buf.array().size() == initialCapacity

        where:
        initialCapacity | _
        0               | _
        10              | _
    }

    void "doit lever une exception si un buffer est cree avec une taille initiale negative"() {
        when:
        new ByteBuf(negativeInitialCapacity)

        then:
        thrown(IllegalArgumentException)

        where:
        negativeInitialCapacity = -1
    }

    void "doit ecrire dans un buffer"() {
        given:
        def buf = new ByteBuf(0)

        expect:
        buf.isEmpty()

        when:
        buf.write(1 as byte)

        then:
        buf.newArray() == [1] as byte[]

        when:
        buf.write([2, 3] as byte[])

        then:
        buf.newArray() == [1, 2, 3] as byte[]
        buf.newArray(1, 2) == [2, 3] as byte[]
    }

    void "doit creer une copie du contenu du buffer"() {
        given:
        def buf = new ByteBuf()
        buf.write([1, 2, 3, 4] as byte[])

        expect:
        buf.newArray() == [1, 2, 3, 4] as byte[]
        buf.newArray(1, 2) == [2, 3] as byte[]
    }

    void "doit manipuler le contenu du buffer"() {
        given:
        def buf = new ByteBuf()
        buf.write([1, 2, 3, 4] as byte[])

        when:
        buf.size(3)

        then:
        buf.newArray() == [1, 2, 3] as byte[]

        when:
        buf.reset()

        then:
        buf.isEmpty()
    }

    void "doit compacter le contenu du buffer"() {
        given:
        def buf = new ByteBuf()
        buf.write([1, 2, 3, 4] as byte[])

        when:
        buf.compact(2)

        then:
        buf.newArray() == [3, 4] as byte[]

        when:
        buf.compact(2)

        then:
        buf.isEmpty()
    }
}
