package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day7Test {
    private val crabPositions = listOf(16, 1, 2, 0, 4, 2, 7, 1, 2, 14)

    @Test
    fun `should naively calculate the most efficient fuel usage to line up the crabs`() {
        val result = naivelyCalculateFuelUsageToAlignCrabs(crabPositions)

        assertThat(result).isEqualTo(37)
    }

    @Test
    fun `should correctly calculate the most efficient fuel usage to line up the crabs`() {
        val result = correctlyCalculateFuelUsageToAlignCrabs(crabPositions)

        assertThat(result).isEqualTo(168)
    }
}
