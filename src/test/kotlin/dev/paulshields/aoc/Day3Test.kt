package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day3Test {
    private val sampleReport = listOf("00100", "11110", "10110", "10111", "10101", "01111", "00111", "11100", "10000", "11001", "00010", "01010")

    @Test
    fun `should correctly decode the power consumption from the diagnostics report`() {
        val result = decodePowerConsumptionFromDiagnosticsReport(sampleReport)

        assertThat(result).isEqualTo(198)
    }

    @Test
    fun `should correctly decode the life support rating from the diagnostics report`() {
        val result = decodeLifeSupportRatingFromDiagnosticsReport(sampleReport)

        assertThat(result).isEqualTo(230)
    }
}
