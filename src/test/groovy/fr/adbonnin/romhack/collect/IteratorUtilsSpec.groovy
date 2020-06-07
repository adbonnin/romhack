package fr.adbonnin.romhack.collect

import spock.lang.Specification

class IteratorUtilsSpec extends Specification {

    void "doit retourner la taille d'un iterateur"() {
        expect:
        IteratorUtils.size(iterator) == expectedSize

        where:
        iterator             || expectedSize
        [1, 2, 3].iterator() || 3
    }
}
