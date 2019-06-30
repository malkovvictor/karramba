package ru.victormalkov.karramba

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import ru.victormalkov.karramba.model.Board

class BoardActor(val board: Board): Actor() {
    private var shaper: ShapeRenderer = ShapeRenderer()

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
    }
}