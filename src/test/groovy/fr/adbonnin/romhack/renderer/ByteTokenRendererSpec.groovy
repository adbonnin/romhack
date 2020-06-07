package fr.adbonnin.romhack.renderer

import fr.adbonnin.romhack.Fixtures
import fr.adbonnin.romhack.bytes.BytesFunction
import fr.adbonnin.romhack.collect.IteratorUtils
import fr.adbonnin.romhack.script.Token
import spock.lang.Specification
import spock.lang.Unroll

class ByteTokenRendererSpec extends Specification {

    @Unroll
    void "doit rendre un iterateur de tokens"() {
        given:
        def bytes = Fixtures.newTestBuff(bytesSize)

        def tokens = (0..<tokensSize).toList().collect {
            def b = bytes[off + it]
            return new Token([b] as byte[], 0, 1, Token.Type.LITERAL, String.valueOf(b as char))
        }

        def function = Mock(BytesFunction) {
            apply(_, _, _) >> tokens.iterator()
        }

        and:
        def writer = new StringWriter()
        def renderer = new ByteTokenRenderer(function, writer)

        when:
        def itr = renderer.apply(bytes, off, 0)
        IteratorUtils.size(itr)
        writer.close()

        then:
        writer.toString() == expectedResult

        where:
        tokensSize | off | expectedResult

        2          | 2   | "" +
                "000:       43 44                                        CD" + ByteTokenRenderer.LS

        2          | 10  | "" +
                "000:                                 4B 4C              KL" + ByteTokenRenderer.LS

        17         | 3   | "" +
                "000:          44 45 46 47 48   49 4A 4B 4C 4D 4E 4F 50  DEFGHIJKLMNOP" + ByteTokenRenderer.LS +
                "010: 51 52 53 54                                        QRST" + ByteTokenRenderer.LS

        6          | 20  | "" +
                "010:             55 56 57 58   59 5A                    UVWXYZ" + ByteTokenRenderer.LS

        bytesSize = 256
    }
}
