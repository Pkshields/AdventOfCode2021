package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import dev.paulshields.aoc.common.Point
import org.junit.jupiter.api.Test

class Day15Test {
    private val riskLevelMap = "1163751742\n" +
            "1381373672\n" +
            "2136511328\n" +
            "3694931569\n" +
            "7463417111\n" +
            "1319128137\n" +
            "1359912421\n" +
            "3125421639\n" +
            "1293138521\n" +
            "2311944581"

    private val startingPosition = Point(0, 0)

    @Test
    fun `should find the distance of the least risky path through the cave`() {
        val finishingPosition = Point(9, 9)
        val target = ChitonCave(riskLevelMap)

        val result = target.findShortestDistanceThroughCave(startingPosition)

        assertThat(result[finishingPosition]).isEqualTo(40)
    }

    @Test
    fun `should find the distance of the least risky path through the full cave`() {
        val finishingPosition = Point(49, 49)
        val target = FullChitonCave(riskLevelMap)

        val result = target.findShortestDistanceThroughCave(startingPosition)

        assertThat(result[finishingPosition]).isEqualTo(315)
    }
}
