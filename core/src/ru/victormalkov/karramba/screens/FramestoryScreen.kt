package ru.victormalkov.karramba.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ru.victormalkov.karramba.MyGame
import ru.victormalkov.karramba.TILE_BACKGROUND

class FramestoryScreen(val game: MyGame) : ScreenAdapter() {
    private val camera = OrthographicCamera()
    private var font: BitmapFont
    private var background: TiledDrawable
    private var stage: Stage
    private var backViewport: ScreenViewport

    val WIDTH  = 64f/*px*/ * 5/*columns*/ + 5/*px*/ * 6/*padding left, right and between buttons*/   //350f
    val HEIGHT = 64f * 4/*rows*/ + 5 * 5

    init {
        font = BitmapFont()
        background = TiledDrawable(game.cellTextures[TILE_BACKGROUND])
        stage = Stage(FitViewport(WIDTH, HEIGHT), game.batch)
        camera.setToOrtho(false, WIDTH, HEIGHT)

        var table = Table()
        var textButtonStyle = TextButton.TextButtonStyle()
        textButtonStyle.font = game.assetManager.get("font1.ttf", BitmapFont::class.java)
        Gdx.app.debug("FramestoryScreen", "Level count: ${game.levelCount}")

        for (i in 1..game.levelCount) {
            var b = TextButton(i.toString(), textButtonStyle)
            b.addListener(object : EventListener {
                override fun handle(event: Event?): Boolean {
                    Gdx.app.log(TAG, "запуск игры")
                    game.loadLevel(i)
                    game.screen = PlayScreen(game)
                    dispose()
                    return true
                }

            })

            table.add(b).expandX()
            if (i % 5 == 0) {
                table.row()
            }
        }
        table.setFillParent(true)
        stage.addActor(table)


        backViewport = ScreenViewport()
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        camera.update()
        //game.batch.projectionMatrix = camera.combined

        game.batch.projectionMatrix = camera.combined
        backViewport.apply()
        game.batch.begin()
        background.draw(game.batch, 0f, 0f, camera.viewportWidth, camera.viewportHeight)
        game.batch.end()
        stage.viewport.apply()
        stage.draw()
/*
        if (Gdx.input.isTouched || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Gdx.app.log(TAG, "запуск игры")
            game.loadLevel(1)
            game.screen = PlayScreen(game)
            dispose()
        }*/
    }

    override fun dispose() {
        super.dispose()
        font.dispose()
        stage.dispose()
    }

    override fun resize(width: Int, height: Int) {
        backViewport.update(width, height)
        stage.viewport.update(width, height)
    }

    companion object {
        const val TAG = "FramestoryScreen"
    }
}