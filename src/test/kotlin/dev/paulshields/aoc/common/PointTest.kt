package dev.paulshields.aoc.common

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class PointTest {
    @Test
    fun `should correctly create a point from a list`() {
        val input = listOf(5, 7)

        val result = Point.fromList(input)

        assertThat(result).isEqualTo(Point(5, 7))
    }

    @Test
    fun `should correctly add points together`() {
        val point = Point(5, 7)
        val addition = Point(6, 8)

        val result = point + addition

        assertThat(result).isEqualTo(Point(11, 15))
    }

    @Test
    fun `should correctly subtract points from each other`() {
        val point = Point(10, 20)
        val subtractor = Point(6, 8)

        val result = point - subtractor

        assertThat(result).isEqualTo(Point(4, 12))
    }
}
