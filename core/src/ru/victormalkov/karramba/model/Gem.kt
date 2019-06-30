package ru.victormalkov.karramba.model

import kotlinx.serialization.Serializable

@Serializable
abstract class Gem(val name: String) {
    abstract val stackable: Boolean
    val effect: GemEffect? = null
}

class OrdinaryGem(name:String) : Gem(name) {
    override val stackable: Boolean
        get() = true
}

class NonStackableGem(name:String) : Gem(name) {
    override val stackable: Boolean
        get() = false

}