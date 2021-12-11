package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import dev.paulshields.aoc.common.Point
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day5Test {
    @Test
    fun `should correctly count the points where lines overlap without any diagonals`() {
        val lineSegments = listOf(
            LineSegment(Point(0, 9), Point(5, 9)),
            LineSegment(Point(9, 4), Point(3, 4)),
            LineSegment(Point(2, 2), Point(2, 1)),
            LineSegment(Point(7, 0), Point(7, 4)),
            LineSegment(Point(0, 9), Point(2, 9)),
            LineSegment(Point(3, 4), Point(1, 4)))

        val result = countPointsWhereLinesOverlap(lineSegments)

        assertThat(result).isEqualTo(5)
    }

    @Test
    fun `should correctly count the points where lines overlap with any diagonals`() {
        val lineSegments = listOf(
            LineSegment(Point(0, 9), Point(5, 9)),
            LineSegment(Point(8, 0), Point(0, 8)),
            LineSegment(Point(9, 4), Point(3, 4)),
            LineSegment(Point(2, 2), Point(2, 1)),
            LineSegment(Point(7, 0), Point(7, 4)),
            LineSegment(Point(6, 4), Point(2, 0)),
            LineSegment(Point(0, 9), Point(2, 9)),
            LineSegment(Point(3, 4), Point(1, 4)),
            LineSegment(Point(0, 0), Point(8, 8)),
            LineSegment(Point(5, 5), Point(8, 2)))

        val result = countPointsWhereLinesOverlap(lineSegments)

        assertThat(result).isEqualTo(12)
    }

    @Nested
    inner class LineSegmentTest {
        @Test
        fun `should correctly calculate all points on a line segment for a horizontal line`() {
            val target = LineSegment(Point(0, 9), Point(5, 9))

            assertThat(target.pointsOnLine).containsExactly(
                Point(0, 9),
                Point(1, 9),
                Point(2, 9),
                Point(3, 9),
                Point(4, 9),
                Point(5, 9))
        }

        @Test
        fun `should correctly calculate all points on a line segment for a vertical line`() {
            val target = LineSegment(Point(6, 0), Point(6, 5))

            assertThat(target.pointsOnLine).containsExactly(
                Point(6, 0),
                Point(6, 1),
                Point(6, 2),
                Point(6, 3),
                Point(6, 4),
                Point(6, 5))
        }

        @Test
        fun `should correctly calculate all points on a line segment for a diagonal line`() {
            val target = LineSegment(Point(0, 0), Point(5, 5))

            assertThat(target.pointsOnLine).containsExactly(
                Point(0, 0),
                Point(1, 1),
                Point(2, 2),
                Point(3, 3),
                Point(4, 4),
                Point(5, 5))
        }
    }
}
