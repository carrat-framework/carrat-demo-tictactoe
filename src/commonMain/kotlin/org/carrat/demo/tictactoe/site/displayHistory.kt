package org.carrat.demo.tictactoe.site

import org.carrat.demo.tictactoe.model.*
import org.carrat.demo.tictactoe.style.BoardStyleSheet.historyContainer
import org.carrat.demo.tictactoe.style.BoardStyleSheet.historyEntry
import org.carrat.demo.tictactoe.style.BoardStyleSheet.historyEntryText
import org.carrat.demo.tictactoe.style.BoardStyleSheet.historyHeader
import org.carrat.demo.tictactoe.style.BoardStyleSheet.lastMoveCell
import org.carrat.demo.tictactoe.style.BoardStyleSheet.lastMoveCellAbove
import org.carrat.demo.tictactoe.style.BoardStyleSheet.lastMoveCellLeftOf
import org.carrat.demo.tictactoe.style.BoardStyleSheet.o
import org.carrat.demo.tictactoe.style.BoardStyleSheet.oPlayer
import org.carrat.demo.tictactoe.style.BoardStyleSheet.smallBoardStyle
import org.carrat.demo.tictactoe.style.BoardStyleSheet.smallCell
import org.carrat.demo.tictactoe.style.BoardStyleSheet.smallPlayerStyle
import org.carrat.demo.tictactoe.style.BoardStyleSheet.winningRowCell
import org.carrat.demo.tictactoe.style.BoardStyleSheet.x
import org.carrat.demo.tictactoe.style.BoardStyleSheet.xPlayer
import org.carrat.web.builder.html.*

private fun BlockConsumer.player(player: Player) {
    div {
        css {
            classes += smallPlayerStyle()
            classes += when (player) {
                Player.X -> xPlayer()
                Player.O -> oPlayer()
            }
        }
    }
}

private fun BlockConsumer.displayBoard(boardState: BoardState, lastMove: Position) {
    val winningRow = boardState.winningRow()
    table {
        css {
            classes += smallBoardStyle()
        }
        tbody {
            VerticalPosition.values().forEach { y ->
                tr {
                    HorizontalPosition.values().forEach { x ->
                        val position = Position(x, y)
                        td {
                            css {
                                classes += smallCell()
                                when ((boardState[position] as? CellState.Marked)?.player) {
                                    Player.X -> classes += x()
                                    Player.O -> classes += o()
                                }
                                if (winningRow?.row?.contains(position) == true) {
                                    classes += winningRowCell()
                                }
                                if (position == lastMove) {
                                    classes += lastMoveCell()
                                }
                                if (position == lastMove.above()) {
                                    classes += lastMoveCellAbove()
                                }
                                if (position == lastMove.leftOf()) {
                                    classes += lastMoveCellLeftOf()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

val displayHistory: BlockContent = {
    div {
        css {
            classes += historyContainer()
        }
        div {
            css {
                classes += historyHeader()
            }
            +"History"
        }

        dynamic(game.history) { entry ->
            div {
                css {
                    classes += historyEntry()
                }
                div {
                    css {
                        classes += historyEntryText()
                    }
                    player(entry.boardState[entry.lastMove].player!!)
                    +" "
                    +when {
                        entry.boardState.winningRow() != null -> "wins!"
                        entry.boardState.full() -> "draws!"
                        else -> "plays"
                    }
                }
                displayBoard(entry.boardState, entry.lastMove)
            }
        }
    }
}
