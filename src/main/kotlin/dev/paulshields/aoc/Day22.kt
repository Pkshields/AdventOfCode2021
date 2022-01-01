/**
 * Day 22: Reactor Reboot
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.extractGroups
import dev.paulshields.aoc.common.readFileAsStringList
import kotlin.math.max
import kotlin.math.min

fun main() {
    println(" ** Day 22: Reactor Reboot ** \n")

    val rebootInstructions = readFileAsStringList("/Day22RebootInstructions.txt")

    val onCubeCount = runRebootInstructions(rebootInstructions, -50..50)
    println("There are $onCubeCount cubes turned on after the reboot instructions (within range) are executed.")

    val fullOnCubeCount = runRebootInstructions(rebootInstructions)
    println("There are $fullOnCubeCount cubes turned on when the complete list of instructions is executed.")
}

/**
 * This uses the concept of negative volume to handle the large amount of cubes being worked with here.
 * If a 9x9x9 range overlaps with another 3x3x3 range by 1 cube, it will add both 3x3x3 ranges to the areas list,
 * plus a "negative range" of 1, to give the correct number of on cubes count of 53 (27+27-1).
 */
fun runRebootInstructions(rawInstructions: List<String>, areaLimit: IntRange? = null): Long {
    val instructions = areaLimit?.let {
        val parsedInstructions = parseInstructions(rawInstructions)
        limitInstructionRangeTo(it, parsedInstructions)
    } ?: parseInstructions(rawInstructions)
    val areas = mutableListOf<Region3D>()

    instructions.forEach { instruction ->
        areas.addAll(areas.mapNotNull { it.overlappingRegion(instruction.region)?.toInvertedRegion() })

        if (instruction.action == RebootAction.ON) {
            areas.add(instruction.region)
        }
    }

    return areas.sumOf { it.size }
}

private fun parseInstructions(instructions: List<String>): List<RebootInstruction> {
    val instructionParser = Regex("(on|off) x=([-\\d]+)..([-\\d]+),y=([-\\d]+)..([-\\d]+),z=([-\\d]+)..([-\\d]+)")

    return instructions.map {
        val parsedInstruction = instructionParser.extractGroups(it)
        val action = if (parsedInstruction.first() == "on") RebootAction.ON else RebootAction.OFF
        val xRange = parsedInstruction[1].toInt()..parsedInstruction[2].toInt()
        val yRange = parsedInstruction[3].toInt()..parsedInstruction[4].toInt()
        val zRange = parsedInstruction[5].toInt()..parsedInstruction[6].toInt()
        RebootInstruction(action, xRange, yRange, zRange)
    }
}

private fun limitInstructionRangeTo(areaLimit: IntRange, instructions: List<RebootInstruction>): List<RebootInstruction> {
    return instructions.mapNotNull {
        val xRangeIntersection = areaLimit.intersectRange(it.region.xRange)
        val yRangeIntersection = areaLimit.intersectRange(it.region.yRange)
        val zRangeIntersection = areaLimit.intersectRange(it.region.zRange)

        if (xRangeIntersection != null && yRangeIntersection != null && zRangeIntersection != null) {
            RebootInstruction(it.action, xRangeIntersection, yRangeIntersection, zRangeIntersection)
        } else null
    }
}

class RebootInstruction(val action: RebootAction, xRange: IntRange, yRange: IntRange, zRange: IntRange) {
    val region = Region3D(xRange, yRange, zRange)
}

open class Region3D(val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {
    open val size by lazy { xRange.count().toLong() * yRange.count().toLong() * zRange.count().toLong() }

    open fun overlappingRegion(region: Region3D): Region3D? {
        val xOverlap = xRange.intersectRange(region.xRange)
        val yOverlap = yRange.intersectRange(region.yRange)
        val zOverlap = zRange.intersectRange(region.zRange)

        return if (xOverlap != null && yOverlap != null && zOverlap != null) {
            Region3D(xOverlap, yOverlap, zOverlap)
        } else null
    }

    open fun toInvertedRegion(): Region3D = NegativeRegion3D(xRange, yRange, zRange)
}

class NegativeRegion3D(xRange: IntRange, yRange: IntRange, zRange: IntRange) : Region3D(xRange, yRange, zRange) {
    override val size by lazy { super.size * -1 }

    override fun overlappingRegion(region: Region3D) = super.overlappingRegion(region)?.toInvertedRegion()
    override fun toInvertedRegion() = Region3D(xRange, yRange, zRange)
}

enum class RebootAction {
    ON, OFF
}

private fun IntRange.intersectRange(other: IntRange): IntRange? {
    val startOfRange = max(this.first, other.first)
    val endOfRange = min(this.last, other.last)

    return if (endOfRange < startOfRange) null else startOfRange..endOfRange
}
