package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day1Test {
    @Test
    fun `should correctly count number of depth increases`() {
        val input = listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)

        val output = countNumberOfDepthIncreases(input)

        assertThat(output).isEqualTo(7)
    }

    @Test
    fun `should handle empty lists when counting the number of depth increases`() {
        val input = emptyList<Int>()

        val output = countNumberOfDepthIncreases(input)

        assertThat(output).isEqualTo(0)
    }

    @Test
    fun `should correctly count number of depth increases when using the 3-measurement sliding window`() {
        val input = listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)

        val output = countNumberOfDepthIncreasesUsingSlidingWindow(input)

        assertThat(output).isEqualTo(5)
    }

    @Test
    fun `should handle empty lists when counting the number of depth increases using the 3-measurement sliding window`() {
        val input = emptyList<Int>()

        val output = countNumberOfDepthIncreasesUsingSlidingWindow(input)

        assertThat(output).isEqualTo(0)
    }
}
