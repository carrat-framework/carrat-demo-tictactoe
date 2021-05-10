package org.carrat.demo.tictactoe.model

sealed class CellState {
    object Empty : CellState()
    data class Marked(
        val player: Player,
        val turn: Int
    ) : CellState()
}
