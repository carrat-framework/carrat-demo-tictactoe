package org.carrat.demo.tictactoe.style

import kotlinx.css.*
import org.carrat.web.css.StyleSheet

object LoadingStyleSheet : StyleSheet("loading") {
    val loadingOverlayStyle by css {
        position = Position.fixed
        zIndex = 1
        backgroundColor = Color("#20242ba0")
        width = 100.pct
        height = 100.vh
        display = Display.flex
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.center
        alignItems = Align.center
    }
    val loadingBoxStyle by css {
        borderColor = Color.white
        borderWidth = 1.px
        borderStyle = BorderStyle.solid
        backgroundColor = Color("#20242b")
        borderRadius = 5.px
        padding(20.px, 30.px)
        color = Color.white
        fontSize = 24.px
        textAlign = TextAlign.center
    }
}
