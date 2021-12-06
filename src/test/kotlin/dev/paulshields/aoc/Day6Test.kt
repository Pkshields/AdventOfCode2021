package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day6Test {
    private val startingFish = listOf(3, 4, 3, 1, 2)

    @Test
    fun `should correctly simulate lanternfish count after 18 days passed`() {
        val result = simulateLanternfishDays(startingFish, 18)

        assertThat(result).isEqualTo(26)
    }

    @Test
    fun `should correctly simulate lanternfish count after 80 days passed`() {
        val result = simulateLanternfishDays(startingFish, 80)

        assertThat(result).isEqualTo(5934)
    }

    @Test
    fun `should correctly simulate lanternfish count after 256 days passed`() {
        val result = simulateLanternfishDays(startingFish, 256)

        assertThat(result).isEqualTo(26984457539)
    }
}
