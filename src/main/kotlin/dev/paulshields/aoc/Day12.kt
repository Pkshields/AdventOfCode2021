/**
 * Day 12: Passage Pathing
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.readFileAsString

fun main() {
    println(" ** Day 12: Passage Pathing ** \n")

    val caveConnections = readFileAsString("/Day12CaveConnections.txt")

    val numberOfPaths = countAllPossiblePathsThroughCaveSystem(caveConnections)
    println("The number of possible paths through the system is $numberOfPaths")

    val numberOfPathsWithSingleDuplicatedCage = countAllPossiblePathsThroughCaveSystemWithSingleDuplicatedSmallCaveAllowed(caveConnections)
    println("The number of possible paths through the system using a single duplicated cave is $numberOfPathsWithSingleDuplicatedCage")
}

fun countAllPossiblePathsThroughCaveSystem(caveConnectionData: String): Int {
    val caveConnections = parseCaveConnectionData(caveConnectionData)
    val allPaths = findAllPaths(listOf("start"), caveConnections)
    return allPaths.size
}

fun countAllPossiblePathsThroughCaveSystemWithSingleDuplicatedSmallCaveAllowed(caveConnectionData: String): Int {
    val caveConnections = parseCaveConnectionData(caveConnectionData)
    val allPaths = findAllPaths(listOf("start"), caveConnections, true)
    return allPaths.size
}

private fun findAllPaths(
    pathStart: List<String>,
    connections: List<CaveConnection>,
    duplicatedSmallCaveAllowed: Boolean = false): List<List<String>> {
    val currentCave = pathStart.last()
    if (pathStart.last() == "end") return listOf(pathStart)

    val allFoundPaths = connections
        .filter { it.contains(currentCave) }
        .filter { it.otherEnd(currentCave) != "start" }
        .filter { caveIsAbleToBePassedThrough(pathStart, it, duplicatedSmallCaveAllowed) }
        .map { pathStart.append(it.otherEnd(currentCave)) }
        .flatMap { newPath ->
            val smallCaveHasBeenDuplicated = newPath.last().isLowerCase() && newPath.count { it == newPath.last() } == 2
            findAllPaths(newPath, connections, duplicatedSmallCaveAllowed && !smallCaveHasBeenDuplicated)
        }

    return if (pathStart.size == 1) allFoundPaths.filter { it.last() == "end" } else allFoundPaths
}

private fun caveIsAbleToBePassedThrough(currentPath: List<String>, nextCaveConnection: CaveConnection, duplicatedSmallCaveAllowed: Boolean) =
    nextCaveConnection.otherEnd(currentPath.last()).let {
        if (it.isLowerCase() && !duplicatedSmallCaveAllowed) !currentPath.contains(it) else true
    }

private fun parseCaveConnectionData(caveConnectionData: String) =
    caveConnectionData
        .lines()
        .map { caveConnection ->
            caveConnection.split("-").let { CaveConnection(it[0], it[1]) }
        }

data class CaveConnection(val caveA: String, val caveB: String) {
    fun contains(cave: String) = caveA == cave || caveB == cave
    fun otherEnd(cave: String) = if (caveA == cave) caveB else caveA
}

private fun String.isLowerCase() = this.toCharArray().all { it.isLowerCase() }
private fun <T> List<T>.append(item: T) = this.toMutableList().apply { add(item) }.toList()
