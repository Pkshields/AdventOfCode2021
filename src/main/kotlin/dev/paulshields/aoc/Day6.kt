/**
 * Day 6: Lanternfish
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.readFileAsString

fun main() {
    println(" ** Day 6: Lanternfish ** \n")

    val startingLanternfish = readFileAsString("/Day6Lanternfish.txt")
        .split(",")
        .mapNotNull { it.toIntOrNull() }

    var fishCount = simulateLanternfishDays(startingLanternfish, 80)

    println("There are $fishCount alive after 80 days")

    fishCount = simulateLanternfishDays(startingLanternfish, 256)

    println("There are $fishCount alive after 256 days")
}

fun simulateLanternfishDays(startingFish: List<Int>, daysToSimulate: Int): Long {
    var fishCount = startingFish.size.toLong()
    val timeline = MutableList(daysToSimulate + 10) { 0L }
    startingFish.forEach { timeline[it] = timeline[it] + 1 }

    (0 until daysToSimulate).forEach {
        fishCount += timeline[it]
        timeline[it + 9] += timeline[it] // Newly created fish
        timeline[it + 7] += timeline[it] // Old fish, back on the list
    }

    return fishCount
}
