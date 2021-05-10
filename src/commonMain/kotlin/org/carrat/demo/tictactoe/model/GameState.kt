package org.carrat.demo.tictactoe.model

sealed class GameState {
    data class Finished(
        val winner: Player?
    ) : GameState()

    data class Ongoing(
        val nextPlayer: Player
    ) : GameState()
}