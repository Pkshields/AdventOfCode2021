package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day8Test {
    @Test
    fun `should correctly count the number of instances of 1, 4, 7 or 8 in the corrupted outputs`() {
        val corruptedOutputs = listOf(
            "fdgacbe cefdb cefbgd gcbe",
            "fcgedb cgb dgebacf gc",
            "cg cg fdcagb cbg",
            "efabcd cedba gadfec cb",
            "gecf egdcabf bgf bfgea",
            "gebdcfa ecba ca fadegcb",
            "cefg dcbef fcge gbcadfe",
            "ed bcgafe cdgba cbgef",
            "gbdfcae bgc cg cgb",
            "fgae cfgab fg bagce")

        val result = countEasyToFindDigits(corruptedOutputs)

        assertThat(result).isEqualTo(26)
    }

    @Test
    fun `should correctly sum all decoded output values`() {
        val corruptedDisplays = listOf(
            CorruptedDisplay(listOf("be", "cfbegad", "cbdgef", "fgaecd", "cgeb", "fdcge", "agebfd", "fecdb", "fabcd", "edb"),
                "fdgacbe cefdb cefbgd gcbe"),
            CorruptedDisplay(listOf("edbfga", "begcd", "cbg", "gc", "gcadebf", "fbgde", "acbgfd", "abcde", "gfcbed", "gfec"),
                "fcgedb cgb dgebacf gc"),
            CorruptedDisplay(listOf("fgaebd", "cg", "bdaec", "gdafb", "agbcfd", "gdcbef", "bgcad", "gfac", "gcb", "cdgabef"),
                "cg cg fdcagb cbg"),
            CorruptedDisplay(listOf("fbegcd", "cbd", "adcefb", "dageb", "afcb", "bc", "aefdc", "ecdab", "fgdeca", "fcdbega"),
                "efabcd cedba gadfec cb"),
            CorruptedDisplay(listOf("aecbfdg", "fbg", "gf", "bafeg", "dbefa", "fcge", "gcbea", "fcaegb", "dgceab", "fcbdga"),
                "gecf egdcabf bgf bfgea"),
            CorruptedDisplay(listOf("fgeab", "ca", "afcebg", "bdacfeg", "cfaedg", "gcfdb", "baec", "bfadeg", "bafgc", "acf"),
                "gebdcfa ecba ca fadegcb"),
            CorruptedDisplay(listOf("dbcfg", "fgd", "bdegcaf", "fgec", "aegbdf", "ecdfab", "fbedc", "dacgb", "gdcebf", "gf"),
                "cefg dcbef fcge gbcadfe"),
            CorruptedDisplay(listOf("bdfegc", "cbegaf", "gecbf", "dfcage", "bdacg", "ed", "bedf", "ced", "adcbefg", "gebcd"),
                "ed bcgafe cdgba cbgef"),
            CorruptedDisplay(listOf("egadfb", "cdbfeg", "cegd", "fecab", "cgb", "gbdefca", "cg", "fgcdab", "egfdb", "bfceg"),
                "gbdfcae bgc cg cgb"),
            CorruptedDisplay(listOf("gcafb", "gcf", "dcaebfg", "ecagb", "gf", "abcdeg", "gaef", "cafbge", "fdbac", "fegbdc"),
                "fgae cfgab fg bagce"))

        val result = sumAllDecodedOutputValues(corruptedDisplays)

        assertThat(result).isEqualTo(61229)
    }

    @Nested
    inner class CorruptedDisplayTest {
        private val target = CorruptedDisplay(
            listOf("acedgfb", "cdfbe", "gcdfa", "fbcad", "dab", "cefabd", "cdfgeb", "eafb", "cagedb", "ab"),
            "cdfeb fcadb cdfeb cdbaf"
        )

        @Test
        fun `should correctly find the matching digit patterns`() {
            val expected = mapOf(
                "cagedb" to 0,
                "ab" to 1,
                "gcdfa" to 2,
                "fbcad" to 3,
                "eafb" to 4,
                "cdfbe" to 5,
                "cdfgeb" to 6,
                "dab" to 7,
                "acedgfb" to 8,
                "cefabd" to 9
            )

            assertThat(target.matchedDigitPatterns).isEqualTo(expected)
        }

        @Test
        fun `should correctly decode the four digit output value`() {
            assertThat(target.decodedOutputValue).isEqualTo(5353)
        }
    }
}
