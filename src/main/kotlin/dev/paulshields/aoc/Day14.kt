/**
 * Day 14: Extended Polymerization
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.addToValue
import dev.paulshields.aoc.common.incrementValue
import dev.paulshields.aoc.common.readFileAsString
import dev.paulshields.aoc.common.subtractFromValue

fun main() {
    println(" ** Day 14: Extended Polymerization ** \n")

    val polymerInstructions = readFileAsString("/Day14PolymerInstructions.txt")

    val partOne = differenceInCountOfMostAndLeastElementsInPolymerAfterIteration(polymerInstructions, 10)
    println("The difference in quantity between the most and least used elements in the polymer for part 1 is $partOne.")

    val partTwo = differenceInCountOfMostAndLeastElementsInPolymerAfterIteration(polymerInstructions, 40)
    println("The difference in quantity between the most and least used elements in the polymer for part 2 is $partTwo.")
}

fun differenceInCountOfMostAndLeastElementsInPolymerAfterIteration(polymerInstructions: String, iterationCount: Int): Long {
    val pairsCount = countPolymerPairsInPolymerAfterIteration(polymerInstructions, iterationCount)
    val individualElementsCountInPolymer = countIndividualElementsInPolymer(pairsCount, polymerInstructions)

    val countOfMostElements = individualElementsCountInPolymer.maxByOrNull { it.value }?.value ?: 0
    val countOfLeastElements = individualElementsCountInPolymer.minByOrNull { it.value }?.value ?: 0

    return countOfMostElements - countOfLeastElements
}

private fun countIndividualElementsInPolymer(polymerPairsCount: Map<String, Long>, polymerTemplate: String): Map<Char, Long> {
    val individualElementsCountInPolymer = polymerPairsCount
        .map { it.key[1] to it.value }
        .groupBy(keySelector = { it.first }, valueTransform = { it.second })
        .map { it.key to it.value.sum() }
        .toMap().toMutableMap()

    individualElementsCountInPolymer.incrementValue(polymerTemplate.first())

    return individualElementsCountInPolymer.toMap()
}

private fun countPolymerPairsInPolymerAfterIteration(polymerInstructions: String, iterationCount: Int): Map<String, Long> {
    val (polymerTemplate, pairInsertionRules) = parsePolymerInstructions(polymerInstructions)

    var elementPairsCount = pairInsertionRules
        .map { it.key }
        .associateWith { pair -> polymerTemplate.windowed(2, 1).count { it == pair }.toLong() }

    (0 until iterationCount).forEach { _ ->
        val nextElementPairsCount = elementPairsCount.toMutableMap()

        elementPairsCount.forEach { (elementPair, count) ->
            if (count == 0L) return@forEach

            nextElementPairsCount.subtractFromValue(elementPair, count)
            pairInsertionRules[elementPair]?.forEach {
                nextElementPairsCount.addToValue(it, count)
            }
        }

        elementPairsCount = nextElementPairsCount.toMap()
    }

    return elementPairsCount
}

private fun parsePolymerInstructions(polymerInstructions: String): Pair<String, Map<String, List<String>>> {
    val (polymerTemplate, rawPairInsertionRules) = polymerInstructions.split("\n\n")

    val pairInsertionRules = rawPairInsertionRules
        .lines()
        .associate {
            val (pair, insertionElement) = it.split(" -> ")
            pair to calculateInsertionReplacementPairs(pair, insertionElement.first())
        }

    return polymerTemplate to pairInsertionRules
}

private fun calculateInsertionReplacementPairs(pair: String, insertionElement: Char) =
    listOf("${pair[0]}$insertionElement", "$insertionElement${pair[1]}")
