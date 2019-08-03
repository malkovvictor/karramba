package ru.victormalkov.karramba.model

// стена - неподвижная, неизменяемая
abstract class Wall: CellEffect {
    override val stackable: Boolean
        get() = false
    override val movable: Boolean
        get() = false

    override fun onStack(): Boolean {
        return true
    }

    override fun onNear(): Boolean {
        return true
    }
}


// просто неподвижная непрозрачная стена
class StaticWall(val tile: String, val tile2: String? = null): Wall() {
    override val tileName: String
        get() = tile
    val tileName2: String?
        get() = tile2
}




// прозрачная стена, например край карты
object TransparentWall: Wall() {
    override val tileName: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}

// неподвижная стена, через которую могут падать камни
object ThruWall: Wall() {
    override val tileName: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}