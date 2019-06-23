package ru.victormalkov.karramba

import kotlinx.serialization.Serializable

@Serializable
class Cell {
    val gem: Gem? = null
    val effect: CellEffect? = null
    val producer: Boolean = false
    val consumer: Boolean = false
}