package dev.paulshields.aoc.common

data class Point3D(val x: Int, val y: Int, val z: Int) {
    companion object {
        fun fromList(xyz: List<Int>) = Point3D(xyz.component1(), xyz.component2(), xyz.component3())
    }

    operator fun plus(point: Point3D) = Point3D(x + point.x, y + point.y, z + point.z)
    operator fun minus(point: Point3D) = Point3D(x - point.x, y - point.y, z - point.z)
}
