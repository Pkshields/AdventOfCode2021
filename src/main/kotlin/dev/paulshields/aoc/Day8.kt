/**
 * Day 8: Seven Segment Search
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.readFileAsStringList

fun main() {
    println(" ** Day 8: Seven Segment Search ** \n")

    val rawCorruptedDisplays = readFileAsStringList("/Day8CorruptedDisplays.txt")

    val corruptedOutputs = rawCorruptedDisplays.map { it.split(" | ")[1] }
    val easyToFindDigitsCount = countEasyToFindDigits(corruptedOutputs)
    println("There are $easyToFindDigitsCount instances of 1, 4, 7 or 8 in the corrupted outputs.")

    val corruptedDisplays = rawCorruptedDisplays.map {
        val splitData = it.split(" | ")
        val uniqueSignalPatterns = splitData[0].split(" ")
        CorruptedDisplay(uniqueSignalPatterns, splitData[1])
    }

    val sumOfDecodedValues = sumAllDecodedOutputValues(corruptedDisplays)
    println("The sum of all decoded output values from the corrupted displays is $sumOfDecodedValues")
}

fun countEasyToFindDigits(outputValues: List<String>) =
    outputValues
        .flatMap { it.trim().split(" ") }
        .count { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }

fun sumAllDecodedOutputValues(corruptedDisplays: List<CorruptedDisplay>) =
    corruptedDisplays.sumOf { it.decodedOutputValue }

data class CorruptedDisplay(val uniqueSignalPatterns: List<String>, val fourDigitOutputValue: String) {
    val matchedDigitPatterns = findMatchingDigitPatterns()
    val decodedOutputValue = decodeOutputValue()

    private fun findMatchingDigitPatterns(): Map<String, Int> {
        val patternsByLength = uniqueSignalPatterns.groupBy { it.length }
        val one = patternsByLength[2].firstOrEmpty()
        val four = patternsByLength[4].firstOrEmpty()
        val seven = patternsByLength[3].firstOrEmpty()
        val eight = patternsByLength[7].firstOrEmpty()

        val six = patternsByLength[6].firstOrEmpty { !it.containsAllChars(one) }
        val five = patternsByLength[5].firstOrEmpty { six.containsAllChars(it) }
        val three = patternsByLength[5].firstOrEmpty { it.containsAllChars(one) }
        val two = patternsByLength[5].firstOrEmpty { it != five && it != three }
        val nine = patternsByLength[6].firstOrEmpty { it.containsAllChars(three) }
        val zero = patternsByLength[6].firstOrEmpty { it != six && it != nine }

        return mapOf(
            zero to 0,
            one to 1,
            two to 2,
            three to 3,
            four to 4,
            five to 5,
            six to 6,
            seven to 7,
            eight to 8,
            nine to 9)
    }

    private fun decodeOutputValue() =
        fourDigitOutputValue
            .split(" ")
            .map { digit -> matchedDigitPatterns
                .filterKeys { digit.length == it.length && digit.containsAllChars(it) }
                .values.first() }
            .joinToString("")
            .toIntOrNull() ?: 0
}

private fun String.containsAllChars(chars: String) = chars.toCharArray().all { this.contains(it) }
private fun List<String>?.firstOrEmpty() = this?.first() ?: ""
private fun List<String>?.firstOrEmpty(predicate: (String) -> Boolean) = this?.first(predicate) ?: ""
