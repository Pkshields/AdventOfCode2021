/**
 * Day 11: Dumbo Octopus
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.Point
import dev.paulshields.aoc.common.readFileAsString

fun main() {
    println(" ** Day 11: Dumbo Octopus ** \n")

    val dumboOctopuses = readFileAsString("/Day11DumboOctopuses.txt")

    val numberOfFlashes = countNumberOfFlashesAfterSteps(dumboOctopuses, 100)
    println("The number of Dumbo Octopus flashes after 100 steps is $numberOfFlashes")

    val stepNumber = calculateStepNumberWhenEveryOctopusIsFlashing(dumboOctopuses)
    println("The step where every octopus is flashing is $stepNumber")
}

fun countNumberOfFlashesAfterSteps(rawOctopusEnergyGrid: String, numberOfSteps: Int): Long {
    var flashCount = 0L
    var octopusEnergyGrid = parseOctopusEnergyGrid(rawOctopusEnergyGrid)

    (1..numberOfSteps).forEach { _ ->
        octopusEnergyGrid = iterateOctopusesOnce(octopusEnergyGrid)
        flashCount += countNumberOfFlashes(octopusEnergyGrid)
    }

    return flashCount
}

fun calculateStepNumberWhenEveryOctopusIsFlashing(rawOctopusEnergyGrid: String): Int {
    var runs = 0
    var octopusEnergyGrid = parseOctopusEnergyGrid(rawOctopusEnergyGrid)

    do {
        ++runs
        octopusEnergyGrid = iterateOctopusesOnce(octopusEnergyGrid)
    } while (countNumberOfFlashes(octopusEnergyGrid) != octopusEnergyGrid.size)

    return runs
}

private fun parseOctopusEnergyGrid(octopusesStartingEnergy: String) =
    octopusesStartingEnergy
        .lines()
        .flatMapIndexed { xPos, line ->
            line.toCharArray().mapIndexed { yPos, char -> Pair(Point(xPos, yPos), char.digitToInt()) }
        }.toMap()

private fun iterateOctopusesOnce(octopusEnergyGrid: Map<Point, Int>): Map<Point, Int> {
    val octopuses = octopusEnergyGrid.toMutableMap()
    val flashedOctopuses = mutableListOf<Point>()

    octopuses.keys.forEach { octopuses.incrementValue(it) }
    var flashingOctopuses = octopuses.filter { it.value > 9 }

    while (flashingOctopuses.isNotEmpty()) {
        flashingOctopuses
            .flatMap { getAllAdjacentPoints(it.key) }
            .forEach { octopuses.incrementValue(it) }

        flashedOctopuses.addAll(flashingOctopuses.keys)
        flashingOctopuses = octopuses.filter { it.value > 9 && !flashedOctopuses.contains(it.key) }
    }

    flashedOctopuses.forEach { octopuses[it] = 0 }

    return octopuses.toMap()
}

private fun countNumberOfFlashes(octopusEnergyGrid: Map<Point, Int>) = octopusEnergyGrid.values.count { it == 0 }

private fun getAllAdjacentPoints(point: Point) = listOf(
    Point(point.x - 1, point.y - 1),
    Point(point.x - 1, point.y),
    Point(point.x - 1, point.y + 1),
    Point(point.x, point.y - 1),
    Point(point.x, point.y + 1),
    Point(point.x + 1, point.y - 1),
    Point(point.x + 1, point.y),
    Point(point.x + 1, point.y + 1))

private fun MutableMap<Point, Int>.incrementValue(key: Point) {
    if (contains(key)) {
        this[key] = this[key]?.plus(1) ?: 0
    }
}
