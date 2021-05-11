package org.carrat.demo.tictactoe.style

import kotlinx.css.*
import org.carrat.web.css.StyleSheet

private val xImage = Image("url(/static/X.svg)")
private val oImage = Image("url(/static/O.svg)")

object BoardStyleSheet : StyleSheet("board") {
    val boardContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.center
        alignItems = Align.center
        alignContent = Align.center
        flexGrow = 1.0
    }

    val boardStyle by css {
        margin(10.px)
        borderCollapse = BorderCollapse.collapse
    }

    val smallBoardStyle by css {
        borderCollapse = BorderCollapse.collapse
    }

    val cell by css {
        borderColor = Color.white
        borderWidth = 3.px
        borderStyle = BorderStyle.solid
        width = 100.px
        height = 100.px
    }

    val smallCell by css {
        borderColor = Color.white
        borderWidth = 2.px
        borderStyle = BorderStyle.solid
        width = 30.px
        height = 30.px
    }

    private val cellStyle : RuleSet = {
        backgroundSize = "80%"
        backgroundRepeat = BackgroundRepeat.noRepeat
        backgroundPosition = "center"
    }

    val lastMoveColor = Color.yellow

    val lastMoveCell by css {
        borderColor = lastMoveColor
    }

    val lastMoveCellLeftOf by css {
        borderRightColor = lastMoveColor
    }

    val lastMoveCellAbove by css {
        borderBottomColor = lastMoveColor
    }

    val x by css {
        +cellStyle
        backgroundImage = xImage
    }

    val o by css {
        +cellStyle
        backgroundImage = oImage
    }

    val winningRowCell by css {
        backgroundColor = Color("#0000ff80")
    }

    val headerStyle by css {
        fontSize = 48.px
        margin(10.px)
    }

    val playerStyle by css {
        width = 48.px
        height = 48.px
        verticalAlign = VerticalAlign.middle
        display = Display.inlineBlock
    }

    val smallPlayerStyle by css {
        width = 28.px
        height = 28.px
        verticalAlign = VerticalAlign.middle
        display = Display.inlineBlock
        margin(0.px, 3.px)
    }

    val xPlayer by css {
        backgroundImage = xImage
    }

    val oPlayer by css {
        backgroundImage = oImage
    }

    val resetButton by css {
        margin(10.px)
        fontSize = 24.px
        backgroundColor = Color.white
        border = "none"
        borderRadius = 3.px
        padding(10.px)
        color = Color("#20242b")
    }

    val historyContainer by css {
        width = 300.px
        backgroundColor = Color("#303338")
        padding(10.px)
        height = 100.pct
        overflowY = Overflow.auto
    }

    val historyHeader by css {
        fontSize = 24.px
    }

    val historyEntry by css {
        display = Display.flex
        justifyContent = JustifyContent.stretch
        alignItems = Align.center
        margin(10.px)
    }
    val historyEntryText by css {
        flexGrow = 1.0
        fontSize = 24.px
        textAlign = TextAlign.center
        marginRight = 20.px
    }
}
