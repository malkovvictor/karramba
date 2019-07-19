package ru.victormalkov.karramba.model

import kotlinx.serialization.Serializable

@Serializable
class Cell {
    var gem: Gem? = null
    var effect: CellEffect? = null
    var producer: Boolean = false
    var consumer: Boolean = false


    var fallmark: Boolean = false
}