package ru.victormalkov.karramba

class ProducerCell: CellEffect {
    override val stackable: Boolean
        get() = true // не изменяет стакуемость

    override fun onStack(): Boolean {
        return true
    }

    override fun onNear(): Boolean {
        return true
    }

    override val decorator: Any
        get() = TODO("not implemented: decorator of producer cell") //To change initializer of created properties use File | Settings | File Templates.

}

class ConsumerCell: CellEffect {
    override val stackable: Boolean
        get() = true // не изменяет стакуемость

    override fun onStack(): Boolean {
        return true
    }

    override fun onNear(): Boolean {
        return true
    }

    override val decorator: Any
        get() = TODO("not implemented: decorator of consumer cell") //To change initializer of created properties use File | Settings | File Templates.

}
