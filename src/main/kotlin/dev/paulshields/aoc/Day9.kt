/**
 * Day 9: Smoke Basin
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.readFileAsString

fun main() {
    println(" ** Day 9: Smoke Basin ** \n")

    val heightMap = readFileAsString("/Day9HeightMap.txt")

    val riskLevel = calculateRiskLevelOfHeightMap(heightMap)
    println("The combined risk level for this height map is $riskLevel")

    val result = findSizeOfBasins(heightMap)
        .sortedDescending()
        .take(3)
        .fold(1) { accumulator, basinSize -> accumulator * basinSize}

    println("The product of the largest 3 basin sizes is $result")
}

fun calculateRiskLevelOfHeightMap(rawHeightMap: String): Int {
    val heightMap = parseHeightMap(rawHeightMap)
    return heightMap.filter { it.isLowPoint }.map { it.location }.sumOf { it + 1 }
}

fun findSizeOfBasins(rawHeightMap: String): List<Int> {
    val heightMap = parseHeightMap(rawHeightMap)
    return heightMap
        .filter { it.isLowPoint }
        .map { calculateSizeOfBasin(it, heightMap) }
}

fun calculateSizeOfBasin(centerLocation: HeightMapLocation, heightMap: List<HeightMapLocation>): Int {
    val modifiableHeightMap = heightMap.toMutableList().apply { remove(centerLocation) }
    val locationsInBasin = mutableListOf(centerLocation)

    var i = -1
    while (++i < locationsInBasin.size) {
        val neighbours = findNeighboursWithinBasin(locationsInBasin[i], modifiableHeightMap)
        locationsInBasin.uniqueAddAll(neighbours)
        modifiableHeightMap.removeAll(neighbours)
    }

    return locationsInBasin.size
}

private fun findNeighboursWithinBasin(location: HeightMapLocation, heightMap: List<HeightMapLocation>): List<HeightMapLocation> {
    return heightMap.filter {
            it.x == location.x - 1 && it.y == location.y ||
                    it.x == location.x + 1 && it.y == location.y ||
                    it.x == location.x && it.y == location.y - 1 ||
                    it.x == location.x && it.y == location.y + 1
        }.filter { it.location != 9 }
}

private fun parseHeightMap(rawHeightMap: String): List<HeightMapLocation> {
    val heightMap = rawHeightMap
        .lines()
        .map { it.toCharArray().map { it.digitToInt() } }

    return heightMap.mapIndexed { yIndex, line ->
        line.mapIndexed { xIndex, location ->
            val left = if (xIndex > 0) location < heightMap[yIndex][xIndex - 1] else true
            val right = if (xIndex < line.size - 1) location < heightMap[yIndex][xIndex + 1] else true
            val top = if (yIndex > 0) location < heightMap[yIndex - 1][xIndex] else true
            val bottom = if (yIndex < heightMap.size - 1) location < heightMap[yIndex + 1][xIndex] else true

            HeightMapLocation(location, xIndex, yIndex, left && right && top && bottom)
        }
    }.flatten()
}

data class HeightMapLocation(val location: Int, val x: Int, val y: Int, val isLowPoint: Boolean)

fun <T> MutableList<T>.uniqueAddAll(listToAdd: List<T>) = addAll(listToAdd.filter { !this.contains(it) })
