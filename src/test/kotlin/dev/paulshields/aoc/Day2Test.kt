package dev.paulshields.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day2Test {
    @Test
    fun `should correctly process the dive commands when using the depth algorithm`() {
        val input = listOf("forward 5", "down 5", "forward 8", "up 3", "down 8", "forward 2")

        val output = processDiveCommandsByDepth(input)

        assertThat(output).isEqualTo(DivePosition(15, 10))
    }

    @Test
    fun `should correctly process the dive commands when using the aim algorithm`() {
        val input = listOf("forward 5", "down 5", "forward 8", "up 3", "down 8", "forward 2")

        val output = processDiveCommandsByAim(input)

        assertThat(output.horizontalPosition).isEqualTo(15)
        assertThat(output.depth).isEqualTo(60)
    }

    @Nested
    inner class DivePositionTest {
        private val position = DivePosition(5, 15)

        @Test
        fun `should move dive position forward`() {
            val output = position.moveForward(10)

            assertThat(output.horizontalPosition).isEqualTo(15)
            assertThat(output.depth).isEqualTo(15)
        }

        @Test
        fun `should move dive position down`() {
            val output = position.diveDown(10)

            assertThat(output.horizontalPosition).isEqualTo(5)
            assertThat(output.depth).isEqualTo(25)
        }

        @Test
        fun `should move dive position up`() {
            val output = position.riseUp(10)

            assertThat(output.horizontalPosition).isEqualTo(5)
            assertThat(output.depth).isEqualTo(5)
        }
    }

    @Nested
    inner class DiveAimPositionTest {
        private val position = DiveAimPosition(5, 50, 0)

        @Test
        fun `should move dive position forward`() {
            val output = position.moveForward(10)

            assertThat(output.horizontalPosition).isEqualTo(15)
            assertThat(output.depth).isEqualTo(50)
        }

        @Test
        fun `should angle and move dive position down`() {
            var output = position.aimDown(5)
            output = output.moveForward(5)

            assertThat(output.horizontalPosition).isEqualTo(10)
            assertThat(output.depth).isEqualTo(75)
        }

        @Test
        fun `should angle and move dive position up`() {
            var output = position.aimUp(5)
            output = output.moveForward(5)

            assertThat(output.horizontalPosition).isEqualTo(10)
            assertThat(output.depth).isEqualTo(25)
        }
    }
}
