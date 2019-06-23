package ru.victormalkov.karramba

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
class StaticWall: Wall() {
    override val decorator: Any
        get() = TODO("not implemented: return static wall texture") //To change initializer of created properties use File | Settings | File Templates.

}

// прозрачная стена, например край карты
class TransparentWall: Wall() {
    override val decorator: Any
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}

// неподвижная стена, через которую могут падать камни
class ThruWall: Wall() {
    override val decorator: Any
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}