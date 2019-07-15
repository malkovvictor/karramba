package ru.victormalkov.karramba.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import ru.victormalkov.karramba.MyGame
import ru.victormalkov.karramba.model.StaticWall
import ru.victormalkov.karramba.model.TransparentWall

const val GEM_SIZE = 24f
const val WORLD_WIDTH = GEM_SIZE * 8
const val WORLD_HEIGHT = GEM_SIZE * 10

const val ORDINARY_GEM_TYPES = 5

class PlayScreen(val game: MyGame) : ScreenAdapter() {
    private var viewport: Viewport
    private var cellTextures: MutableMap<String, TextureRegion>

    init {
        cellTextures = HashMap()
        game.assetManager.get("karramba.atlas", TextureAtlas::class.java).regions.forEach {
            cellTextures[it.name] = it
        }
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
        viewport.apply(true)

    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        viewport.camera.update()
        if (game.board == null) {
            return
        }
        val height = viewport.camera.viewportHeight
        game.batch.projectionMatrix = viewport.camera.combined
        game.batch.begin()
        for (x in 0 until game.board!!.width) {
            for (y in 0 until game.board!!.height) {
                var tr: TextureRegion? = null
                when (game.board!!.cells[x][y].effect) {
                    StaticWall -> {
                        tr = cellTextures["wall1"]
                    }
                    TransparentWall -> {}
                    else -> {
                        // эффект отсутствует
                        //tr = cellTextures
                        if (game.board!!.cells[x][y].gem != null) {
                            tr = cellTextures[game.board!!.cells[x][y].gem!!.name]
                        } else {
                            //tr = cellTextures["gem0"]
                        }
                    }
                }
//                println("region width = ${tr?.regionWidth}, height = ${tr?.regionHeight}")
                if (tr != null) game.batch.draw(tr, GEM_SIZE * x, height - GEM_SIZE * (y + 1))
            }
        }
        game.batch.end()

    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }
}