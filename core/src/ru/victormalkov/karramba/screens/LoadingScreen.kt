package ru.victormalkov.karramba.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ru.victormalkov.karramba.MyGame

class LoadingScreen(private val game: MyGame) : ScreenAdapter() {
    private val TAG = "LoadingScreen"
    private val camera = OrthographicCamera()
    private var loading : Sprite
    private var font: BitmapFont

    init {
        camera.setToOrtho(false, 800f, 600f)
        val ldscreenTexture = Texture(Gdx.files.internal("loading.png"))
        loading = Sprite(ldscreenTexture)
        font = BitmapFont()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        camera.update()
        game.batch.projectionMatrix = camera.combined
        game.batch.begin()
        game.batch.draw(loading, 0f,0f)

        font.draw(game.batch, String.format("%.0f", 100 * game.assetManager.progress), 10f, 100f)
        game.batch.end()
        if (game.assetManager.update()) {
            game.finishLoading()
            game.screen = FramestoryScreen(game)
            Gdx.app.log(TAG, "loading finished")
            Gdx.app.log(TAG, "${game.assetManager.loadedAssets} assets loaded")
        }
    }

    override fun dispose() {
        super.dispose()
        font.dispose()
    }
}