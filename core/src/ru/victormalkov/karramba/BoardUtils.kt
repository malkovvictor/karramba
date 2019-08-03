package ru.victormalkov.karramba

import com.badlogic.gdx.Gdx
import ru.victormalkov.karramba.model.*
import kotlin.random.Random

val gemPool: Array<Gem> = Array(ORDINARY_GEM_TYPES) { OrdinaryGem("gem$it") }
val wallPool = listOf(StaticWall("rpgTile159"), StaticWall("rpgTile155"), StaticWall("rpgTile156"), StaticWall("rpgTile160"),
        StaticWall("rpgTile197", "rpgTile177"), StaticWall("rpgTile195", "rpgTile175"),
        StaticWall("rpgTile198", "rpgTile178"),
        StaticWall("rpgTile196", "rpgTile176"))

const val TILE_BOTTOM_LEFT = "rpgTile036"
const val TILE_BOTTOM = "rpgTile037"
const val TILE_BOTTOM_RIGHT = "rpgTile038"
const val TILE_LEFT = "rpgTile018"
const val TILE_RIGHT = "rpgTile020"
const val TILE_TOP_LEFT = "rpgTile000"
const val TILE_TOP = "rpgTile001"
const val TILE_TOP_RIGHT = "rpgTile002"
const val TILE_NORMAL = "rpgTile019"


fun readBoardFromFile(filename: String): Board {
    val TAG = "BoardUtils"
    val reader = Gdx.files.internal(filename).reader(2048) //todo move to asset manager
    val (width, height) = reader.readLine()!!.split(" ").map(String::toInt)
    val result = Board(width, height)
    val toFill: MutableCollection<Pair<Int, Int>> = LinkedHashSet()

    for (y in height - 1 downTo 0) {
        val s = reader.readLine()
        if (s == null) {
            throw Exception("incorrect board")
        }
        val items = s.trim().split("\\s+".toRegex())
        items.forEachIndexed { x, s ->
            with(s) {
                // 1) основной тип ячейки доски
                when {
                    contains('.') -> {
                        // ячейка с камнем
                    }
                    contains('#') -> {
                        // непрозрачная стена
                        result.cells[x][y].effect = wallPool[Random.nextInt(wallPool.size)]
                        println(result.cells[x][y].effect)
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
                        Gdx.app.debug(TAG, "unknown cell ($x, $y) type: ${this}")
                    }
                }

                // 2)
                if (contains('p')) {
                    // producer
                    result.cells[x][y].effect = ProducerCell
                    result.cells[x][y].producer = true
                    Gdx.app.debug(TAG, "cell ($x, $y) is producer")
                }
                if (contains('c')) {
                    // consumer
                    result.cells[x][y].effect = ConsumerCell
                    result.cells[x][y].consumer = true
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
                    contains('5') -> {
                        result.cells[x][y].gem = gemPool[5]
                    }
                    contains('6') -> {
                        result.cells[x][y].gem = gemPool[6]
                    }
                    contains('7') -> {
                        result.cells[x][y].gem = gemPool[7]
                    }
                    contains('8') -> {
                        result.cells[x][y].gem = gemPool[8]
                    }
                    contains('9') -> {
                        result.cells[x][y].gem = gemPool[9]
                    }
                    contains('e') -> {
                        // ячейка при создании должна быть пустой
                    }
                    else -> {
                        // добавить в список на заполнение
                        toFill.add(Pair(x, y))
                    }
                }
                if (result.cells[x][y].gem != null) {
                    result.possibleGemColors.add(result.cells[x][y].gem!!)
                }
            }
        }
    }

    fillEmptyCells(result, toFill)
    return result
}

fun fillEmptyCells(board: Board, toFill: Collection<Pair<Int, Int>>) {
    while (board.possibleGemColors.size < MAX_BOARD_GEM_COLORS) {
        board.possibleGemColors.add(gemPool[Random.nextInt(gemPool.size)])
    }
    toFill.forEach {
        val x = it.first
        val y = it.second
        val colors = board.possibleGemColors.mapTo(mutableListOf()) { it }.filter {
            // если с этим цветом составилась комбинация, забраковать цвет
            with(board) {
                !(
                        (it === g(x - 1, y) && it === g(x - 2, y)) ||
                                (it === g(x - 1, y) && it === g(x + 1, y)) ||
                                (it === g(x + 1, y) && it === g(x + 2, y)) ||
                                (it === g(x, y - 1) && it === g(x, y - 2)) ||
                                (it === g(x, y - 1) && it === g(x, y + 1)) ||
                                (it === g(x, y + 1) && it === g(x, y + 2))
                        )
            }
        }

        val r = Random.nextInt(colors.size)
        board.cells[x][y].gem = colors[r]
    }
}