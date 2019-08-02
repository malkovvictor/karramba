package ru.victormalkov.karramba.model

interface GemEffect {
    val stackable: Boolean
        get() = true

    val movable: Boolean
        get() = true

    val decorator: Any // TODO gem effect tileName
}