package ru.victormalkov.karramba

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor

class BoardActor(private val game: MyGame): Actor() {
    private var shaper: ShapeRenderer = ShapeRenderer()

    override fun draw(batch: Batch?, parentAlpha: Float) {
/*
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
        batch.begin() */

        for (x in 0 until game.board!!.width) {
            for (y in 0 until game.board!!.height) {
                var tr: TextureRegion?
                if (x == 0) {
                    if (y == 0) {
                        tr = game.tiles["rpgTile036"]
                    } else if (y == game.board!!.height - 1) {
                        tr = game.tiles["rpgTile000"]
                    } else {
                        tr = game.tiles["rpgTile018"]
                    }
                } else if (x == game.board!!.width - 1) {
                    if (y == 0) {
                        tr = game.tiles["rpgTile038"]
                    } else if (y == game.board!!.height - 1) {
                        tr = game.tiles["rpgTile002"]
                    } else {
                        tr = game.tiles["rpgTile020"]
                    }
                } else {
                    if (y == 0) {
                        tr = game.tiles["rpgTile037"]
                    } else if (y == game.board!!.height - 1) {
                        tr = game.tiles["rpgTile001"]
                    } else {
                        tr = game.tiles["rpgTile019"]
                    }
                }

                if (tr != null) {
                    batch!!.draw(tr, x * GEM_SIZE, y * GEM_SIZE)
                }
            }
        }
    }

    override fun act(delta: Float) {
        //super.act(delta)
    }

    companion object {
        @Suppress("unused")
        const val TAG = "BoardActor"
    }
}