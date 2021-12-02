/**
 * Day 2: Dive!
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.readFileAsStringList

fun main() {
    println(" ** Day 2: Dive! ** \n")

    val diveCommands = readFileAsStringList("/Day2DiveCommands.txt")

    val finalPositionByDepth = processDiveCommandsByDepth(diveCommands)
    println("Final position of part 1 is ${finalPositionByDepth.horizontalPosition} with a depth of ${finalPositionByDepth.depth}, " +
            "the puzzle answer is ${finalPositionByDepth.horizontalPosition * finalPositionByDepth.depth}")

    val finalPositionByAim = processDiveCommandsByAim(diveCommands)
    println("Final position of part 2 is ${finalPositionByAim.horizontalPosition} with a depth of ${finalPositionByAim.depth}, " +
            "the puzzle answer is ${finalPositionByAim.horizontalPosition * finalPositionByAim.depth}")
}

fun processDiveCommandsByDepth(commands: List<String>) =
    commands.fold(DivePosition(0, 0)) { position, nextMove ->
        val (action, unitsAsString) = nextMove.split(" ")
        val units = unitsAsString.toInt()

        when (action) {
            "forward" -> position.moveForward(units)
            "down" -> position.diveDown(units)
            "up" -> position.riseUp(units)
            else -> position
        }
    }

fun processDiveCommandsByAim(commands: List<String>) =
    commands.fold(DiveAimPosition(0, 0, 0)) { position, nextMove ->
        val (action, unitsAsString) = nextMove.split(" ")
        val units = unitsAsString.toInt()

        when (action) {
            "forward" -> position.moveForward(units)
            "down" -> position.aimDown(units)
            "up" -> position.aimUp(units)
            else -> position
        }
    }

data class DivePosition(val horizontalPosition: Int, val depth: Int) {
    fun moveForward(units: Int) = DivePosition(horizontalPosition + units, depth)
    fun diveDown(units: Int) = DivePosition(horizontalPosition, depth + units)
    fun riseUp(units: Int) = DivePosition(horizontalPosition, depth - units)
}

data class DiveAimPosition(val horizontalPosition: Int, val depth: Int, private val aim: Int) {
    fun moveForward(units: Int) = DiveAimPosition(horizontalPosition + units, depth + (units * aim), aim)
    fun aimDown(units: Int) = DiveAimPosition(horizontalPosition, depth, aim + units)
    fun aimUp(units: Int) = DiveAimPosition(horizontalPosition, depth, aim - units)
}
