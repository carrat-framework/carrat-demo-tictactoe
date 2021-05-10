package org.carrat.demo.tictactoe.model

data class WinningRow(
    val row: Row,
    val winner: Player
)