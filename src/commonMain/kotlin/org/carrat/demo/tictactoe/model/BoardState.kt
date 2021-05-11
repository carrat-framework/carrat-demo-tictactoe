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

fun BoardState.winningRow(): WinningRow? {
    return Row.values.firstNotNullOfOrNull { match(it) }
}

private fun BoardState.match(row: Row): WinningRow? {
    val firstCellValue = this[row.first].player
    return if (firstCellValue != null && firstCellValue == this[row.second].player && firstCellValue == this[row.third].player) {
        WinningRow(row, firstCellValue)
    } else {
        null
    }
}

fun BoardState.full(): Boolean {
    return Position.values.all { this[it] is CellState.Marked }
}