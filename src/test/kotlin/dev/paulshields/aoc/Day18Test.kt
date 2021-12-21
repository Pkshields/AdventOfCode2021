package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import dev.paulshields.aoc.testcommon.runTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class Day18Test {
    private val mathsHomework = listOf(
        "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
        "[[[5,[2,8]],4],[5,[[9,9],0]]]",
        "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
        "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
        "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
        "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
        "[[[[5,4],[7,7]],8],[[8,3],8]]",
        "[[9,3],[[9,9],[6,[4,9]]]]",
        "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
        "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]")

    @Test
    fun `should correctly complete the snailfish maths homework`() {
        val result = completeSnailfishMathsHomework(mathsHomework)

        assertThat(result.toString()).isEqualTo("[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]")
        assertThat(result.magnitude).isEqualTo(4140)
    }

    @Test
    fun `should calculate the largest magnitude for the sum of any two numbers in the maths homework`() {
        val result = calculateLargestMagnitudeForSumOfAnyTwoNumbers(mathsHomework)

        assertThat(result).isEqualTo(3993)
    }

    @Nested
    inner class SnailfishNumberTest {
        @TestFactory
        fun `should correctly normalise snailfish number which contains explosions`() = listOf(
            "[[[[[9,8],1],2],3],4]" to "[[[[0,9],2],3],4]",
            "[7,[6,[5,[4,[3,2]]]]]" to "[7,[6,[5,[7,0]]]]",
            "[[6,[5,[4,[3,2]]]],1]" to "[[6,[5,[7,0]]],3]",
            "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]" to "[[3,[2,[8,0]]],[9,[5,[7,0]]]]"
        ).runTest { (rawSnailFishNumber, result) ->
            val target = SnailfishNumber(rawSnailFishNumber)

            assertThat(target.toString()).isEqualTo(result)
        }

        @TestFactory
        fun `should correctly normalise snailfish number which contains splits`() = listOf(
            "[12,1]" to "[[6,6],1]",
            "[13,1]" to "[[6,7],1]",
            "[9,[9,16]]" to "[9,[9,[8,8]]]",
            "[20,30]" to "[[[5,5],[5,5]],[[7,8],[7,8]]]"
        ).runTest { (rawSnailFishNumber, result) ->
            val target = SnailfishNumber(rawSnailFishNumber)

            assertThat(target.toString()).isEqualTo(result)
        }

        @Test
        fun `should correctly normalise snailfish number which contains both explosions and splits`() {
            val rawSnailFishNumber = "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]"

            val target = SnailfishNumber(rawSnailFishNumber)

            assertThat(target.toString()).isEqualTo("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")
        }

        @TestFactory
        fun `should correctly add snailfish numbers together`() = listOf(
            Triple("[1,1]", "[2,2]", "[[1,1],[2,2]]"),
            Triple("[1,[2,3]]", "[[4,5],6]", "[[1,[2,3]],[[4,5],6]]")
        ).runTest { (left, right, expectedResult) ->
            val leftNumber = SnailfishNumber(left)
            val rightNumber = SnailfishNumber(right)

            val result = leftNumber + rightNumber

            assertThat(result.toString()).isEqualTo(expectedResult)
        }

        @TestFactory
        fun `should correctly calculate magnitude of snailfish number`() = listOf(
            "[[1,2],[[3,4],5]]" to 143,
            "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]" to 1384,
            "[[[[1,1],[2,2]],[3,3]],[4,4]]" to 445,
            "[[[[3,0],[5,3]],[4,4]],[5,5]]" to 791,
            "[[[[5,0],[7,4]],[5,5]],[6,6]]" to 1137,
            "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]" to 3488
        ).runTest { (snailfishNumber, expectedMagnitude) ->
            val target = SnailfishNumber(snailfishNumber)

            assertThat(target.magnitude).isEqualTo(expectedMagnitude)
        }
    }
}
