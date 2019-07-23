package ru.victormalkov.karramba.boardEvents

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import ru.victormalkov.karramba.GEM_SIZE
import ru.victormalkov.karramba.MyGame
import ru.victormalkov.karramba.model.Gem

class RemoveEvent(game: MyGame, val xx: Int, val yy: Int, private val gem: Gem) : BoardEvent(game) {
    private val me = Sprite(game.cellTextures[gem.name])

    init {
        me.x = xx * GEM_SIZE
        me.y = yy * GEM_SIZE
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        //if (!batch!!.isBlendingEnabled) batch.enableBlending()
//        batch!!.draw(game.cellTextures[gem.name], getX(), getY())
        me.draw(batch)
    }

    override fun act(delta: Float) {
        super.act(delta)
        me.setAlpha(1.0f - myTime / eventTime)
    }


    companion object {
        @Suppress("unused")
        const val TAG = "RemoveEvent"
    }
}