package ru.victormalkov.karramba.model

class Move(val fromx: Int, val fromy: Int, val direction: Direction)


enum class Direction {
    UP, RIGHT, DOWN, LEFT
}
