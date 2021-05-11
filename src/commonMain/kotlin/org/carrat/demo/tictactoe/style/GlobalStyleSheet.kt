package org.carrat.demo.tictactoe.style

import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.s
import kotlinx.css.properties.transition
import org.carrat.web.css.StyleSheet

object GlobalStyleSheet : StyleSheet("global", {
    "*" {
        boxSizing = BoxSizing.borderBox
        margin(0.px)
    }

    a {
        color = Color.inherit
        textDecoration = TextDecoration.none
    }

    body {
        fontFamily = "'Inter', sans-serif"
        backgroundColor = Color("#20242b")
        color = Color.white
        margin(0.px)
        display = Display.flex
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.stretch
        alignItems = Align.stretch
        height = 100.vh
        alignContent = Align.stretch
        userSelect = UserSelect.none
    }

})
