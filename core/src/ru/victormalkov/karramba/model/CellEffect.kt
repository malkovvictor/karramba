package ru.victormalkov.karramba.model

interface CellEffect {
    val stackable: Boolean
    val movable: Boolean

    // если вернулось false, следует эффект снять
    fun onStack(): Boolean
    fun onNear(): Boolean

    val tileName: String // TODO return cell tileName texture
}

