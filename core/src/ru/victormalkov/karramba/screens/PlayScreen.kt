package ru.victormalkov.karramba.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import ru.victormalkov.karramba.*
import ru.victormalkov.karramba.boardEvents.BoardEvent
import java.util.*

class PlayScreen(val game: MyGame) : ScreenAdapter() {
    private var viewport: Viewport
    private var stage: MyStage

    init {
        if (game.board == null) {
            throw Exception("board is null")
        }

        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
        viewport.apply(true)

        stage = MyStage(viewport, game)
        Gdx.input.inputProcessor = stage
        val bg = Group()
        val fg = Group()

        stage.addActor(bg)
        stage.addActor(fg)
        stage.addActor(stage.boardEvents)
        fg.touchable = Touchable.enabled

        var boardActor = BoardActor(game)
        bg.addActor(boardActor)

        for (x in 0 until game.board!!.width) {
            for (y in 0 until game.board!!.height) {
                var cellActor = CellActor(game.board!!.cells[x][y], x, y, game)
                fg.addActor(cellActor)
                cellActor.setPosition(x * GEM_SIZE, y * GEM_SIZE)
                cellActor.setSize(GEM_SIZE, GEM_SIZE)
                cellActor.setBounds(x * GEM_SIZE, y * GEM_SIZE, GEM_SIZE, GEM_SIZE)
            }
        }
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(delta)
        stage.draw()

    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }
}