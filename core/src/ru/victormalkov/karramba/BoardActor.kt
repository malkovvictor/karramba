package ru.victormalkov.karramba

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import ru.victormalkov.karramba.model.Board


class BoardActor(private val game: MyGame): Actor() {
    private var shaper: ShapeRenderer = ShapeRenderer()

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (batch!!.isDrawing) {
            batch.end()
        }
        shaper.projectionMatrix = stage.viewport.camera.combined
        shaper.begin(ShapeRenderer.ShapeType.Filled)
        shaper.color = Color.FOREST
        shaper.rect(0f,  0f, game.board!!.width * GEM_SIZE, game.board!!.height * GEM_SIZE)
        shaper.end()

        batch.projectionMatrix = stage.viewport.camera.combined
        batch.begin()
    }

    override fun act(delta: Float) {
        //super.act(delta)
    }
}