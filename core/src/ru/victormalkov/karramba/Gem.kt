package ru.victormalkov.karramba

import kotlinx.serialization.Serializable

@Serializable
abstract class Gem {
    abstract val stackable: Boolean
    val effect: GemEffect? = null
}