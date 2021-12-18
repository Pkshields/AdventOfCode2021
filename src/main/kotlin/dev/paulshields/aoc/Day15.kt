/**
 * Day 15: Chiton
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.Point
import dev.paulshields.aoc.common.readFileAsString

fun main() {
    println(" ** Day 15: Chiton ** \n")

    val riskLevelMap = readFileAsString("/Day15RiskLevelMap.txt")
    val startingPosition = Point(0, 0)

    val cave = ChitonCave(riskLevelMap)
    var finishingPosition = Point(cave.width - 1, cave.height - 1)
    var riskLevelsFromStartingPosition = cave.findShortestDistanceThroughCave(startingPosition)
    println("The least risky distance through the cave is ${riskLevelsFromStartingPosition[finishingPosition]}")

    val fullCave = FullChitonCave(riskLevelMap)
    finishingPosition = Point(fullCave.width - 1, fullCave.height - 1)
    riskLevelsFromStartingPosition = fullCave.findShortestDistanceThroughCave(startingPosition)
    println("The least risky distance through the full cave is ${riskLevelsFromStartingPosition[finishingPosition]}")
}

class FullChitonCave(rawRiskLevelMap: String) : ChitonCave(rawRiskLevelMap) {
    private val cellWidth = super.width
    private val cellHeight = super.height
    override var width = cellWidth * 5
    override var height = cellHeight * 5

    override fun getRiskAtNode(node: Point): Int {
        val xPos = node.x % cellWidth
        val yPos = node.y % cellHeight
        val widthIterations = node.x / cellWidth
        val heightIterations = node.y / cellHeight

        return ((riskLevelMap[yPos][xPos] + widthIterations + heightIterations - 1) % 9) + 1
    }
}

open class ChitonCave(rawRiskLevelMap: String) {
    protected val riskLevelMap = parseRiskLevelMap(rawRiskLevelMap)
    open var width = riskLevelMap[0].size
        protected set
    open var height = riskLevelMap.size
        protected set

    private fun parseRiskLevelMap(riskLevelMap: String): List<List<Int>> {
        return riskLevelMap.lines().map { it.toCharArray().map { it.digitToInt() } }
    }

    /**
     * Using a lightly modified Dijkstra's Algorithm
     *
     * Performance on this is still dodgy. Worth a revisit sometime. Might be the use of linked collections for storage types.
     */
    fun findShortestDistanceThroughCave(startingPosition: Point): Map<Point, Int> {
        val numberOfNodes = width * height
        val riskLevelsOverDistance = mutableMapOf(startingPosition to 0)
        val settledNodes = mutableSetOf<Point>()
        val unsettledNodes = mutableSetOf(startingPosition)

        while (settledNodes.size < numberOfNodes) {
            val closestNode = unsettledNodes.minByOrNull { riskLevelsOverDistance[it] ?: Int.MAX_VALUE } ?: unsettledNodes.first()
            val closestNodeRiskLevel = riskLevelsOverDistance[closestNode] ?: Int.MAX_VALUE
            settledNodes.add(closestNode)
            unsettledNodes.remove(closestNode)

            val adjacentNodes = getAdjacentNodes(closestNode)
                .filter { adjacentNode -> !settledNodes.contains(adjacentNode) }
            unsettledNodes.addAll(adjacentNodes)

            adjacentNodes
                .forEach { adjacentNode ->
                    val currentRiskLevel = riskLevelsOverDistance[adjacentNode] ?: Int.MAX_VALUE
                    val proposedRiskLevel = closestNodeRiskLevel + getRiskAtNode(adjacentNode)
                    if (proposedRiskLevel < currentRiskLevel) {
                        riskLevelsOverDistance[adjacentNode] = proposedRiskLevel
                    }
                }
        }

        return riskLevelsOverDistance.toMap()
    }

    protected open fun getRiskAtNode(node: Point) = riskLevelMap[node.y][node.x]

    private fun getAdjacentNodes(node: Point): List<Point> {
        val topNode = if (node.y > 0) Point(node.x, node.y - 1) else null
        val leftNode = if (node.x > 0) Point(node.x - 1, node.y) else null
        val bottomNode = if (node.y < height - 1) Point(node.x, node.y + 1) else null
        val rightNode = if (node.x < width - 1) Point(node.x + 1, node.y) else null

        return listOfNotNull(topNode, leftNode, bottomNode, rightNode)
    }
}
