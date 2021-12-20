/**
 * Day 17: Trick Shot
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.Point
import dev.paulshields.aoc.common.extractGroups
import dev.paulshields.aoc.common.readFileAsString
import kotlin.math.abs

fun main() {
    println(" ** Day 17: Trick Shot ** \n")

    val oceanTrenchArea = readFileAsString("/Day17OceanTrenchArea.txt").let {
        val oceanTrenchAreaRegex = Regex("target area: x=([-\\d]+)..([-\\d]+), y=([-\\d]+)..([-\\d]+)")
        val parts = oceanTrenchAreaRegex.extractGroups(it).map { it.toInt() }
        val xRange = increasingRangeBetween(parts[0], parts[1])
        val yRange = increasingRangeBetween(parts[2], parts[3])
        TargetArea(xRange, yRange)
    }

    val maxHeight = findHeightAtVelocityWhichLandsInTrench(oceanTrenchArea)
    println("The max height, at thus the most stylish velocity, is $maxHeight")

    val allValidVelocities = findAllInitialVelocitiesWhichLandInTrench(oceanTrenchArea)
    println("The total number of possible velocities is ${allValidVelocities.size}")
}

fun findHeightAtVelocityWhichLandsInTrench(targetArea: TargetArea) =
    findAllInitialVelocitiesWhichLandInTrench(targetArea)
        .map { it.maxHeight }
        .maxByOrNull { it } ?: 0

fun findAllInitialVelocitiesWhichLandInTrench(targetArea: TargetArea) =
    possibleXVelocities(targetArea)
        .flatMap { xVelocity ->
            possibleYVelocities(targetArea).mapNotNull { collectDataIfLandsInTargetArea(Point(xVelocity, it), targetArea) }
        }

private fun possibleXVelocities(targetArea: TargetArea) = (0..targetArea.right)

private fun possibleYVelocities(targetArea: TargetArea) = (targetArea.bottom..maxValidYVelocity(targetArea))

private fun maxValidYVelocity(targetArea: TargetArea) =
    if (targetArea.top > 0 && targetArea.bottom < 0) {
        targetArea.top + abs(targetArea.bottom)
    } else if (targetArea.bottom < 0) {
        abs(targetArea.bottom)
    } else {
        targetArea.top
    }

private fun collectDataIfLandsInTargetArea(initialVelocity: Point, targetArea: TargetArea): VelocityMetadata? {
    var position = Point(0, 0)
    var velocity = initialVelocity
    var maxHeight = 0

    while (trajectoryIsStillValid(position, targetArea)) {
        position += velocity
        velocity = calculateNewVelocity(velocity)

        maxHeight = if (position.y > maxHeight) position.y else maxHeight

        if (targetArea.isWithinArea(position)) return VelocityMetadata(initialVelocity, maxHeight)
    }

     return null
}

fun calculateNewVelocity(velocity: Point): Point {
    val xVelocity = if (velocity.x > 0) {
        velocity.x - 1
    } else if (velocity.x < 0) {
        velocity.x + 1
    } else 0

    return Point(xVelocity, velocity.y - 1)
}

private fun trajectoryIsStillValid(position: Point, targetArea: TargetArea) =
    position.x < targetArea.right && position.y > targetArea.bottom

data class TargetArea(val xRange: IntProgression, val yRange: IntProgression) {
    val top = if (yRange.first > yRange.last) yRange.first else yRange.last
    val left = xRange.first
    val bottom = if (yRange.first < yRange.last) yRange.first else yRange.last
    val right = xRange.last

    fun isWithinArea(position: Point) = xRange.contains(position.x) && yRange.contains(position.y)
}

data class VelocityMetadata(val initialVelocity: Point, val maxHeight: Int)

private fun increasingRangeBetween(start: Int, end: Int) = if (start < end) (start..end) else (end..start)
