package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day10Test {
    private val testCode = listOf(
        "[({(<(())[]>[[{[]{<()<>>",
        "[(()[<>])]({[<{<<[]>>(",
        "{([(<{}[<>[]}>{[]{[(<()>",
        "(((({<>}<{<{<>}{[]{[]{}",
        "[[<[([]))<([[{}[[()]]]",
        "[{[{({}]{}}([{[{{{}}([]",
        "{<[[]]>}<{[{[{[]{()[[[]",
        "[<(<(<(<{}))><([]([]()",
        "<{([([[(<>()){}]>(<<{{",
        "<{([{{}}[<[[[<>{}]]]>[]]")

    @Test
    fun `should correctly calculate the total illegal character syntax score`() {
        val result = calculateIllegalCharacterSyntaxScore(testCode)

        assertThat(result).isEqualTo(26397)
    }

    @Test
    fun `should correctly calculate the middle incomplete syntax score`() {
        val result = calculateMiddleIncompleteSyntaxScore(testCode)

        assertThat(result).isEqualTo(288957)
    }
}
