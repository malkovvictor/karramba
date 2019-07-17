package ru.victormalkov.karramba.screens

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import ru.victormalkov.karramba.*
import java.lang.Exception

class PlayScreen(val game: MyGame) : ScreenAdapter() {
    private var viewport: Viewport
    private var stage: Stage

    init {
        if (game.board == null) {
            throw Exception("board is null")
        }

        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
        viewport.apply(true)

        stage = Stage(viewport)

        var boardActor = BoardActor(game.board!!)
        for (x in 0 until game.board!!.width) {
            for (y in 0 until game.board!!.height) {
                var cellActor = CellActor(game.board!!.cells[x][y], x, y, game)
                boardActor.addActor(cellActor)
            }
        }


        stage.addActor(boardActor)
    }

    override fun show() {
        super.show()
    }

    override fun render(delta: Float) {
        // todo переписать в stage и actor

/*        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
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
                        tr = game.cellTextures["wall1"]
                    }
                    TransparentWall -> {}
                    else -> {
                        // эффект отсутствует
                        //tr = cellTextures
                        if (game.board!!.cells[x][y].gem != null) {
                            tr = game.cellTextures[game.board!!.cells[x][y].gem!!.name]
                        } else {
                            //tr = cellTextures["gem0"]
                        }
                    }
                }
//                println("region width = ${tr?.regionWidth}, height = ${tr?.regionHeight}")
                if (tr != null) game.batch.draw(tr, GEM_SIZE * x, height - GEM_SIZE * (y + 1))
            }
        }
        game.batch.end()*/

    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }
}