/**
 * Day 3: Binary Diagnostic
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.readFileAsStringList

fun main() {
    println(" ** Day 3: Binary Diagnostic ** \n")

    val diagnosticsReport = readFileAsStringList("/Day3DiagnosticsReport.txt")

    val powerConsumption = decodePowerConsumptionFromDiagnosticsReport(diagnosticsReport)
    println("The power consumption provided by the diagnostics report is $powerConsumption")

    val lifeSupportRating = decodeLifeSupportRatingFromDiagnosticsReport(diagnosticsReport)
    println("The life support rating provided by the diagnostics report is $lifeSupportRating")
}

fun decodePowerConsumptionFromDiagnosticsReport(report: List<String>): Int {
    val bitsGroupedByIndex = groupBitsInReportByIndex(report)

    val gammaRate = findMostCommonBitsInReportByIndex(bitsGroupedByIndex)
        .joinToString("")
        .toInt(2)
    val epsilonRate = findLeastCommonBitsInReportByIndex(bitsGroupedByIndex)
        .joinToString("")
        .toInt(2)

    return gammaRate * epsilonRate
}

fun decodeLifeSupportRatingFromDiagnosticsReport(report: List<String>): Int {
    val oxygenGeneratorRating = findNumberMatchingBitCriteria(report, ::findMostCommonBitsInReportByIndex)
    val co2ScrubberRating = findNumberMatchingBitCriteria(report, ::findLeastCommonBitsInReportByIndex)

    return oxygenGeneratorRating * co2ScrubberRating
}

private fun groupBitsInReportByIndex(report: List<String>) =
    report.map(::toIndexedListOfChars)
        .flatten()
        .groupBy(keySelector = { it.first }, valueTransform = { it.second })
        .map { it.value }

private fun toIndexedListOfChars(input: String) =
    input.toList().mapIndexed { index, char -> Pair(index, char) }

private fun findMostCommonBitsInReportByIndex(bitsGroupedByIndex: List<List<Char>>) =
    bitsGroupedByIndex.map(::getMostCommonChar)

private fun findLeastCommonBitsInReportByIndex(bitsGroupedByIndex: List<List<Char>>) =
    bitsGroupedByIndex.map(::getLeastCommonChar)

private fun getMostCommonChar(input: List<Char>) =
    input.sortedByDescending { it }.groupBy { it }.maxByOrNull { it.value.size }?.key ?: '1'

private fun getLeastCommonChar(input: List<Char>) =
    input.sortedBy { it }.groupBy { it }.minByOrNull { it.value.size }?.key ?: '0'

fun findNumberMatchingBitCriteria(report: List<String>, matchingBitCriteriaGenerator: (List<List<Char>>) -> List<Char>): Int {
    var index = 0
    var filteredReport = report

    while (filteredReport.size > 1) {
        val bitsGroupedByIndex = groupBitsInReportByIndex(filteredReport)
        val bitToMatch = matchingBitCriteriaGenerator(bitsGroupedByIndex)[index]
        filteredReport = filteredReport.filter { it[index] == bitToMatch }
        ++index
    }

    return filteredReport.first().toInt(2)
}
