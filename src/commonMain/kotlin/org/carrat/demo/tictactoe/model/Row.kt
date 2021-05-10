package org.carrat.demo.tictactoe.model

class Row private constructor(
    val first: Position,
    val second: Position,
    val third: Position
) {
    fun contains(position: Position): Boolean {
        return first == position ||
                second == position ||
                third == position
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Row

        if (first != other.first) return false
        if (second != other.second) return false
        if (third != other.third) return false

        return true
    }

    override fun hashCode(): Int {
        var result = first.hashCode()
        result = 31 * result + second.hashCode()
        result = 31 * result + third.hashCode()
        return result
    }

    companion object {
        val values: Set<Row> =
            VerticalPosition.values().map {
                Row(
                    Position(HorizontalPosition.LEFT, it),
                    Position(HorizontalPosition.CENTER, it),
                    Position(HorizontalPosition.RIGHT, it)
                )
            }.toSet() +
                    HorizontalPosition.values().map {
                        Row(
                            Position(it, VerticalPosition.TOP),
                            Position(it, VerticalPosition.MIDDLE),
                            Position(it, VerticalPosition.BOTTOM)
                        )
                    }.toSet() +
                    setOf(
                        Row(
                            Position(HorizontalPosition.LEFT, VerticalPosition.TOP),
                            Position(HorizontalPosition.CENTER, VerticalPosition.MIDDLE),
                            Position(HorizontalPosition.RIGHT, VerticalPosition.BOTTOM)
                        ),
                        Row(
                            Position(HorizontalPosition.LEFT, VerticalPosition.BOTTOM),
                            Position(HorizontalPosition.CENTER, VerticalPosition.MIDDLE),
                            Position(HorizontalPosition.RIGHT, VerticalPosition.TOP)
                        )
                    )
    }
}