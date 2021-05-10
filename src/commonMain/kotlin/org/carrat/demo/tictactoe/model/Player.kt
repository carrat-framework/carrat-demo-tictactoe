package org.carrat.demo.tictactoe.model

enum class Player {
    X {
        override val other: Player
            get() = O
    },
    O {
        override val other: Player
            get() = X
    };

    abstract val other : Player
}