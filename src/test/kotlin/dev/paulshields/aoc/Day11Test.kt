package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day11Test {
    private val dumboOctopuses =
            "5483143223\n" +
            "2745854711\n" +
            "5264556173\n" +
            "6141336146\n" +
            "6357385478\n" +
            "4167524645\n" +
            "2176841721\n" +
            "6882881134\n" +
            "4846848554\n" +
            "5283751526"

    @Test
    fun `should correctly calculate number of flashes in simple example`() {
        val simpleExample = "11111\n" +
                "19991\n" +
                "19191\n" +
                "19991\n" +
                "11111"

        val result = countNumberOfFlashesAfterSteps(simpleExample, 1)

        assertThat(result).isEqualTo(9)
    }

    @Test
    fun `should correctly calculate the number of octopus flashes over 100 steps`() {
        val result = countNumberOfFlashesAfterSteps(dumboOctopuses, 100)

        assertThat(result).isEqualTo(1656)
    }

    @Test
    fun `should correctly calculate the step where all octopuses will flash at the same time`() {
        val result = calculateStepNumberWhenEveryOctopusIsFlashing(dumboOctopuses)

        assertThat(result).isEqualTo(195)
    }
}
