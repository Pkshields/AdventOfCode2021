package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day14Test {
    private val polymerInstructions = "NNCB\n" +
            "\n" +
            "CH -> B\n" +
            "HH -> N\n" +
            "CB -> H\n" +
            "NH -> C\n" +
            "HB -> C\n" +
            "HC -> B\n" +
            "HN -> C\n" +
            "NN -> C\n" +
            "BH -> H\n" +
            "NC -> B\n" +
            "NB -> B\n" +
            "BN -> B\n" +
            "BB -> N\n" +
            "BC -> B\n" +
            "CC -> N\n" +
            "CN -> C"

    @Test
    fun `should correctly calculate difference in count of most and least elements in polymer after pair insertion process is run ten times`() {
        val result = differenceInCountOfMostAndLeastElementsInPolymerAfterIteration(polymerInstructions, 10)

        assertThat(result).isEqualTo(1588L)
    }

    @Test
    fun `should correctly calculate difference in count of most and least elements in polymer after pair insertion process is run forty times`() {
        val result = differenceInCountOfMostAndLeastElementsInPolymerAfterIteration(polymerInstructions, 40)

        assertThat(result).isEqualTo(2188189693529L)
    }
}
