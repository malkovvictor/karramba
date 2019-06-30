package ru.victormalkov.karramba

import com.badlogic.gdx.Gdx
import ru.victormalkov.karramba.model.*
import kotlin.random.Random

val gemPool: Array<Gem> = Array(ORDINARY_GEM_TYPES) {OrdinaryGem("gem$it")}

fun readBoardFromFile(filename: String): Board {
    val reader = Gdx.files.internal(filename).reader(2048) //todo move to asset manager
    val (width, height) = reader.readLine()!!.split(" ").map(String::toInt)
    val result = Board(width, height)
    val toFill: MutableCollection<Pair<Int, Int>> = LinkedHashSet<Pair<Int, Int>>()

    for (y in 0 until height) {
        val s = reader.readLine()
        if (s == null) {
            throw Exception("incorrect board")
        }
        val items = s.trim().split("\\s+".toRegex())
//        items.forEach{println("item:'$it'")}
        items.forEachIndexed { x, s ->
            with (s) {
                // 1) основной тип ячейки доски
                when {
                    contains('.') -> {
                        // ячейка с камнем
                    }
                    contains('#') -> {
                        // непрозрачная стена
                        result.cells[x][y].effect = StaticWall
                    }
                    contains('o') -> {
                        // прозрачная невидимая стена
                        result.cells[x][y].effect = TransparentWall
                    }
                    contains('~') -> {
                        // стена, через которую могут проваливаться фишки
                        result.cells[x][y].effect = ThruWall
                    }
                    else -> {
                        println("unknown cell ($x, $y) type: ${this}")
                    }
                }

                // 2)
                if (contains('p')) {
                    // producer
                }
                if (contains('c')) {
                    // consumer
                }

                // 3) циферками может быть задан камень
                if (contains('.')) when {
                    contains('0') -> {
                        result.cells[x][y].gem = gemPool[0]
                    }
                    contains('1') -> {
                        result.cells[x][y].gem = gemPool[1]
                    }
                    contains('2') -> {
                        result.cells[x][y].gem = gemPool[2]
                    }
                    contains('3') -> {
                        result.cells[x][y].gem = gemPool[3]
                    }
                    contains('4') -> {
                        result.cells[x][y].gem = gemPool[4]
                    }
                    contains('e') -> {
                        // ячейка при создании должна быть пустой
                    }
                    else -> {
                        // добавить в список на заполнение
                        toFill.add(Pair(x, y))
                    }
                }
            }
        }
    }

    fillEmptyCells(result, toFill)
    return result
}

fun fillEmptyCells(board: Board, toFill: Collection<Pair<Int,Int>>) {
    toFill.forEach {
        val x = it.first
        val y = it.second
        val colors = gemPool.mapTo(mutableListOf()) {it}.filter {
            // если с этим цветом составилась комбинация, забраковать цвет
            with (board) {
                !(
                    (it === g(x-1,y) && it === g(x-2, y)) ||
                            (it === g(x-1, y) && it === g(x + 1, y)) ||
                            (it === g(x+1, y) && it === g(x + 2, y)) ||
                            (it === g(x, y - 1) && it === g(x, y-2)) ||
                            (it === g(x, y - 1) && it === g(x, y + 1)) ||
                            (it === g(x, y + 1) && it === g(x, y+2))
                )
            }
        }
     //   println("cell ($x,$y): ${colors.map { it.name }}" )

        val r = Random.nextInt(colors.size)
        board.cells[x][y].gem = colors[r]
    }
}