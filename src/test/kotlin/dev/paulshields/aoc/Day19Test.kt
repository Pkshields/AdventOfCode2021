package dev.paulshields.aoc

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsSubList
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import dev.paulshields.aoc.common.Point3D
import dev.paulshields.aoc.common.readFileAsString
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day19Test {
    @Test
    fun `should correctly count the number of scanners in the sample report`() {
        val sampleReport = readFileAsString("/Day19SampleScannerReport.txt")

        val result = findAllScannersAndBeacons(sampleReport)

        assertThat(result.beaconLocations).hasSize(79)
    }

    @Test
    fun `should correctly calculate the manhattan distance between the 2 furthest away scanners in the sample report`() {
        val sampleReport = readFileAsString("/Day19SampleScannerReport.txt")
        val target = findAllScannersAndBeacons(sampleReport)

        val result = calculateLargestManhattanDistance(target.scannerPositions)

        assertThat(result).isEqualTo(3621)
    }

    @Nested
    inner class SimplerTests {
        private val twelveBeaconPositions = listOf(
            Point3D(1, 1, 1),
            Point3D(2, 2, 2),
            Point3D(3, 3, 3),
            Point3D(4, 4, 4),
            Point3D(5, 5, 5),
            Point3D(6, 6, 6),
            Point3D(7, 7, 7),
            Point3D(8, 8, 8),
            Point3D(9, 9, 9),
            Point3D(10, 10, 10),
            Point3D(11, 11, 11),
            Point3D(12, 12, 12))

        private val alternateTwelveBeaconPositions = twelveBeaconPositions.map { it + Point3D(1000, 1000, 1000) }

        @Test
        fun `should find absolute locations of all beacons where scanners are on the same orientation`() {
            val relativeBeaconLocations = listOf(
                twelveBeaconPositions,
                twelveBeaconPositions.map { it - Point3D(1, 1, 1) } + listOf(Point3D(17, 17, 17)))

            val result = findAllScannersAndBeacons(relativeBeaconLocations)

            assertThat(result.beaconLocations).all {
                hasSize(13)
                containsSubList(twelveBeaconPositions)
                contains(Point3D(18, 18, 18))
            }
        }

        @Test
        fun `should find absolute locations of all beacons where scanners are on the same orientation but are relative to different scanners`() {
            val relativeBeaconLocations = listOf(
                twelveBeaconPositions,
                (twelveBeaconPositions + alternateTwelveBeaconPositions).map { it - Point3D(1, 1, 1) },
                alternateTwelveBeaconPositions.map { it - Point3D(5, 5, 5) }
            )

            val result = findAllScannersAndBeacons(relativeBeaconLocations)

            assertThat(result.beaconLocations).all {
                hasSize(24)
                containsSubList(twelveBeaconPositions)
                containsSubList(alternateTwelveBeaconPositions)
            }
        }

        @Test
        fun `should correctly calculate the manhattan distance between the 2 furthest away scanners`() {
            val beacons = listOf(Point3D(1105, -1205, 1229), Point3D(-92, -2380, -20))

            val result = calculateLargestManhattanDistance(beacons)

            assertThat(result).isEqualTo(3621)
        }
    }
}
