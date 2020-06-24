package fr.adbonnin.romhack

class Fixtures {

    static byte[] newTestBuff(int size) {
        def bytes = new byte[size]

        (0..<size).each { int value ->
            def c = ((value % 32) + 65) as byte
            bytes[value] = c
        }

        return bytes
    }

    private Fixtures() { /* Cannot be instantiated */ }
}
