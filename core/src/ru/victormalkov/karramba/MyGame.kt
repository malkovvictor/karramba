package ru.victormalkov.karramba

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ru.victormalkov.karramba.model.Board
import ru.victormalkov.karramba.screens.LoadingScreen

class MyGame : Game() {
    val assetManager: AssetManager = AssetManager()
    lateinit var batch: Batch private set
    var board: Board? = null


    var stars: Int = 0 // TODO: store and load stars
        private set
    var levelsCompleted = 0
        private set

    private fun loadAssets() {
        assetManager.load("karramba.png", Texture::class.java)
        assetManager.load("karramba.atlas", TextureAtlas::class.java)
        assetManager.load("Blackmoor_Tides_Loop.ogg", Music::class.java)
    }

    override fun create() {
        batch = SpriteBatch()
        loadAssets()
        this.screen = LoadingScreen(this)
    }

    override fun dispose() {
        batch.dispose()
        assetManager.dispose()
    }

    fun loadLevel(n: Int) {
        board = readBoardFromFile("level$n")

    }
}