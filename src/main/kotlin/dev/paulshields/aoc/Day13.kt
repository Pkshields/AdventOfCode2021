/**
 * Day 13: Transparent Origami
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.Point
import dev.paulshields.aoc.common.extractGroups
import dev.paulshields.aoc.common.readFileAsString

fun main() {
    println(" ** Day 13: Transparent Origami ** \n")

    val pageOne = readFileAsString("/Day13ManualPageOne.txt")

    val numberOfDotsAfterOneFold = countVisibleDotsAfterFold(pageOne, 1)
    println("Number of dots still visible after one fold is $numberOfDotsAfterOneFold")

    val foldedPaper = printPaperAfterFolds(pageOne)
    println("The final folded paper:\n")
    println(foldedPaper)
}

fun countVisibleDotsAfterFold(transparentPaper: String, numberOfFolds: Int? = null) =
    foldPaper(transparentPaper, numberOfFolds).size

fun printPaperAfterFolds(transparentPaper: String): String {
    val dotsAfterFolds = foldPaper(transparentPaper)

    val width = dotsAfterFolds.maxByOrNull { it.x }?.let { it.x + 1 } ?: 0
    val height = dotsAfterFolds.maxByOrNull { it.y }?.let { it.y + 1 } ?: 0

    val foldedPaper = MutableList(height) { MutableList(width) { "." } }
    dotsAfterFolds.forEach { foldedPaper[it.y][it.x] = "#" }

    return foldedPaper.joinToString("\n") { it.joinToString("") }
}

private fun foldPaper(transparentPaper: String, numberOfFolds: Int? = null): List<Point> {
    var (dots, folds) = parseTransparentPaperSheet(transparentPaper)
    val foldsToPerform = folds.take(numberOfFolds ?: folds.size)

    foldsToPerform.forEach { fold ->
        dots = dots.map {
            when (fold.direction) {
                "x" -> foldDotLeft(it, fold.line)
                "y" -> foldDotUp(it, fold.line)
                else -> it
            }
        }.distinct()
    }

    return dots
}

private fun foldDotLeft(dot: Point, foldLine: Int) =
    if (dot.x > foldLine) Point(foldLine - (dot.x - foldLine), dot.y) else dot

private fun foldDotUp(dot: Point, foldLine: Int) =
    if (dot.y > foldLine) Point(dot.x, foldLine - (dot.y - foldLine)) else dot

private fun parseTransparentPaperSheet(sheet: String): Pair<List<Point>, List<Fold>> {
    val foldRegex = Regex("fold along ([xy])=(\\d+)")

    val (dotsSheet, foldInstructions) = sheet.split("\n\n")

    val dots = dotsSheet.lines().map {
        Point.fromList(it.split(",").map { it.toInt() })
    }

    val folds = foldInstructions
        .lines()
        .map { foldRegex.extractGroups(it) }
        .map { (direction, line) -> Fold(line.toInt(), direction) }

    return dots to folds
}

data class Fold(val line: Int, val direction: String)
