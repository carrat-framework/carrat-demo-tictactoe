package org.carrat.demo.tictactoe.model

import org.carrat.flow.Flow
import org.carrat.flow.property.property
import org.carrat.model.SubscribableReference

class Game(val flow: Flow) {
    private val cells = flow.query { Array(9) { flow.property<CellState>(CellState.Empty) } }
    private var turn = 1
    private var _currentPlayer = flow.query { flow.property(Player.X) }
    private var _winningRow = flow.query { flow.property<WinningRow?>(null) }
    private var _gameState = flow.query { flow.property<GameState>(GameState.Ongoing(Player.X)) }
    val currentPlayer: SubscribableReference<Player>
        get() = _currentPlayer
    val winningRow: SubscribableReference<WinningRow?>
        get() = _winningRow
    val gameState: SubscribableReference<GameState>
        get() = _gameState


    operator fun get(position: Position) = cells[position.ordinal].value
    operator fun set(position: Position, value: CellState) {
        cells[position.ordinal].value = value
    }

    fun play(position: Position) {
        if(_gameState.value is GameState.Ongoing) {
            val cell = cells[position.ordinal]
            val player = _currentPlayer.value
            if (cell.value == CellState.Empty) {
                val cellState = CellState.Marked(player, turn++)
                val boardState = boardStateAfterPlay(position, cellState)
                val winningRow = boardState.winningRow()
                _winningRow.value = winningRow
                cell.value = cellState
                _currentPlayer.value = player.other
                _gameState.value = if(winningRow != null) {
                    GameState.Finished(winningRow.winner)
                } else if(boardState.full()) {
                    GameState.Finished(null)
                } else {
                    GameState.Ongoing(player.other)
                }
            }
        }
    }

    private fun boardStateAfterPlay(position: Position, cellState: CellState): BoardState {
        return BoardState(cells.withIndex().map { (index, value) ->
            if (position.ordinal == index) {
                cellState
            } else {
                value.value
            }
        })
    }

    private fun BoardState.winningRow(): WinningRow? {
        return Row.values.firstNotNullOfOrNull { match(it) }
    }

    private fun BoardState.full(): Boolean {
        return Position.values.all { this[it] is CellState.Marked }
    }

    private fun BoardState.match(row: Row): WinningRow? {
        val firstCellValue = this[row.first].player
        return if (firstCellValue != null && firstCellValue == this[row.second].player && firstCellValue == this[row.third].player) {
            WinningRow(row, firstCellValue)
        } else {
            null
        }
    }

    private val CellState.player : Player? get() = (this as? CellState.Marked)?.player

    fun getCell(position: Position): SubscribableReference<CellState> = cells[position.ordinal]

    fun reset() {
        flow.apply {
            cells.forEach { it.value = CellState.Empty }
            turn = 1
            _currentPlayer.value = Player.X
            _gameState.value = GameState.Ongoing(Player.X)
            _winningRow.value = null
        }
    }
}