/**
 * Day 1: Sonar Sweep
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.readFileAsStringList

fun main() {
    println(" ** Day 1: Sonar Sweep ** \n")

    val depths = readFileAsStringList("/Day1Depths.txt").mapNotNull { it.toIntOrNull() }

    val numDepthIncreases = countNumberOfDepthIncreases(depths)
    println("There are $numDepthIncreases depth measurements that are larger than the previous measurement.")

    val slidingWindowNumDepthIncreases = countNumberOfDepthIncreasesUsingSlidingWindow(depths)
    println("Using a 3-measurement sliding window, there are $slidingWindowNumDepthIncreases depths that are larger than the previous.")
}

fun countNumberOfDepthIncreases(depths: List<Int>) =
    depths.zipWithNext().count { it.second > it.first }

fun countNumberOfDepthIncreasesUsingSlidingWindow(depths: List<Int>) =
    depths.zipWithNextTwo()
        .map { it.first + it.second + it.third }
        .zipWithNext()
        .count { it.second > it.first }

private fun List<Int>.zipWithNextTwo() =
    this.zipWithNext()
        .zipWithNext { one, two -> Triple(one.first, one.second, two.second) }
