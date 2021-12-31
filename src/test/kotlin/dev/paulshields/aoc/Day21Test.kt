package dev.paulshields.aoc

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day21Test {
    private val playerOneStartingPosition = 4
    private val playerTwoStartingPosition = 8

    @Test
    fun `should correctly play the game with deterministic die`() {
        val result = playDiracDiceWithDeterminateDie(playerOneStartingPosition, playerTwoStartingPosition)

        assertThat(result.playerOneScore).isEqualTo(1000)
        assertThat(result.playerTwoScore).isEqualTo(745)
        assertThat(result.numberOfDieRolls).isEqualTo(993)
    }

    @Test
    fun `should correctly count the result of all realities when playing dirac dice with the quantum die`() {
        val result = playDiracDiceWithQuantumDie(playerOneStartingPosition, playerTwoStartingPosition)

        assertThat(result).all {
            contains(Player.PLAYER_ONE, 444356092776315)
            contains(Player.PLAYER_TWO, 341960390180808)
        }
    }

    @Nested
    inner class DeterministicDieTest {
        val target = DeterministicDie()

        @Test
        fun `should role die incrementally`() {
            assertThat(target.rollDie()).isEqualTo(1)
            assertThat(target.rollDie()).isEqualTo(2)
            assertThat(target.rollDie()).isEqualTo(3)
        }

        @Test
        fun `should reset back to 1 after 100 is rolled`() {
            (0 until 100).forEach { _ -> target.rollDie() }

            assertThat(target.rollDie()).isEqualTo(1)
            assertThat(target.rollDie()).isEqualTo(2)
        }

        @Test
        fun `should correctly count number of die rolls`() {
            (0 until 3).forEach { _ -> target.rollDie() }

            assertThat(target.numberOfRolls).isEqualTo(3)
        }
    }
}
