package ru.victormalkov.karramba

import com.badlogic.gdx.Gdx
import ru.victormalkov.karramba.model.Board
import ru.victormalkov.karramba.model.StaticWall
import ru.victormalkov.karramba.model.TransparentWall
import java.io.File

fun readBoardFromFile(filename: String): Board {
    val reader = Gdx.files.internal(filename).reader(2048)
    val (width, height) = reader.readLine()!!.split(" ").map(String::toInt)
    val result = Board(width, height)

    for (y in 0 until height) {
        val s = reader.readLine()
        if (s == null || s.length != width) {
            throw Exception("incorrect board width")
        }
        for (x in 0 until width) {
            when (s[x]) {
                '.' -> {
                    // ячейка с камнем
                }
                '#' -> {
                    // непрозрачная стена
                    result.cells[x][y].effect = StaticWall
                }
                'o' -> {
                    // прозрачная невидимая стена
                    result.cells[x][y].effect = TransparentWall
                }
                else -> {
                    println("unknown cell (${x}, ${y}) type: ${s[x]}")
                }
            }
        }
    }

    return result
}

// для тестов
fun printBoard(board: Board) {

}