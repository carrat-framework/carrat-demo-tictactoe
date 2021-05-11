package org.carrat.demo.tictactoe.model

sealed class CellState {
    object Empty : CellState()
    data class Marked(
        val player: Player
    ) : CellState()
}

val CellState.player : Player? get() = (this as? CellState.Marked)?.player