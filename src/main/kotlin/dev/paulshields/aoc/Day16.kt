/**
 * Day 16: Packet Decoder
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.readFileAsString

fun main() {
    println(" ** Day 16: Packet Decoder ** \n")

    val packetTransmission = readFileAsString("/Day16BITSTransmission.txt")

    val packetVersionsSum = sumAllVersionNumbersInBitsPackets(packetTransmission)
    println("Sum of all packet versions is $packetVersionsSum")

    val resultFromBitsPacket = calculateResultFromAllBitsPackets(packetTransmission).first()
    println("The final calcualted result from the BITS packets is $resultFromBitsPacket")
}

fun sumAllVersionNumbersInBitsPackets(packetTransmissionAsHex: String) =
    sumAllVersionNumbersInBitsPackets(decodePackets(packetTransmissionAsHex))

private fun sumAllVersionNumbersInBitsPackets(packetTransmission: List<Packet>): Int =
    packetTransmission.sumOf {
        when (it) {
            is OperatorPacket -> sumAllVersionNumbersInBitsPackets(it.subPackets) + it.packetVersion
            is LiteralPacket -> it.packetVersion
            else -> 0
        }
    }

fun calculateResultFromAllBitsPackets(packetTransmissionAsHex: String) = calculateResultFromAllBitsPackets(decodePackets(packetTransmissionAsHex))

private fun calculateResultFromAllBitsPackets(packetTransmission: List<Packet>) =
    packetTransmission.filterIsInstance<OperatorPacket>().map(::calculateResultFromBitsPacket)

private fun calculateResultFromBitsPacket(packet: OperatorPacket): Long {
    val subPacketValues = packet.subPackets.mapNotNull {
        when (it) {
            is OperatorPacket -> calculateResultFromBitsPacket(it)
            is LiteralPacket -> it.literal
            else -> null
        }
    }

    return when (packet.operatorPacketType) {
        OperatorPacketType.SUM -> subPacketValues.sum()
        OperatorPacketType.PRODUCT -> subPacketValues.fold(1) { accumulator, value -> accumulator * value }
        OperatorPacketType.MINIMUM -> subPacketValues.minOrNull() ?: 0
        OperatorPacketType.MAXIMUM -> subPacketValues.maxOrNull() ?: 0
        OperatorPacketType.GREATER_THAN -> if (subPacketValues[0] > subPacketValues[1]) 1 else 0
        OperatorPacketType.LESS_THAN -> if (subPacketValues[0] < subPacketValues[1]) 1 else 0
        OperatorPacketType.EQUAL_TO -> if (subPacketValues[0] == subPacketValues[1]) 1 else 0
    }
}

fun decodePackets(packetTransmissionAsHex: String) = decodePackets(binaryBufferFromHex(packetTransmissionAsHex))

private fun decodePackets(packetTransmission: BinaryBuffer): List<Packet> {
    val packets = mutableListOf<Packet>()
    while (!packetTransmission.isEmpty()) {
        packets.add(decodePacket(packetTransmission))
    }
    return packets
}

private fun decodePacket(packetTransmission: BinaryBuffer): Packet {
    val packetVersion = packetTransmission.takeInt(3)
    val packetType = packetTransmission.takeInt(3)

    return when (packetType) {
        4 -> decodeLiteralPacket(packetVersion, packetTransmission)
        else -> decodeOperatorPacket(packetVersion, packetType, packetTransmission)
    }
}

private fun decodeLiteralPacket(packetVersion: Int, packetTransmission: BinaryBuffer): LiteralPacket {
    val halfBytes = mutableListOf<String>()

    do {
        val continueBit = packetTransmission.takeBoolean()
        halfBytes.add(packetTransmission.takeBits(4))
    } while (continueBit)

    val literal = halfBytes.joinToString("").toLong(2)
    return LiteralPacket(packetVersion, literal)
}

private fun decodeOperatorPacket(packetVersion: Int, packetType: Int, packetTransmission: BinaryBuffer): OperatorPacket {
    val lengthTypeId = packetTransmission.takeBits(1)
    val subPackets = when (lengthTypeId) {
        "0" -> decodeOperatorSubPacketsUsingLengthOfPackets(packetTransmission)
        else -> decodeOperatorSubPacketsUsingNumberOfPackets(packetTransmission)
    }
    return OperatorPacket(packetVersion, OperatorPacketType.fromInt(packetType), subPackets)
}

private fun decodeOperatorSubPacketsUsingLengthOfPackets(packetTransmission: BinaryBuffer): List<Packet> {
    val lengthOfSubPackets = packetTransmission.takeInt(15)
    val subPacketBuffer = BinaryBuffer(packetTransmission.takeBits(lengthOfSubPackets))
    return decodePackets(subPacketBuffer)
}

private fun decodeOperatorSubPacketsUsingNumberOfPackets(packetTransmission: BinaryBuffer): List<Packet> {
    val numberOfSubPackets = packetTransmission.takeInt(11)
    return (0 until numberOfSubPackets).map { decodePacket(packetTransmission) }
}

open class Packet(open val packetVersion: Int)
data class LiteralPacket(override val packetVersion: Int, val literal: Long) : Packet(packetVersion)
data class OperatorPacket(
    override val packetVersion: Int,
    val operatorPacketType: OperatorPacketType,
    val subPackets: List<Packet>) : Packet(packetVersion)

enum class OperatorPacketType(val operatorType: Int) {
    SUM(0),
    PRODUCT(1),
    MINIMUM(2),
    MAXIMUM(3),
    GREATER_THAN(5),
    LESS_THAN(6),
    EQUAL_TO(7);

    companion object {
        fun fromInt(value: Int) = values().first { it.operatorType == value }
    }
}

private class BinaryBuffer(private val binaryString: String) {
    private var bufferPosition = 0

    fun takeBits(bits: Int) =
        binaryString
            .substring(bufferPosition, bufferPosition + bits)
            .also { bufferPosition += bits }

    fun takeInt(bits: Int) =
        binaryString
            .substring(bufferPosition, bufferPosition + bits)
            .toInt(2)
            .also { bufferPosition += bits }

    fun takeBoolean() = binaryString[bufferPosition++] == '1'

    fun isEmpty() = !binaryString.substring(bufferPosition).contains('1')
}

private fun binaryBufferFromHex(hexString: String) = BinaryBuffer(parseHex(hexString))

private fun parseHex(packetTransmissionAsHex: String) =
    packetTransmissionAsHex
        .toCharArray()
        .joinToString("") { it.digitToInt(16).toString(2).padStart(4, '0') }
