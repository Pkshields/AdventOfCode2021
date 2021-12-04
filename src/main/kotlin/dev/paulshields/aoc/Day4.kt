/**
 * Day 4: Giant Squid
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.readFileAsString

fun main() {
    println(" ** Day 4: Giant Squid ** \n")

    val splitFile = readFileAsString("/Day4BingoBoards.txt")
        .trim()
        .split("\n\n")

    val drawnNumbers = splitFile[0]
        .split(",")
        .mapNotNull { it.toIntOrNull() }

    val boards = splitFile
        .drop(1)
        .map { BingoBoard(it) }

    drawnNumbers.first { drawnNumber ->
        boards.forEach { it.markNumber(drawnNumber) }
        boards.any { it.bingo() }
    }

    val bingoBoardScore = boards.first { it.bingo() }.calculateBoardScore()
    println("The score of the first board that called bingo is $bingoBoardScore")

    val lastWinningBoard = splitFile
        .drop(1)
        .map { RiggedBingoBoard(it) }
        .onEach { it.checkAllDrawnNumbers(drawnNumbers) }
        .maxByOrNull { it.indexWhenBingo }

    println("The score of the board that would win last is ${lastWinningBoard?.calculateBoardScore()}")
}

open class BingoBoard(rawBoard: String) {
    private var board = rawBoard
        .split("\n")
        .map {
            it.trim()
                .replace("  ", " ")
                .split(" ")
                .associate { Pair(it.toInt(), false) }
        }

    private var lastCalledNumber = -1

    fun markNumber(number: Int) {
        lastCalledNumber = number
        board = board.map { it.mapValues { it.key == number || it.value } }
    }

    fun bingo(): Boolean {
        val rowCheck = board.any { it.all { it.value } }
        val columnCheck = board
            .flatMap { it.values.mapIndexed { index, item -> Pair(index, item) } }
            .groupBy(keySelector = { it.first }, valueTransform = { it.second })
            .any { it.value.all { it } }

        return rowCheck || columnCheck
    }

    fun calculateBoardScore() = board.flatMap { it.filter { !it.value }.keys }.sum() * lastCalledNumber
}

class RiggedBingoBoard(rawBoard: String) : BingoBoard(rawBoard) {
    var indexWhenBingo = -1
        private set

    fun checkAllDrawnNumbers(drawnNumbers: List<Int>) {
        indexWhenBingo = 0
        do {
            markNumber(drawnNumbers[indexWhenBingo])
        } while (!bingo() && drawnNumbers.size > ++indexWhenBingo)
    }
}
