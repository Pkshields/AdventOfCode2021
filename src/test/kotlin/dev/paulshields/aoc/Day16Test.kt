package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isEqualTo
import dev.paulshields.aoc.testcommon.runTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class Day16Test {
    @TestFactory
    fun `should correctly count all packet versions from packets`() = listOf(
        "8A004A801A8002F478" to 16,
        "620080001611562C8802118E34" to 12,
        "C0015000016115A2E0802F182340" to 23,
        "A0016C880162017C3686B18A3D4780" to 31)
        .runTest { (packetTransmission, expectedSum) ->
            val result = sumAllVersionNumbersInBitsPackets(packetTransmission)

            assertThat(result).isEqualTo(expectedSum)
        }

    @TestFactory
    fun `should correctly calculate the result from all BITS packets`() = listOf(
        "C200B40A82" to 3L,
        "04005AC33890" to 54L,
        "880086C3E88112" to 7L,
        "CE00C43D881120" to 9L,
        "D8005AC2A8F0" to 1L,
        "F600BC2D8F" to 0L,
        "9C005AC2F8F0" to 0L,
        "9C0141080250320F1802104A08" to 1L)
        .runTest { (packetTransmission, expectedResult) ->
            val result = calculateResultFromAllBitsPackets(packetTransmission)

            assertThat(result).containsOnly(expectedResult)
        }

    @Nested
    inner class PacketDecodingTests {
        @Test
        fun `should decode literal packet`() {
            val literalPacket = "D2FE28"

            val result = decodePackets(literalPacket)

            assertThat(result).containsOnly(LiteralPacket(6, 2021))
        }

        @Test
        fun `should decode operator packet using the number of packets data length`() {
            val operatorPacket = "38006F45291200"

            val result = decodePackets(operatorPacket)

            assertThat(result).containsOnly(OperatorPacket(1, OperatorPacketType.LESS_THAN, listOf(LiteralPacket(6, 10), LiteralPacket(2, 20))))
        }

        @Test
        fun `should decode operator packet using the length of data containing subpackets`() {
            val operatorPacket = "EE00D40C823060"

            val result = decodePackets(operatorPacket)

            assertThat(result).containsOnly(
                OperatorPacket(7, OperatorPacketType.MAXIMUM, listOf(
                    LiteralPacket(2, 1),
                    LiteralPacket(4, 2),
                    LiteralPacket(1, 3))))
        }
    }
}
