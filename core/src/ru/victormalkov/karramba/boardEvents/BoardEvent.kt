package ru.victormalkov.karramba.boardEvents

import com.badlogic.gdx.scenes.scene2d.Actor
import ru.victormalkov.karramba.MyGame

open class BoardEvent(val game: MyGame): Actor() {
    open val eventTime = 0.5f // sec

    var myTime = 0.0f

    override fun act(delta: Float) {
        super.act(delta)
        myTime += delta
        if (myTime > eventTime) myTime = eventTime
    }
}