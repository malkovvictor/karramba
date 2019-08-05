package ru.victormalkov.karramba.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import ru.victormalkov.karramba.*

class PlayScreen(val game: MyGame) : ScreenAdapter() {
    private var gameViewport: Viewport

    private var stage: GameStage
//    private var backStage: Stage
    private val hud: Hud
  //  private var background: TiledDrawable

    init {
        if (game.board == null) {
            throw Exception("board is null")
        }

        gameViewport = FitViewport(game.getWorldWidth(), game.getWorldHeight())
        hud = Hud(game)
        stage = GameStage(gameViewport, game)
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
        game.currentScore = 0

        game.music = game.assetManager.get(MUSIC, Music::class.java)
        game.music?.isLooping = true
        //game.music?.play()

        Gdx.graphics.isContinuousRendering = false
        Gdx.graphics.requestRendering()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(delta)
        hud.stage.act(delta)

      //  game.batch.begin()
      //  background.draw(game.batch, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
      //  game.batch.end()
        hud.stage.viewport.apply(true)
        hud.draw()
        gameViewport.apply(true)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        hud.stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        super.dispose()
        stage.dispose()
        hud.dispose()
    }
}