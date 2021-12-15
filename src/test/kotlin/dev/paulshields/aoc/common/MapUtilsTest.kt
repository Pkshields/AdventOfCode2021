package dev.paulshields.aoc.common

import assertk.assertThat
import assertk.assertions.isEqualTo
import dev.paulshields.aoc.testcommon.parameterizedTest
import dev.paulshields.aoc.testcommon.runTest
import org.junit.jupiter.api.TestFactory

class MapUtilsTest {
    private val key = "key"

    @TestFactory
    fun `should add to value in map`() = parameterizedTest(
        Triple(1, 2, 3),
        Triple(5, 5, 10),
        Triple(40, 11, 51),
        Triple(-1, 1, 0),
        Triple(0, 50, 50))
        .runTest { (initial, addition, result) ->
            val target = mutableMapOf(key to initial)

            target.addToValue(key, addition)

            assertThat(target[key]).isEqualTo(result)
        }

    @TestFactory
    fun `should add long to value in map`() = parameterizedTest(
        Triple(1L, 2L, 3L),
        Triple(5L, 5L, 10L),
        Triple(40L, 11L, 51L),
        Triple(-1L, 1L, 0L),
        Triple(0L, 50L, 50L))
        .runTest { (initial, addition, result) ->
            val target = mutableMapOf(key to initial)

            target.addToValue(key, addition)

            assertThat(target[key]).isEqualTo(result)
        }

    @TestFactory
    fun `should subtract from value in map`() = parameterizedTest(
        Triple(3, 2, 1),
        Triple(10, 5, 5),
        Triple(51, 11, 40),
        Triple(0, 1, -1),
        Triple(50, 50, 0))
        .runTest { (initial, addition, result) ->
            val target = mutableMapOf(key to initial)

            target.subtractFromValue(key, addition)

            assertThat(target[key]).isEqualTo(result)
        }

    @TestFactory
    fun `should subtract long from value in map`() = parameterizedTest(
        Triple(3L, 2L, 1L),
        Triple(10L, 5L, 5L),
        Triple(51L, 11L, 40L),
        Triple(0L, 1L, -1L),
        Triple(50L, 50L, 0L))
        .runTest { (initial, addition, result) ->
            val target = mutableMapOf(key to initial)

            target.subtractFromValue(key, addition)

            assertThat(target[key]).isEqualTo(result)
        }

    @TestFactory
    fun `should increment value in map`() = parameterizedTest(
        1 to 2,
        10 to 11,
        50 to 51,
        -1 to 0,
        -100 to -99)
        .runTest { (initial, result) ->
            val target = mutableMapOf(key to initial)

            target.incrementValue(key)

            assertThat(target[key]).isEqualTo(result)
        }

    @TestFactory
    fun `should increment long value in map`() = parameterizedTest(
        1L to 2L,
        10L to 11L,
        50L to 51L,
        -1L to 0L,
        -100L to -99L)
        .runTest { (initial, result) ->
            val target = mutableMapOf(key to initial)

            target.incrementValue(key)

            assertThat(target[key]).isEqualTo(result)
        }

    @TestFactory
    fun `should decrement value in map`() = parameterizedTest(
        2 to 1,
        11 to 10,
        51 to 50,
        0 to -1,
        -99 to -100)
        .runTest { (initial, result) ->
            val target = mutableMapOf(key to initial)

            target.decrementValue(key)

            assertThat(target[key]).isEqualTo(result)
        }

    @TestFactory
    fun `should decrement long value in map`() = parameterizedTest(
        2L to 1L,
        11L to 10L,
        51L to 50L,
        0L to -1L,
        -99L to -100L)
        .runTest { (initial, result) ->
            val target = mutableMapOf(key to initial)

            target.decrementValue(key)

            assertThat(target[key]).isEqualTo(result)
        }
}
