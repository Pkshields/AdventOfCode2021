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
}
