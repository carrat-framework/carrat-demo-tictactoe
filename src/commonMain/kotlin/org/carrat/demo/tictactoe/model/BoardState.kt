package org.carrat.demo.tictactoe.model

class BoardState private constructor(
    private val cells : Array<CellState>
) {
    operator fun get(position: Position) = cells[position.ordinal]

    companion object {
        operator fun invoke(cells : Array<CellState>) = BoardState(cells.copyOf())
        operator fun invoke(cells : List<CellState>) = BoardState(cells.toTypedArray())
    }
}