package ru.victormalkov.karramba.model

import com.badlogic.gdx.Gdx
import kotlinx.serialization.Serializable
import ru.victormalkov.karramba.gemPool
import kotlin.math.abs
import kotlin.random.Random

@Serializable
class Board(val width: Int, val height: Int) {
    val TAG = "Board"
    internal val cells: Array<Array<Cell>> = Array(width) { Array(height) { Cell() } }

    fun c(x: Int, y: Int): Cell? {
        return if (x in 0 until width && y in 0 until height)
            cells[x][y]
        else
            null
    }

    fun g(x: Int, y: Int): Gem? {
        return c(x, y)?.gem
    }

    fun hasMoves(): Move? {
        //TODO
        return Move(1, 1, Direction.RIGHT)
    }


    // заполнить все производящие ячейки камнями случайного цвета
    fun produce(): Boolean {
        var produced = false
        for (x in 0 until width) {
            for (y in 0 until height) {
                val gg = g(x, y)
                val cc = c(x, y)

                if (g(x, y) == null && c(x, y)!!.producer) {
                    c(x, y)!!.gem = gemPool[Random.nextInt(gemPool.size)]
                    Gdx.app.debug(TAG, "produced in cell ($x, $y)")
                    produced = true
                }
            }
        }
        return produced
    }

    private fun checkUp(x: Int, y: Int): Boolean {
        for (t in y+1 until height) {
            if (movable(x, t)) return true
            if (g(x, t) != null || c(x, t)?.effect is Wall) return false
        }
        return false
    }

