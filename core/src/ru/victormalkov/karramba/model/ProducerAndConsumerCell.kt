package ru.victormalkov.karramba.model

object ProducerCell: CellEffect {
    override val stackable: Boolean
        get() = true // не изменяет стакуемость
    override val movable: Boolean
        get() = true

    override fun onStack(): Boolean {
        return true
    }

    override fun onNear(): Boolean {
        return true
    }

    override val tileName: String
        get() = TODO("not implemented: tileName of producer cell") //To change initializer of created properties use File | Settings | File Templates.

}

object ConsumerCell: CellEffect {
    override val stackable: Boolean
        get() = true // не изменяет стакуемость
    override val movable: Boolean
        get() = true

    override fun onStack(): Boolean {
        return true
    }

    override fun onNear(): Boolean {
        return true
    }

    override val tileName: String
        get() = TODO("not implemented: tileName of consumer cell") //To change initializer of created properties use File | Settings | File Templates.

}
