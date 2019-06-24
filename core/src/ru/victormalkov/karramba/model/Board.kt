package ru.victormalkov.karramba.model

import kotlinx.serialization.Serializable

@Serializable
class Board(val width: Int, val height: Int) {
    internal val cells: Array<Array<Cell>> = Array(width) {Array(height) { Cell() } }

    fun hasMoves(): Move? {
        //TODO
        return Move(1, 1, Direction.RIGHT)
    }


    // заполнить все производящие ячейки камнями случайного цвета
    fun produce() {

    }

    // уронить камни вниз
    internal fun fall() {

    }

    internal fun shuffle() {

    }

    // TODO мб это в контроллере должно быть??
    fun processMove(x: Int, y: Int, direction: Direction) {

    }

    // заполнить все ячейки, где должен быть камень, камнями случайного цвета
    // так, чтобы не образовалось рядов
    fun fill() {

    }
}
