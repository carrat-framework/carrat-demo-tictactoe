package org.carrat.demo.tictactoe.site

import kotlinx.html.*
import org.carrat.demo.tictactoe.model.*
import org.carrat.demo.tictactoe.style.BoardStyleSheet.boardContainer
import org.carrat.demo.tictactoe.style.BoardStyleSheet.boardStyle
import org.carrat.demo.tictactoe.style.BoardStyleSheet.cell
import org.carrat.demo.tictactoe.style.BoardStyleSheet.headerStyle
import org.carrat.demo.tictactoe.style.BoardStyleSheet.o
import org.carrat.demo.tictactoe.style.BoardStyleSheet.oPlayer
import org.carrat.demo.tictactoe.style.BoardStyleSheet.resetButton
import org.carrat.demo.tictactoe.style.BoardStyleSheet.winningRowCell
import org.carrat.demo.tictactoe.style.BoardStyleSheet.x
import org.carrat.demo.tictactoe.style.BoardStyleSheet.xPlayer
import org.carrat.flow.flow
import org.carrat.model.SubscribableReference
import org.carrat.web.builder.*

private fun CBuilder.player(player: Player) {
    tag(::DIV) {
        css {
            classes += when (player) {
                Player.X -> xPlayer()
                Player.O -> oPlayer()
            }
        }
    }
}

val displayGame: CBlock = {
    tag(::DIV) {
        css {
            classes += boardContainer()
        }
        tag(::DIV) {
            css {
                classes += headerStyle()
            }
            dynamic(game.gameState) {
                when (it) {
                    is GameState.Finished -> {
                        if (it.winner != null) {
                            player(it.winner)
                            +" won!"
                        } else {
                            +"Draw!"
                        }
                    }
                    is GameState.Ongoing -> {
                        +"Current player: "
                        player(it.nextPlayer)
                    }
                }
            }
        }
        tag(::TABLE) {
            css {
                classes += boardStyle()
            }
            tag(::TBODY) {
                VerticalPosition.values().forEach { y ->
                    tag(::TR) {
                        HorizontalPosition.values().forEach { x ->
                            val position = Position(x, y)
                            tag(::TD) {
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
                                bind(TD::classes, cssClasses)
                                onTagEvent(tag, "click") {
                                    game.play(position)
                                }
                            }
                        }
                    }
                }
            }
        }
        tag(::BUTTON) {
            css {
                classes += resetButton()
            }
            +"Reset"
            onTagEvent(tag, "click") {
                game.reset()
            }
        }
    }
}
