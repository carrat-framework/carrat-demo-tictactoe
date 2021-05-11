package org.carrat.demo.tictactoe.model

enum class HorizontalPosition {
    LEFT {
        override fun leftOf() = null
    },
    CENTER {
        override fun leftOf() = LEFT
    },
    RIGHT {
        override fun leftOf() = CENTER
    };

    abstract fun leftOf(): HorizontalPosition?
}