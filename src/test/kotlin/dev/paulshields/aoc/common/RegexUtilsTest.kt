package dev.paulshields.aoc.common

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.hasSize
import org.junit.jupiter.api.Test

class RegexUtilsTest {
    private val target = Regex("Regex Data ([-\\d]+) - (\\w+)")

    @Test
    fun `should correctly extract groups from regex string`() {
        val input = "Regex Data 1 - wordsandthings"

        val result = target.extractGroups(input)

        assertThat(result).containsExactly("1", "wordsandthings")
    }

    @Test
    fun `should nothing when regex does not match`() {
        val input = "Regex Data notamatch"

        val result = target.extractGroups(input)

        assertThat(result).hasSize(0)
    }
}
