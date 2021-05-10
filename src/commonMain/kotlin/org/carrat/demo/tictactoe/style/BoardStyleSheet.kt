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

    val cell by css {
        borderColor = Color.white
        borderWidth = 3.px
        borderStyle = BorderStyle.solid
        width = 100.px
        height = 100.px
    }

    private val cellStyle : RuleSet = {
        backgroundSize = "80%"
        backgroundRepeat = BackgroundRepeat.noRepeat
        backgroundPosition = "center"
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

    private val playerStyle : RuleSet = {
        width = 48.px
        height = 48.px
        verticalAlign = VerticalAlign.middle
        display = Display.inlineBlock
    }

    val xPlayer by css {
        +playerStyle
        backgroundImage = xImage
    }

    val oPlayer by css {
        +playerStyle
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
}
