package org.carrat.demo.tictactoe.site

import org.carrat.demo.tictactoe.model.*
import org.carrat.demo.tictactoe.style.BoardStyleSheet.boardContainer
import org.carrat.demo.tictactoe.style.BoardStyleSheet.boardStyle
import org.carrat.demo.tictactoe.style.BoardStyleSheet.cell
import org.carrat.demo.tictactoe.style.BoardStyleSheet.headerStyle
import org.carrat.demo.tictactoe.style.BoardStyleSheet.o
import org.carrat.demo.tictactoe.style.BoardStyleSheet.oPlayer
import org.carrat.demo.tictactoe.style.BoardStyleSheet.playerStyle
import org.carrat.demo.tictactoe.style.BoardStyleSheet.resetButton
import org.carrat.demo.tictactoe.style.BoardStyleSheet.winningRowCell
import org.carrat.demo.tictactoe.style.BoardStyleSheet.x
import org.carrat.demo.tictactoe.style.BoardStyleSheet.xPlayer
import org.carrat.flow.flow
import org.carrat.model.SubscribableReference
import org.carrat.web.builder.html.*
import org.carrat.web.webapi.HTMLTableCellElement
import org.carrat.web.webapi.classSet

private fun BlockConsumer.player(player: Player) {
    div {
        css {
            classes += playerStyle()
            classes += when (player) {
                Player.X -> xPlayer()
                Player.O -> oPlayer()
            }
        }
    }
}

val displayGame: BlockContent = {
    div {
        css {
            classes += boardContainer()
        }
        div {
            css {
                classes += headerStyle()
            }
            dynamic(game.gameState) {
                when (it) {
                    is GameState.Finished -> {
                        if (it.winner != null) {
                            player(it.winner)
                            text(" won!")
                        } else {
                            text("Draw!")
                        }
                    }
                    is GameState.Ongoing -> {
                        text("Current player: ")
                        player(it.nextPlayer)
                    }
                }
            }
        }
        table {
            css {
                classes += boardStyle()
            }
            tbody {
                VerticalPosition.values().forEach { y ->
                    tr {
                        HorizontalPosition.values().forEach { x ->
                            val position = Position(x, y)
                            td {
                                val cell = game.getCell(position)
                                val cssClasses: SubscribableReference<Set<String>> = flow.run {
                                    query {
                                        lazyMap {
                                            val cssClasses = mutableSetOf(cell())
                                            when ((cell.value as? CellState.Marked)?.player) {
                                                Player.X -> cssClasses += x()
                                                Player.O -> cssClasses += o()
                                            }
                                            if (game.winningRow.value?.row?.contains(position) == true) {
                                                cssClasses += winningRowCell()
                                            }
                                            cssClasses
                                        }.asProperty()
                                    }
                                }
                                bind(HTMLTableCellElement::classSet, cssClasses)
                                onClick {
                                    game.play(position)
                                }
                            }
                        }
                    }
                }
            }
        }
        button {
            css {
                classes += resetButton()
            }
            +"Reset"
            onClick {
                game.reset()
            }
        }
    }
}
