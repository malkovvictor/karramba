package ru.victormalkov.karramba

interface CellEffect {
    val stackable: Boolean

    // если вернулось false, следует эффект снять
    fun onStack(): Boolean
    fun onNear(): Boolean

    val decorator: Any // TODO return cell decorator texture
}