    // уронить камни вниз
    internal fun fall(): Triple<Pair<Int,Int>,Pair<Int,Int>,Gem>? {
        // на первом проходе роняем только вертикально
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (g(x, y) == null && !(c(x, y)?.effect is Wall)) {
                    Gdx.app.debug(TAG, "found hole at ($x, $y)")
                    if (movable(x, y + 1)) {
                        c(x, y)!!.gem = c(x, y + 1)!!.gem
                        c(x, y + 1)!!.gem = null
                        Gdx.app.debug(TAG, "fall to ($x, $y) from up")
                        return Triple(Pair(x, y + 1), Pair(x, y), c(x, y)!!.gem!!)
                    }
                }
            }
        }

        // на втором проходе пробуем уронить по диагонали
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (g(x, y) == null && !(c(x, y)?.effect is Wall)) {
                    Gdx.app.debug(TAG, "found hole at ($x, $y)")
                    if (movable(x - 1, y + 1)) {
                        c(x, y)!!.gem = c(x - 1, y + 1)!!.gem
                        c(x - 1, y + 1)!!.gem = null
                        Gdx.app.debug(TAG, "fall to ($x, $y) from up-left")
                        return Triple(Pair(x - 1, y + 1), Pair(x, y), c(x, y)!!.gem!!)
                    } else if (movable(x + 1, y + 1)) {
                        c(x, y)!!.gem = c(x + 1, y + 1)!!.gem
                        c(x + 1, y + 1)!!.gem = null
                        Gdx.app.debug(TAG, "fall to ($x, $y) from up-right")
                        return Triple(Pair(x + 1, y + 1), Pair(x, y), c(x, y)!!.gem!!)
                    }
                }
            }
        }

        return null
    }

    internal fun shuffle() {

    }

    private fun stackable(x: Int, y: Int): Boolean {
        return g(x, y) != null &&
                g(x, y)!!.stackable &&
                g(x, y)!!.effect?.stackable != false &&
                c(x, y)!!.effect?.stackable != false
    }

    private fun movable(x: Int, y: Int): Boolean {
        return g(x, y) != null &&
                g(x, y)!!.effect?.movable != false &&
                c(x, y)!!.effect?.movable != false
    }

    private fun test(x1: Int, y1: Int, x2: Int, y2: Int, x3: Int, y3: Int): Boolean {
        val g1 = g(x1, y1)
        val g2 = g(x2, y2)
        val g3 = g(x3, y3)
        return g1 != null && g1 == g2 && g1 == g3 && stackable(x1, y1) && stackable(x2, y2) && stackable(x3, y3)
    }

    fun processMove(sx: Int, sy: Int, dx: Int, dy: Int): Boolean {
        // проверка, что это соседние клетки
        if (abs(sx - dx) + abs(sy - dy) != 1) {
            Gdx.app.debug(TAG, "not adjasent - cannot move")
            return false
        }

        // проверка, что перемещаем в допустимое место
        if (g(dx, dy) is Wall) {
            Gdx.app.debug(TAG, "dest is wall - cannot move")
            return false
        }
        if (g(dx, dy)?.effect?.movable == false) {
            Gdx.app.debug(TAG, "dest gem effect says - cannot move")
            return false
        }
        if (c(dx, dy)!!.effect?.movable == false) {
            Gdx.app.debug(TAG, "dest cell effect says - cannot move")
            return false
        }
        if (!movable(sx, sy)) {
            Gdx.app.debug(TAG, "source gem is not movable, this is strange")
            return false
        }

        // проверка, что составляется комбинация
        var combo = -1
        if (sy == dy) {
            if (test(sx, sy, dx, dy - 1, dx, dy + 1)) combo = 1
            else if (test(sx, sy, dx, dy - 1, dx, dy - 2)) combo = 2
            else if (test(sx, sy, dx, dy + 1, dx, dy + 2)) combo = 3
            else if (test(dx, dy, sx, sy - 1, sx, sy + 1)) combo = 4
            else if (test(dx, dy, sx, sy - 1, sx, sy - 2)) combo = 5
            else if (test(dx, dy, sx, sy + 1, sx, sy + 2)) combo = 6
            else if (sx < dx) {
                if (test(sx, sy, dx + 1, dy, dx + 2, dy)) combo = 7
                else if (test(dx, dy, sx - 1, sy, sx - 2, sy)) combo = 8
            } else { // sx > dx
                if (test(dx, dy, sx + 1, sy, sx + 2, sy)) combo = 9
                else if (test(sx, sy, dx - 1, dy, dx - 2, dy)) combo = 10
            }
        } else { // sx == dx
            if (test(sx, sy, dx - 1, dy, dx + 1, dy)) combo = 11
            else if (test(sx, sy, dx - 2, dy, dx - 1, dy)) combo = 12
            else if (test(sx, sy, dx + 2, dy, dx + 1, dy)) combo = 13
            else if (test(dx, dy, sx - 1, sy, sx + 1, sy)) combo = 14
            else if (test(dx, dy, sx - 2, sy, sx - 1, sy)) combo = 15
            else if (test(dx, dy, sx + 2, sy, sx + 1, sy)) combo = 16
            else if (sy > dy) {
                if (test(sx, sy, dx, dy - 1, dx, dy - 2)) combo = 17
                else if (test(dx, dy, sx, sy + 1, sx, sy + 2)) combo = 18
            } else { // sy < dy
                if (test(dx, dy, sx, sy - 1, sx, sy - 2)) combo = 19
                else if (test(sx, sy, dx, dy + 1, dx, dy + 2)) combo = 20
            }
        }
        if (combo < 0) {
            Gdx.app.debug(TAG, "no combo found -- cannot move")
            return false
        }
        Gdx.app.debug(TAG, "found combo #$combo")

        println("ok to move")
        val tmp = g(sx, sy)
        c(sx, sy)!!.gem = g(dx, dy)
        c(dx, dy)!!.gem = tmp

        return true
    }

    fun removeMatches(): List<Triple<Int, Int, Gem>> {
        val removeList = ArrayList<Triple<Int,Int,Gem>>()
        for (x in 0 until width) {
            for (y in 0 until height) {
                if (stackable(x, y)) {
                    var xx = 1
                    var yy = 1
                    while (stackable(x + xx, y) && g(x + xx, y) == g(x, y)) xx++
                    while (stackable(x, y + yy) && g(x, y + yy) == g(x, y)) yy++
                    if (xx >= 3) {
                        for (x2 in x until x + xx) {
                            removeList.add(Triple(x2, y, c(x2, y)!!.gem!!))
                        }
                    }
                    if (yy >= 3) {
                        for (y2 in y until y + yy) {
                            removeList.add(Triple(x, y2, c(x, y2)!!.gem!!))
                        }
                    }
                }
            }
        }

        val distRemoveList = removeList.distinct()
        distRemoveList.forEach { (x, y, g) ->
            c(x,y)!!.gem = null
        }
        return distRemoveList
    }
}
