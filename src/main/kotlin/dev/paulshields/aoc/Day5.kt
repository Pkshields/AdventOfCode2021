/**
 * Day 5: Hydrothermal Venture
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.Point
import dev.paulshields.aoc.common.readFileAsStringList

fun main() {
    println(" ** Day 5: Hydrothermal Venture ** \n")

    val rawHydrothermalVents = readFileAsStringList("/Day5HydrothermalVentsLineSegments.txt")

    val hydrothermalVents = rawHydrothermalVents.map {
        it.split(" -> ").map {
            it.split(",")
                .mapNotNull { it.toIntOrNull() }
                .let(Point::fromList)
        }.let(LineSegment::fromList)
    }

    val ventsExcludingDiagonals = hydrothermalVents.filter { it.start.x == it.end.x || it.start.y == it.end.y }

    val numberOfOverlapPointExcludingDiagonals = countPointsWhereLinesOverlap(ventsExcludingDiagonals)
    println("There are $numberOfOverlapPointExcludingDiagonals points where the hydrothermal vents overlap excluding diagonals")

    val numberOfOverlapPoints = countPointsWhereLinesOverlap(hydrothermalVents)
    println("There are $numberOfOverlapPoints points where the hydrothermal vents overlap including diagonals")
}

fun countPointsWhereLinesOverlap(lineSegments: List<LineSegment>) =
    lineSegments.flatMap { it.pointsOnLine }
        .groupingBy { it }
        .eachCount()
        .filter { it.value > 1 }
        .count()

data class LineSegment(val start: Point, val end: Point) {
    companion object {
        fun fromList(xy: List<Point>) = LineSegment(xy.component1(), xy.component2())
    }

    val pointsOnLine = if (start.x == end.x) {
        directionlessRange(start.y, end.y).map { Point(start.x, it) }
    } else if (start.y == end.y) {
        directionlessRange(start.x, end.x).map { Point(it, start.y) }
    } else {
        directionlessRange(start.x, end.x).zip(directionlessRange(start.y, end.y)).map { Point(it.first, it.second) }
    }
}

private fun directionlessRange(start: Int, end: Int) = if (start < end) (start..end) else (start downTo end)
