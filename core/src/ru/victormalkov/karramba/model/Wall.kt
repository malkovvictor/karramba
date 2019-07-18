package ru.victormalkov.karramba.model

// стена - неподвижная, неизменяемая
abstract class Wall: CellEffect {
    override val stackable: Boolean
        get() = false

    override fun onStack(): Boolean {
        return true
    }

    override fun onNear(): Boolean {
        return true
    }
}


// просто неподвижная непрозрачная стена
object StaticWall: Wall() {
    override val movable: Boolean
        get() = false
    override val decorator: Any
        get() = TODO("not implemented: return static wall texture") //To change initializer of created properties use File | Settings | File Templates.

}

// прозрачная стена, например край карты
object TransparentWall: Wall() {
    override val movable: Boolean
        get() = false
    override val decorator: Any
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}

// неподвижная стена, через которую могут падать камни
object ThruWall: Wall() {
    override val movable: Boolean
        get() = false
    override val decorator: Any
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}