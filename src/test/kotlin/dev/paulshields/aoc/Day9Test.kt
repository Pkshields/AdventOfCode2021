package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.containsAll
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day9Test {
    private val heightMap = "2199943210\n" +
            "3987894921\n" +
            "9856789892\n" +
            "8767896789\n" +
            "9899965678"

    @Test
    fun `should correctly calculate risk level of height map`() {
        val result = calculateRiskLevelOfHeightMap(heightMap)

        assertThat(result).isEqualTo(15)
    }

    @Test
    fun `should correctly find all basin sizes`() {
        val result = findSizeOfBasins(heightMap)

        assertThat(result).hasSize(4)
        assertThat(result).containsAll(3, 9, 14, 9)
    }
}
