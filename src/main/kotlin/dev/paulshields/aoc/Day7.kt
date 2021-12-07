/**
 * Day 7: The Treachery of Whales
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.readFileAsString
import kotlin.math.abs

fun main() {
    println(" ** Day 7: The Treachery of Whales ** \n")

    val crabPositions = readFileAsString("/Day7CrabPositions.txt").split(",").mapNotNull { it.toIntOrNull() }
    val naiveFuelUsed = naivelyCalculateFuelUsageToAlignCrabs(crabPositions)

    println("Naively, the crabs would have to use a combined total of $naiveFuelUsed fuel to line up.")

    val actualFuelUsed = correctlyCalculateFuelUsageToAlignCrabs(crabPositions)

    println("The crabs would actually have to use a combined total of $actualFuelUsed fuel to line up.")
}

fun naivelyCalculateFuelUsageToAlignCrabs(crabPositions: List<Int>): Int {
    val medianPosition = crabPositions.sorted()[crabPositions.size / 2]
    return crabPositions.sumOf { abs(it - medianPosition) }
}

fun correctlyCalculateFuelUsageToAlignCrabs(crabPositions: List<Int>): Int {
    val lastPossiblePosition = crabPositions.sorted().maxOrNull() ?: 0
    return (0 until lastPossiblePosition).map { possiblePosition ->
        crabPositions.sumOf {
            val distanceToTravel = abs(it - possiblePosition)
            sumNumbersUpTo(distanceToTravel)
        }
    }.sorted().minOrNull() ?: 0
}

private fun sumNumbersUpTo(number: Int) = (number * (number + 1)) / 2
