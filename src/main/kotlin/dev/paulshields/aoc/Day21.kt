/**
 * Day 21: Dirac Dice
 */

package dev.paulshields.aoc

import dev.paulshields.aoc.common.readFileAsStringList

fun main() {
    println(" ** Day 21: Dirac Dice ** \n")

    val diracDiceStaringPositionsText = readFileAsStringList("/Day21StartingPositions.txt")
    val playerOnePosition = diracDiceStaringPositionsText[0].split(" ").last().toInt()
    val playerTwoPosition = diracDiceStaringPositionsText[1].split(" ").last().toInt()

    val gameResults = playDiracDiceWithDeterminateDie(playerOnePosition, playerTwoPosition)
    val finalCalculation = when (gameResults.winner) {
        Player.PLAYER_ONE -> gameResults.playerTwoScore * gameResults.numberOfDieRolls
        else -> gameResults.playerOneScore * gameResults.numberOfDieRolls
    }
    println("The final result after playing the game with a deterministic die is $finalCalculation")

    val resultsFromAllRealities = playDiracDiceWithQuantumDie(playerOnePosition, playerTwoPosition)
    val result = resultsFromAllRealities.map { it.value }.maxOf { it }
    println("When playing with the quantum die, the player who wins the most wins in $result universes.")
}

fun playDiracDiceWithDeterminateDie(playerOneStartingPosition: Int, playerTwoStartingPosition: Int): DiracDiceResults {
    var playerOnePosition = playerOneStartingPosition - 1
    var playerTwoPosition = playerTwoStartingPosition - 1
    var playerOneScore = 0
    var playerTwoScore = 0
    val die = DeterministicDie()

    var playerTurn = Player.PLAYER_ONE

    while (playerOneScore < 1000 && playerTwoScore < 1000) {
        val steps = die.rollDie() + die.rollDie() + die.rollDie()
        if (playerTurn == Player.PLAYER_ONE) {
            playerOnePosition = (playerOnePosition + steps) % 10
            playerOneScore += playerOnePosition + 1
            playerTurn = Player.PLAYER_TWO
        } else {
            playerTwoPosition = (playerTwoPosition + steps) % 10
            playerTwoScore += playerTwoPosition + 1
            playerTurn = Player.PLAYER_ONE
        }
    }

    return DiracDiceResults(playerOneScore, playerTwoScore, die.numberOfRolls)
}

data class DiracDiceResults(val playerOneScore: Int, val playerTwoScore: Int, val numberOfDieRolls: Int) {
    val winner = if (playerOneScore >= 1000) Player.PLAYER_ONE else Player.PLAYER_TWO
}

class DeterministicDie {
    var numberOfRolls = 0
        private set

    private var lastRole = -1

    fun rollDie(): Int {
        ++numberOfRolls
        lastRole = (lastRole + 1) % 100
        return lastRole + 1
    }
}

fun playDiracDiceWithQuantumDie(playerOneStartingPosition: Int, playerTwoStartingPosition: Int): Map<Player, Long> {
    val initialGameData = DiracDiceGameData(
        0,
        playerOneStartingPosition - 1,
        0,
        playerTwoStartingPosition - 1,
        Player.PLAYER_ONE)
    val memoizeData = mutableMapOf<Pair<DiracDiceGameData, Int>, Map<Player, Long>>()

    val resultsFromAllUniverses = allPossibleQuantumDieRolls().map { playDiracDiceWithQuantumDie(initialGameData, it, memoizeData) }

    return sumNumericMaps(resultsFromAllUniverses)
}

private fun playDiracDiceWithQuantumDie(
    gameData: DiracDiceGameData,
    nextRole: Int,
    memoizeData: MutableMap<Pair<DiracDiceGameData, Int>, Map<Player, Long>>): Map<Player, Long> {
    memoizeData[gameData to nextRole]?.let { return it }

    val gameDataAfterMove = gameData.moveNextPlayerBy(nextRole)

    if (gameDataAfterMove.playerOneScore >= 21) {
        return mapOf(Player.PLAYER_ONE to 1, Player.PLAYER_TWO to 0)
    } else if (gameDataAfterMove.playerTwoScore >= 21) {
        return mapOf(Player.PLAYER_ONE to 0, Player.PLAYER_TWO to 1)
    }

    val resultsFromAllUniverses = allPossibleQuantumDieRolls()
        .map { playDiracDiceWithQuantumDie(gameDataAfterMove, it, memoizeData) }

    return sumNumericMaps(resultsFromAllUniverses)
        .also { memoizeData[gameData to nextRole] = it }
}

private fun allPossibleQuantumDieRolls() =
    (1..3).flatMap { one ->
        (1..3).flatMap { two ->
            (1..3).map { one + two + it }
        }
    }

private fun sumNumericMaps(maps: List<Map<Player, Long>>) =
    maps.first()
        .keys
        .associateWith { player ->
            maps.mapNotNull { it[player] }.sumOf { it }
        }

data class DiracDiceGameData(
    val playerOneScore: Int,
    val playerOnePosition: Int,
    val playerTwoScore: Int,
    val playerTwoPosition: Int,
    val nextPlayerToMove: Player) {

    fun moveNextPlayerBy(steps: Int) =
        if (nextPlayerToMove == Player.PLAYER_ONE) {
            val playerPosition = (playerOnePosition + steps) % 10
            val playerScore = playerOneScore + playerPosition + 1
            DiracDiceGameData(playerScore, playerPosition, playerTwoScore, playerTwoPosition, Player.PLAYER_TWO)
        } else {
            val playerPosition = (playerTwoPosition + steps) % 10
            val playerScore = playerTwoScore + playerPosition + 1
            DiracDiceGameData(playerOneScore, playerOnePosition, playerScore, playerPosition, Player.PLAYER_ONE)
        }
}

enum class Player {
    PLAYER_ONE, PLAYER_TWO
}
