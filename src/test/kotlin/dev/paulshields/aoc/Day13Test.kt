package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day13Test {
    private val transparentPaper = "6,10\n" +
            "0,14\n" +
            "9,10\n" +
            "0,3\n" +
            "10,4\n" +
            "4,11\n" +
            "6,0\n" +
            "6,12\n" +
            "4,1\n" +
            "0,13\n" +
            "10,12\n" +
            "3,4\n" +
            "3,0\n" +
            "8,4\n" +
            "1,10\n" +
            "2,14\n" +
            "8,10\n" +
            "9,0\n" +
            "\n" +
            "fold along y=7\n" +
            "fold along x=5"

    @Test
    fun `should correctly count the number of dots on a page folded once`() {
        val result = countVisibleDotsAfterFold(transparentPaper, 1)

        assertThat(result).isEqualTo(17)
    }

    @Test
    fun `should correctly print the folded paper`() {
        val expectedResult = "#####\n" +
                "#...#\n" +
                "#...#\n" +
                "#...#\n" +
                "#####"

        val result = printPaperAfterFolds(transparentPaper)

        assertThat(result).isEqualTo(expectedResult)
    }
}
