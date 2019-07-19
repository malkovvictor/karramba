package ru.victormalkov.karramba.boardEvents

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import ru.victormalkov.karramba.GEM_SIZE
import ru.victormalkov.karramba.MyGame
import ru.victormalkov.karramba.model.Gem

class FallEvent(game: MyGame, val source: Pair<Int, Int>, val dest: Pair<Int, Int>, val gem: Gem) : BoardEvent(game) {
    override val eventTime: Float
        get() = .05f

    private val me = Sprite(game.cellTextures[gem.name])

    override fun draw(batch: Batch?, parentAlpha: Float) {
        me.x = source.first * GEM_SIZE + (dest.first - source.first) * (myTime / eventTime) * GEM_SIZE
        me.y = source.second * GEM_SIZE + (dest.second - source.second) * (myTime / eventTime) * GEM_SIZE
        me.draw(batch!!)
    }

}