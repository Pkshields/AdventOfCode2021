package dev.paulshields.aoc.common

data class Point(val x: Int, val y: Int) {
    companion object {
        fun fromList(xy: List<Int>) = Point(xy.component1(), xy.component2())
    }

    operator fun plus(point: Point) = Point(x + point.x, y + point.y)
    operator fun minus(point: Point) = Point(x - point.x, y - point.y)
}
