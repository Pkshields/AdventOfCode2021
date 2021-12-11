/**
 * Day 10: Syntax Scoring
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.readFileAsStringList

fun main() {
    println(" ** Day 10: Syntax Scoring ** \n")

    val navigationSubsystemCode = readFileAsStringList("/Day10NavigationSubsystemCode.txt")

    val illegalCharacterSyntaxScore = calculateIllegalCharacterSyntaxScore(navigationSubsystemCode)
    println("The illegal character syntax score for the navigation subsystem code is $illegalCharacterSyntaxScore")

    val middleIncompleteSyntaxScore = calculateMiddleIncompleteSyntaxScore(navigationSubsystemCode)
    println("The middle incomplete syntax score for the navigation subsystem code is $middleIncompleteSyntaxScore")
}

fun calculateIllegalCharacterSyntaxScore(code: List<String>) =
    syntaxChecker(code)
        .filterIsInstance<CorruptSyntaxError>().map {
            when (it.illegalCharacter) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> 0
            }
        }.sum()

fun calculateMiddleIncompleteSyntaxScore(code: List<String>): Long {
    val scores = syntaxChecker(code)
        .filterIsInstance<IncompleteSyntaxError>()
        .map(::toIncompleteSyntaxScore)
        .sorted()

    return scores[scores.size / 2]
}

private fun toIncompleteSyntaxScore(error: IncompleteSyntaxError) =
    error.missingClosingCharacters
        .mapNotNull {
            when (it) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> null
            }
        }
        .map { it.toLong() }
        .fold(0L) { accumulator, characterScore -> (accumulator * 5) + characterScore }

private fun syntaxChecker(code: List<String>) =
    code.mapIndexedNotNull { index, line -> findAnySyntaxErrorsInLine(line, index) }

private fun findAnySyntaxErrorsInLine(code: String, lineNumber: Int): SyntaxError? {
    val expectedQueue = mutableListOf<Char>()
    for (char in code.toCharArray()) {
        when (char) {
            '(' -> expectedQueue.add(')')
            '[' -> expectedQueue.add(']')
            '{' -> expectedQueue.add('}')
            '<' -> expectedQueue.add('>')
            expectedQueue.last() -> expectedQueue.removeLast()
            else -> return CorruptSyntaxError(lineNumber, char)
        }
    }

    return if (expectedQueue.isEmpty()) null else IncompleteSyntaxError(lineNumber, expectedQueue.reversed().toList())
}

open class SyntaxError(open val line: Int)
data class CorruptSyntaxError(override val line: Int, val illegalCharacter: Char) : SyntaxError(line)
data class IncompleteSyntaxError(override val line: Int, val missingClosingCharacters: List<Char>) : SyntaxError(line)
