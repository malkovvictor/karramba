package ru.victormalkov.karramba

import ru.victormalkov.karramba.model.Board

class GameController {
    var board: Board? = null

    fun create() {
        board = readBoardFromFile("level1")

    }

}