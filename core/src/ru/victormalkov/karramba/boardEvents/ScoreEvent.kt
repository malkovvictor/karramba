package ru.victormalkov.karramba.boardEvents

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import ru.victormalkov.karramba.GEM_SIZE
import ru.victormalkov.karramba.MyGame

class ScoreEvent(game: MyGame, val myScore: Int, val x: Int, val y: Int) : BoardEvent(game) {
    var myX: Float = (x + 0.5f) * GEM_SIZE
    var myY: Float = (y + 0.5f) * GEM_SIZE

    override fun draw(batch: Batch?, parentAlpha: Float) {
        game.assetManager.get("scoreOnBoard.ttf", BitmapFont::class.java).draw(batch, myScore.toString(), myX, myY + GEM_SIZE * myTime / eventTime)
    }

    override fun act(delta: Float) {
        super.act(delta)
//        myY += GEM_SIZE * myTime / eventTime
    }
}