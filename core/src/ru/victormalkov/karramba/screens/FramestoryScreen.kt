package ru.victormalkov.karramba.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Button
import ru.victormalkov.karramba.MyGame

class FramestoryScreen(val game: MyGame) : ScreenAdapter() {
    private val camera = OrthographicCamera()
    private var font: BitmapFont

    init {
        camera.setToOrtho(false, 800f, 600f)
        font = BitmapFont()

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update()

        game.batch.projectionMatrix = camera.combined
        game.batch.begin()

        font.draw(game.batch, "welcome", 100f, 150f)
        font.draw(game.batch, "tap anywhere", 100f, 100f)

        game.batch.end()

        if (Gdx.input.isTouched || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Gdx.app.log(Companion.TAG, "запуск игры")
            game.loadLevel(2)
            game.screen = PlayScreen(game)
            dispose()
        }
    }

    override fun dispose() {
        super.dispose()
        font.dispose()
    }

    companion object {
        const val TAG = "FramestoryScreen"
    }
}