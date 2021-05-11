package org.carrat.demo.tictactoe.model

import org.carrat.flow.Flow
import org.carrat.flow.list.list
import org.carrat.flow.property.property
import org.carrat.model.SubscribableReference
import org.carrat.model.list.SubscribableList

class Game(private val flow: Flow) {
    private val cells = flow.query { Array(9) { flow.property<CellState>(CellState.Empty) } }
    private var _winningRow = flow.property<WinningRow?>(null)
    private var _gameState = flow.property<GameState>(GameState.Ongoing(Player.X))
    private val _history = flow.list<HistoryEntry>()
    val winningRow: SubscribableReference<WinningRow?>
        get() = _winningRow
    val gameState: SubscribableReference<GameState>
        get() = _gameState
    val history: SubscribableList<HistoryEntry>
        get() = _history


    operator fun get(position: Position) = cells[position.ordinal].value
    operator fun set(position: Position, value: CellState) {
        cells[position.ordinal].value = value
    }

    fun play(position: Position) {
        val gameState = _gameState.value
        if(gameState is GameState.Ongoing) {
            val cell = cells[position.ordinal]
            val player = gameState.nextPlayer
            if (cell.value == CellState.Empty) {
                val cellState = CellState.Marked(player)
                val boardState = boardStateAfterPlay(position, cellState)
                val winningRow = boardState.winningRow()
                _winningRow.value = winningRow
                cell.value = cellState
                _gameState.value = if(winningRow != null) {
                    GameState.Finished(winningRow.winner)
                } else if(boardState.full()) {
                    GameState.Finished(null)
                } else {
                    GameState.Ongoing(player.other)
                }
                _history.add(HistoryEntry(boardState, position))
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

    fun getCell(position: Position): SubscribableReference<CellState> = cells[position.ordinal]

    fun reset() {
        flow.apply {
            cells.forEach { it.value = CellState.Empty }
            _gameState.value = GameState.Ongoing(Player.X)
            _winningRow.value = null
            _history.clear()
        }
    }
}