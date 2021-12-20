package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import dev.paulshields.aoc.common.Point
import org.junit.jupiter.api.Test

class Day17Test {
    private val targetArea = TargetArea((20..30), (-10..-5))

    @Test
    fun `should find highest possible height for any velocity that hits given target area`() {
        val result = findHeightAtVelocityWhichLandsInTrench(targetArea)

        assertThat(result).isEqualTo(45)
    }

    @Test
    fun `should count of all valid initial velocities that hits given target area`() {
        val result = findAllInitialVelocitiesWhichLandInTrench(targetArea)

        assertThat(result).hasSize(112)
    }

    @Test
    fun `should find all valid initial velocities that hits given target area`() {
        val result = findAllInitialVelocitiesWhichLandInTrench(targetArea)

        assertThat(result.map { it.initialVelocity }).containsExactlyInAnyOrder(
            Point(23, -10),
            Point(25, -9),
            Point(27, -5),
            Point(29, -6),
            Point(22, -6),
            Point(21, -7),
            Point(9, 0),
            Point(27, -7),
            Point(24, -5),
            Point(25, -7),
            Point(26, -6),
            Point(25, -5),
            Point(6, 8),
            Point(11, -2),
            Point(20, -5),
            Point(29, -10),
            Point(6, 3),
            Point(28, -7),
            Point(8, 0),
            Point(30, -6),
            Point(29, -8),
            Point(20, -10),
            Point(6, 7),
            Point(6, 4),
            Point(6, 1),
            Point(14, -4),
            Point(21, -6),
            Point(26, -10),
            Point(7, -1),
            Point(7, 7),
            Point(8, -1),
            Point(21, -9),
            Point(6, 2),
            Point(20, -7),
            Point(30, -10),
            Point(14, -3),
            Point(20, -8),
            Point(13, -2),
            Point(7, 3),
            Point(28, -8),
            Point(29, -9),
            Point(15, -3),
            Point(22, -5),
            Point(26, -8),
            Point(25, -8),
            Point(25, -6),
            Point(15, -4),
            Point(9, -2),
            Point(15, -2),
            Point(12, -2),
            Point(28, -9),
            Point(12, -3),
            Point(24, -6),
            Point(23, -7),
            Point(25, -10),
            Point(7, 8),
            Point(11, -3),
            Point(26, -7),
            Point(7, 1),
            Point(23, -9),
            Point(6, 0),
            Point(22, -10),
            Point(27, -6),
            Point(8, 1),
            Point(22, -8),
            Point(13, -4),
            Point(7, 6),
            Point(28, -6),
            Point(11, -4),
            Point(12, -4),
            Point(26, -9),
            Point(7, 4),
            Point(24, -10),
            Point(23, -8),
            Point(30, -8),
            Point(7, 0),
            Point(9, -1),
            Point(10, -1),
            Point(26, -5),
            Point(22, -9),
            Point(6, 5),
            Point(7, 5),
            Point(23, -6),
            Point(28, -10),
            Point(10, -2),
            Point(11, -1),
            Point(20, -9),
            Point(14, -2),
            Point(29, -7),
            Point(13, -3),
            Point(23, -5),
            Point(24, -8),
            Point(27, -9),
            Point(30, -7),
            Point(28, -5),
            Point(21, -10),
            Point(7, 9),
            Point(6, 6),
            Point(21, -5),
            Point(27, -10),
            Point(7, 2),
            Point(30, -9),
            Point(21, -8),
            Point(22, -7),
            Point(24, -9),
            Point(20, -6),
            Point(6, 9),
            Point(29, -5),
            Point(8, -2),
            Point(27, -8),
            Point(30, -5),
            Point(24, -7))
    }
}
