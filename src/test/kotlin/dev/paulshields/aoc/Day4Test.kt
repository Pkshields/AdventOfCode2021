package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day4Test {
    @Nested
    inner class BingoBoardTest {
        private val target = BingoBoard(
            "14 21 17 24  4\n" +
                    "10 16 15  9 19\n" +
                    "18  8 23 26 20\n" +
                    "22 11 13  6  5\n" +
                    " 2  0 12  3  7")

        @Test
        fun `should correctly find horizontal bingo`() {
            target.markNumber(14)
            target.markNumber(21)
            target.markNumber(17)
            target.markNumber(24)
            target.markNumber(4)

            assertThat(target.bingo()).isEqualTo(true)
        }

        @Test
        fun `should correctly find vertical bingo`() {
            target.markNumber(14)
            target.markNumber(10)
            target.markNumber(18)
            target.markNumber(22)
            target.markNumber(2)

            assertThat(target.bingo()).isEqualTo(true)
        }

        @Test
        fun `should not find bingo when no numbers are marked`() {
            assertThat(target.bingo()).isEqualTo(false)
        }

        @Test
        fun `should not find bingo when a diagonal is marked`() {
            listOf(14, 16, 23, 6, 7)
                .forEach { target.markNumber(it) }

            assertThat(target.bingo()).isEqualTo(false)
        }

        @Test
        fun `should correctly match advent of code score`() {
            givenNumbersAreMarked(7, 4, 9, 5, 11, 17, 23, 2, 0, 14, 21, 24)

            assertThat(target.bingo()).isEqualTo(true)
            assertThat(target.calculateBoardScore()).isEqualTo(4512)
        }

        private fun givenNumbersAreMarked(vararg numbers: Int) = numbers.forEach { target.markNumber(it) }
    }

    @Nested
    inner class RiggedBingoBoardTest {
        private val target = RiggedBingoBoard(
            "14 21 17 24  4\n" +
                    "10 16 15  9 19\n" +
                    "18  8 23 26 20\n" +
                    "22 11 13  6  5\n" +
                    " 2  0 12  3  7")

        @Test
        fun `should search all drawn numbers for bingo`() {
            target.checkAllDrawnNumbers(listOf(7, 4, 9, 5, 11, 17, 23, 2, 0, 14, 21, 24))

            assertThat(target.bingo()).isEqualTo(true)
            assertThat(target.indexWhenBingo).isEqualTo(11)
        }

        @Test
        fun `should not find bingo if drawn numbers doesn't contain bingo`() {
            target.checkAllDrawnNumbers(listOf(7, 4, 9, 5, 11, 17, 23, 2, 0, 14, 21))

            assertThat(target.bingo()).isEqualTo(false)
        }

        @Test
        fun `should start with index unset`() {
            assertThat(target.indexWhenBingo).isEqualTo(-1)
        }
    }
}
