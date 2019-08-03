package ru.victormalkov.karramba

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor

class BoardActor(private val game: MyGame): Actor() {
    override fun draw(batch: Batch?, parentAlpha: Float) {
/*
текстуры фона в 2 раза меньше текстур камней
в одну ячейку поля надо четыре текстуры фона:
a b
c d
 */
        for (x in 0 until game.board!!.width) {
            for (y in 0 until game.board!!.height) {
                var a: TextureRegion?
                var b: TextureRegion?
                var c: TextureRegion?
                var d: TextureRegion?
                if (x == 0) {
                    if (y == 0) { // bottom left
                        a = game.cellTextures[TILE_LEFT]
                        b = game.cellTextures[TILE_NORMAL]
                        c = game.cellTextures[TILE_BOTTOM_LEFT]
                        d = game.cellTextures[TILE_BOTTOM]
                    } else if (y == game.board!!.height - 1) { // top left
                        a = game.cellTextures[TILE_TOP_LEFT]
                        b = game.cellTextures[TILE_TOP]
                        c = game.cellTextures[TILE_LEFT]
                        d = game.cellTextures[TILE_NORMAL]
                    } else { // left
                        a = game.cellTextures[TILE_LEFT]
                        b = game.cellTextures[TILE_NORMAL]
                        c = game.cellTextures[TILE_LEFT]
                        d = game.cellTextures[TILE_NORMAL]
                    }
                } else if (x == game.board!!.width - 1) {
                    if (y == 0) { // bottom right
                        a = game.cellTextures[TILE_NORMAL]
                        b = game.cellTextures[TILE_RIGHT]
                        c = game.cellTextures[TILE_BOTTOM]
                        d = game.cellTextures[TILE_BOTTOM_RIGHT]
                    } else if (y == game.board!!.height - 1) { // top right
                        a = game.cellTextures[TILE_TOP]
                        b = game.cellTextures[TILE_TOP_RIGHT]
                        c = game.cellTextures[TILE_NORMAL]
                        d = game.cellTextures[TILE_RIGHT]
                    } else { // right
                        a = game.cellTextures[TILE_NORMAL]
                        b = game.cellTextures[TILE_RIGHT]
                        c = game.cellTextures[TILE_NORMAL]
                        d = game.cellTextures[TILE_RIGHT]
                    }
                } else {
                    if (y == 0) { // bottom
                        a = game.cellTextures[TILE_NORMAL]
                        b = game.cellTextures[TILE_NORMAL]
                        c = game.cellTextures[TILE_BOTTOM]
                        d = game.cellTextures[TILE_BOTTOM]
                    } else if (y == game.board!!.height - 1) { // top
                        a = game.cellTextures[TILE_TOP]
                        b = game.cellTextures[TILE_TOP]
                        c = game.cellTextures[TILE_NORMAL]
                        d = game.cellTextures[TILE_NORMAL]
                    } else { // not on edge
                        a = game.cellTextures[TILE_NORMAL]
                        b = game.cellTextures[TILE_NORMAL]
                        c = game.cellTextures[TILE_NORMAL]
                        d = game.cellTextures[TILE_NORMAL]
                    }
                }

                if (a != null) {
                    batch!!.draw(a, x * GEM_SIZE, (y + 0.5f) * GEM_SIZE)
                    batch.draw(b, (x + 0.5f)* GEM_SIZE, (y + 0.5f) * GEM_SIZE)
                    batch.draw(c, x * GEM_SIZE, y * GEM_SIZE)
                    batch.draw(d, (x + 0.5f) * GEM_SIZE, y * GEM_SIZE)
                }
            }
        }
    }

   companion object {
        @Suppress("unused")
        const val TAG = "BoardActor"
    }
}