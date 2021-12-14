package dev.paulshields.aoc.common

data class Point(val x: Int, val y: Int) {
    companion object {
        fun fromList(xy: List<Int>) = Point(xy.component1(), xy.component2())
    }
}
