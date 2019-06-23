package ru.victormalkov.karramba

import java.io.File

fun readBoardFromFile(filename: String): Board {
    val reader = File(filename).bufferedReader()
    val (width, height) = reader.readLine()!!.split(" ").map(String::toInt)
    val result = Board(0, 0)


    return result
}

// для тестов
fun printBoard(board: Board) {

}