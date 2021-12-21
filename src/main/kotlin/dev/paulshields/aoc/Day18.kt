/**
 * Day 18: Snailfish
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.readFileAsStringList
import kotlin.math.ceil

fun main() {
    println(" ** Day 18: Snailfish ** \n")

    val mathsHomework = readFileAsStringList("/Day18SnailfishMathsHomework.txt")

    val completedHomeworkNumber = completeSnailfishMathsHomework(mathsHomework)
    println("The magnitude of the final sum from the maths homework is ${completedHomeworkNumber.magnitude}")

    val largestMagnitude = calculateLargestMagnitudeForSumOfAnyTwoNumbers(mathsHomework)
    println("The largest magnitude of the sum of any two snailfish numbers in this list is $largestMagnitude")
}

fun completeSnailfishMathsHomework(mathsHomework: List<String>) =
    mathsHomework
        .drop(1)
        .fold(SnailfishNumber(mathsHomework.first())) { accumulator, number -> accumulator + SnailfishNumber(number) }

fun calculateLargestMagnitudeForSumOfAnyTwoNumbers(mathsHomework: List<String>) =
    mathsHomework
        .flatMap { number -> mathsHomework.map { Pair(number, it) } }
        .distinct()
        .map { (left, right) -> SnailfishNumber(left) + SnailfishNumber(right) }
        .maxOfOrNull { it.magnitude } ?: 0

class SnailfishNumber(stringEncodedNumber: String) {
    private val rootNode = parse(stringEncodedNumber).apply { reduce() }

    val magnitude: Int by lazy { rootNode.magnitude }

    private fun parse(number: String, nestLevel: Int = 0): SnailfishNode {
        val bracketsDropped = number.substring(1, number.length - 1)
        val nodes = bracketsDropped.splitAtIndex(findPairSplitPosition(bracketsDropped)).map {
            it.toIntOrNull()?.let { SnailfishInteger(it, nestLevel + 1) } ?: parse(it, nestLevel + 1)
        }
        return SnailfishNode(nodes[0], nodes[1], nestLevel)
    }

    private fun findPairSplitPosition(number: String): Int {
        var bracketCount = 0
        for ((index, char) in number.toCharArray().withIndex()) {
            if (char == '[') ++bracketCount
            else if (char == ']') --bracketCount
            else if (char == ',' && bracketCount == 0) return index
        }
        return -1
    }

    operator fun plus(number: SnailfishNumber) = SnailfishNumber("[$this,$number]")

    override fun toString() = rootNode.toString()
}

private class SnailfishNode(private var left: SnailfishElement, private var right: SnailfishElement, nestLevel: Int) : SnailfishElement(nestLevel) {
    override val magnitude by lazy { (3 * left.magnitude) + (2 * right.magnitude) }

    override fun explode(): Pair<Int?, Int?>? {
        if (left is SnailfishInteger && right is SnailfishInteger && nestLevel >= 4) {
            return Pair(left.toIntOrNull(), right.toIntOrNull())
        }

        left.explode()?.let {
            if (allNotNull(it.first, it.second)) left = snailfishZero(nestLevel + 1)
            it.second?.let { right.addToLeft(it) }
            return Pair(it.first, null)
        }

        right.explode()?.let {
            if (allNotNull(it.first, it.second)) right = snailfishZero(nestLevel + 1)
            it.first?.let { left.addToRight(it) }
            return Pair(null, it.second)
        }

        return null
    }

    override fun split() =
        left.split()?.also { if (it.nestLevel == left.nestLevel) left = it }
            ?: right.split()?.also { if (it.nestLevel == right.nestLevel) right = it }

    fun reduce() {
        do {
            val reducedResult = left.explode()?.also {
                it.second?.let { right.addToLeft(it) }
            } ?: right.explode()?.also {
                it.first?.let { left.addToRight(it) }
            } ?: split()
        } while (reducedResult != null)
    }

    override fun addToLeft(regularNumber: Int) = left.addToLeft(regularNumber)
    override fun addToRight(regularNumber: Int) = right.addToRight(regularNumber)

    override fun toString() = "[$left,$right]"
}

private class SnailfishInteger(private var number: Int, nestLevel: Int) : SnailfishElement(nestLevel) {
    override val magnitude
        get() = number

    fun toInt() = number

    override fun split(): SnailfishNode? {
        if (number > 9) {
            return SnailfishNode(
                (number / 2).toSnailfishInteger(nestLevel + 1),
                number.divideRoundUp(2).toSnailfishInteger(nestLevel + 1),
                nestLevel)
        }
        return null
    }

    override fun addToLeft(regularNumber: Int) {
        number += regularNumber
    }

    override fun addToRight(regularNumber: Int) {
        number += regularNumber
    }

    override fun toString() = number.toString()
}

private abstract class SnailfishElement(val nestLevel: Int) {
    abstract val magnitude: Int

    open fun explode(): Pair<Int?, Int?>? = null
    open fun split(): SnailfishNode? = null
    abstract fun addToLeft(regularNumber: Int)
    abstract fun addToRight(regularNumber: Int)
    fun toIntOrNull() = (this as? SnailfishInteger)?.toInt()
}

private fun snailfishZero(nestLevel: Int) = SnailfishInteger(0, nestLevel)

private fun String.splitAtIndex(index: Int) = listOf(this.substring(0, index), this.substring(index + 1))
private fun Int.toSnailfishInteger(nestLevel: Int) = SnailfishInteger(this, nestLevel)
private fun Int.divideRoundUp(divisor: Int) = ceil(this.toDouble() / divisor).toInt()
private fun allNotNull(vararg items: Any?) = items.all { it != null }
