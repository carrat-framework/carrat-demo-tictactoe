package org.carrat.demo.tictactoe.model

enum class VerticalPosition {
    TOP {
        override fun above() = null
    },
    MIDDLE {
        override fun above() = TOP
    },
    BOTTOM {
        override fun above() = MIDDLE
    };

    abstract fun above(): VerticalPosition?
}