package ru.victormalkov.karramba

import kotlinx.serialization.Serializable

@Serializable
class Board(val width: Int, val height: Int) {
    internal val cells: Array<Array<Cell>> = Array(width) {Array(height) {Cell()} }

    fun hasMoves(): Boolean {
        //TODO
        return true
    }

    internal fun fall() {

    }

    internal fun shuffle() {

    }

    // TODO мб это в контроллере должно быть??
    fun processMove(x: Int, y: Int, direction: Direction) {

    }
}

enum class Direction {
    UP, RIGHT, DOWN, LEFT
}
