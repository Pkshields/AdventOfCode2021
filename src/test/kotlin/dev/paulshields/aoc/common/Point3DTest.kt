package dev.paulshields.aoc.common

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Point3DTest {
    @Test
    fun `should correctly create a point from a list`() {
        val input = listOf(5, 7, 9)

        val result = Point3D.fromList(input)

        assertThat(result).isEqualTo(Point3D(5, 7, 9))
    }

    @Test
    fun `should correctly add points together`() {
        val point = Point3D(5, 7, 9)
        val addition = Point3D(6, 8, 3)

        val result = point + addition

        assertThat(result).isEqualTo(Point3D(11, 15, 12))
    }

    @Test
    fun `should correctly subtract points from each other`() {
        val point = Point3D(10, 20, 60)
        val subtractor = Point3D(6, 8, 22)

        val result = point - subtractor

        assertThat(result).isEqualTo(Point3D(4, 12, 38))
    }
}
