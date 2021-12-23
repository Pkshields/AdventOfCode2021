/**
 * Day 19: Beacon Scanner
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.Point3D
import dev.paulshields.aoc.common.readFileAsString
import kotlin.math.abs

fun main() {
    println(" ** Day 19: Beacon Scanner ** \n")

    val scannerReport = readFileAsString("/Day19ScannerReport.txt")
    val allScannersAndBeacons = findAllScannersAndBeacons(scannerReport)
    println("There are ${allScannersAndBeacons.beaconLocations.size} beacons in the ocean trench.")

    val largestManhattanDistance = calculateLargestManhattanDistance(allScannersAndBeacons.scannerPositions)
    println("The manhattan distance between the 2 scanners furthest away from each other is $largestManhattanDistance")
}

fun findAllScannersAndBeacons(scannerReport: String) = findAllScannersAndBeacons(parseScannerReport(scannerReport))

/**
 * Assumes scanner 0 is at point (0, 0, 0)
 */
fun findAllScannersAndBeacons(relativeBeaconPositions: List<List<Point3D>>): ScannerMap {
    var unmappedScanners = relativeBeaconPositions
        .mapIndexed { index, scanner -> index to getAllRotationsOfAScanner(scanner) }
        .toMap()
    val mappedScanners = mutableMapOf(0 to Scanner(Point3D(0, 0, 0), relativeBeaconPositions[0]))
    val mappedScannerToProcessQueue = mutableListOf(0)

    while (mappedScannerToProcessQueue.size > 0) {
        val mappedScannerIndex = mappedScannerToProcessQueue.removeFirst()
        val mappedScanner = mappedScanners[mappedScannerIndex] ?: return ScannerMap(emptyList(), emptyList())

        for ((unmappedScannerIndex, allRotationsOfUnmappedScanner) in unmappedScanners) {
            val newlyMappedScanner = findScannerLocationRelativeToAnotherScanner(allRotationsOfUnmappedScanner, mappedScanner)

            newlyMappedScanner?.let {
                mappedScanners[unmappedScannerIndex] = it
                mappedScannerToProcessQueue.add(unmappedScannerIndex)
            }
        }

        unmappedScanners = unmappedScanners.filter { (key, _) -> !mappedScanners.keys.contains(key) }
    }

    val allBeacons = mappedScanners
        .map { it.value }
        .fold(emptyList<Point3D>()) { accumulator, scanner ->
            (accumulator + scanner.beaconsWithinSight.map { it + scanner.position }).distinct()
        }

    val allScannerPositions = mappedScanners.map { it.value.position }

    return ScannerMap(allScannerPositions, allBeacons)
}

private fun getAllRotationsOfAScanner(scanner: List<Point3D>): List<List<Point3D>> {
    return scanner
        .map { it.rotations() }
        .flatMap { it.mapIndexed { index, value -> index to value } }
        .groupBy(keySelector = { it.first }, valueTransform = { it.second })
        .map { it.value }
}

private fun findScannerLocationRelativeToAnotherScanner(allRotationsOfUnmappedScanner: List<List<Point3D>>, anotherScanner: Scanner): Scanner? {
    val newlyMappedScanner = allRotationsOfUnmappedScanner.firstNotNullOfOrNull { rotatedScanner ->
        val offsetToMappedScanner = rotatedScanner
            .flatMap { beacon -> anotherScanner.beaconsWithinSight.map { otherBeacon -> otherBeacon - beacon } }
            .groupingBy { it }
            .eachCount()
            .filter { it.value >= 12 }
            .map { it.key }
            .firstOrNull()

        offsetToMappedScanner?.let { Pair(it, rotatedScanner) }
    }

    return newlyMappedScanner?.let {
        val offsetToZero = newlyMappedScanner.first.plus(anotherScanner.position)
        Scanner(offsetToZero, newlyMappedScanner.second)
    }
}

fun calculateLargestManhattanDistance(locations: List<Point3D>) =
    locations
        .flatMap { left ->
            locations
                .filter { left != it }
                .map { right -> abs(left.x - right.x) + abs(left.y - right.y) + abs(left.z - right.z) }
        }
        .maxOf { it }

private fun parseScannerReport(report: String) =
    report.split("\n\n")
        .map { it
            .lines()
            .drop(1)
            .map { Point3D.fromList(it.split(',').map { it.toInt() }) }
        }

data class Scanner(val position: Point3D, val beaconsWithinSight: List<Point3D>)
data class ScannerMap(val scannerPositions: List<Point3D>, val beaconLocations: List<Point3D>)

/* ktlint-disable no-multi-spaces */
private fun Point3D.rotations() = listOf(
    this,                                                    // Standard
    Point3D(this.y, this.x * -1, this.z),                 // Rotated 90 degrees
    Point3D(this.x * -1, this.y * -1, this.z),         // Rotated 180 degrees
    Point3D(this.y * -1, this.x, this.z),                 // Rotated 180 degrees

    Point3D(this.z * -1, this.y, this.x),                 // Pointing right
    Point3D(this.y, this.z, this.x),                         // Pointing right, rotated 90 degrees
    Point3D(this.z, this.y * -1, this.x),                 // Pointing right, rotated 180 degrees
    Point3D(this.y * -1, this.z * -1, this.x),         // Pointing right, rotated 270 degrees

    Point3D(this.x * -1, this.y, this.z * -1),         // Pointing backwards
    Point3D(this.y, this.x, this.z * -1),                 // Pointing backwards, rotated 90 degrees
    Point3D(this.x, this.y * -1, this.z * -1),         // Pointing backwards, rotated 180 degrees
    Point3D(this.y * -1, this.x * -1, this.z * -1), // Pointing backwards

    Point3D(this.z, this.y, this.x * -1),                  // Pointing right
    Point3D(this.y, this.z * -1, this.x * -1),          // Pointing right, rotated 90 degrees
    Point3D(this.z * -1, this.y * -1, this.x * -1),  // Pointing right, rotated 180 degrees
    Point3D(this.y * -1, this.z, this.x * -1),          // Pointing right, rotated 270 degrees

    Point3D(this.x, this.z * -1, this.y),                  // Pointing up
    Point3D(this.z * -1, this.x * -1, this.y),          // Pointing up, rotated 90 degrees
    Point3D(this.x * -1, this.z, this.y),                  // Pointing up, rotated 180 degrees
    Point3D(this.z, this.x, this.y),                          // Pointing up, rotated 270 degrees

    Point3D(this.x, this.z, this.y * -1),                  // Pointing down
    Point3D(this.z, this.x * -1, this.y * -1),          // Pointing down, rotated 90 degrees
    Point3D(this.x * -1, this.z * -1, this.y * -1),  // Pointing down, rotated 180 degrees
    Point3D(this.z * -1, this.x, this.y * -1),          // Pointing down, rotated 270 degrees
)
/* ktlint-disable no-multi-spaces */
