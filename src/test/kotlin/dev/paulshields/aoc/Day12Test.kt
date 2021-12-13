package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import dev.paulshields.aoc.testcommon.parameterizedTest
import dev.paulshields.aoc.testcommon.runTest
import org.junit.jupiter.api.TestFactory

class Day12Test {
    private val smallExample = "start-A\n" +
            "start-b\n" +
            "A-c\n" +
            "A-b\n" +
            "b-d\n" +
            "A-end\n" +
            "b-end"

    private val mediumExample = "dc-end\n" +
            "HN-start\n" +
            "start-kj\n" +
            "dc-start\n" +
            "dc-HN\n" +
            "LN-dc\n" +
            "HN-end\n" +
            "kj-sa\n" +
            "kj-HN\n" +
            "kj-dc"

    private val largeExample = "fs-end\n" +
            "he-DX\n" +
            "fs-he\n" +
            "start-DX\n" +
            "pj-DX\n" +
            "end-zg\n" +
            "zg-sl\n" +
            "zg-pj\n" +
            "pj-he\n" +
            "RW-he\n" +
            "fs-DX\n" +
            "pj-RW\n" +
            "zg-RW\n" +
            "start-pj\n" +
            "he-WI\n" +
            "zg-he\n" +
            "pj-fs\n" +
            "start-RW"

    @TestFactory
    fun `should correctly count all paths through cave system`() = parameterizedTest(
        smallExample to 10,
        mediumExample to 19,
        largeExample to 226)
        .runTest { (caveConnections, numberOfPaths) ->
            val result = countAllPossiblePathsThroughCaveSystem(caveConnections)

            assertThat(result).isEqualTo(numberOfPaths)
        }

    @TestFactory
    fun `should correctly count all paths through cave system when allowed to visit single small cave twice`() = parameterizedTest(
        smallExample to 36,
        mediumExample to 103,
        largeExample to 3509)
        .runTest { (caveConnections, numberOfPaths) ->
            val result = countAllPossiblePathsThroughCaveSystemWithSingleDuplicatedSmallCaveAllowed(caveConnections)

            assertThat(result).isEqualTo(numberOfPaths)
        }
}
