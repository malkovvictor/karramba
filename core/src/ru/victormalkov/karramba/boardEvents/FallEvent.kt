package ru.victormalkov.karramba.boardEvents

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import ru.victormalkov.karramba.GEM_SIZE
import ru.victormalkov.karramba.MyGame
import ru.victormalkov.karramba.MyStage
import ru.victormalkov.karramba.model.Gem

class FallEvent(game: MyGame, val source: Pair<Int, Int>, val dest: Pair<Int, Int>, val gem: Gem) : BoardEvent(game) {
    override val eventTime: Float
        get() = 3.2f

    private val me = Sprite(game.cellTextures[gem.name])
    private var previous: FallEvent? = null

    init {
        game.board!!.c(dest.first, dest.second)!!.fallmark = true
    }

    private fun checkChain(): Boolean {
        val s = stage as MyStage
        if (game.board!!.c(source.first, source.second)!!.fallmark) {
            return s.eventList.find {
                it is FallEvent &&
                        it.dest.first == source.first &&
                        it.dest.second == source.second
            } != null
        }
        return false
    }

    override fun act(delta: Float) {
        if (!checkChain()) super.act(delta)
    }
    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (checkChain()) return
        me.x = source.first * GEM_SIZE + (dest.first - source.first) * (myTime / eventTime) * GEM_SIZE
        me.y = source.second * GEM_SIZE + (dest.second - source.second) * (myTime / eventTime) * GEM_SIZE
        me.draw(batch!!)
    }

}