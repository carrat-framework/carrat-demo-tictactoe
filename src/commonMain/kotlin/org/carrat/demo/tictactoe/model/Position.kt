package org.carrat.demo.tictactoe.model

data class Position(
    val horizontal: HorizontalPosition,
    val vertical: VerticalPosition
) {
    val ordinal: Int get() = horizontal.ordinal * 3 + vertical.ordinal

    companion object {
        val values: Set<Position>
            get() = HorizontalPosition.values().flatMap { x ->
                VerticalPosition.values().map { y -> Position(x, y) }
            }.toSet()
    }
}
