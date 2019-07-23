package ru.victormalkov.karramba

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor

class BoardActor(private val game: MyGame): Actor() {
    private var shaper: ShapeRenderer = ShapeRenderer()

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (batch!!.isDrawing) {
            batch.end()
        }
        //Gdx.app.debug(TAG, "drawing board, width: ${game.board!!.width}, height: ${game.board!!.height}")
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

    companion object {
        @Suppress("unused")
        const val TAG = "BoardActor"
    }
}