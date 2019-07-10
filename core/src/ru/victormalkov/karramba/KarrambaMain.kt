package ru.victormalkov.karramba

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import ru.victormalkov.karramba.model.Board

class KarrambaMain : ApplicationAdapter() {
//    private lateinit var renderer: BoardRenderer
    private lateinit var controller: GameController

    private lateinit var stage: Stage

    private val manager: AssetManager = AssetManager()

    override fun create() {
        load()


        controller = GameController()
        //renderer = BoardRenderer(controller)
        controller.create()

        stage = Stage()
        Gdx.input.inputProcessor = stage



        //renderer.create()
    }

    override fun render() {
        //renderer.render()
        Gdx.gl.glClearColor(0f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.draw()
    }

    override fun dispose() {
//        renderer.dispose()
        manager.dispose()
    }

    override fun resize(width: Int, height: Int) {
        //renderer.resize(width, height)
        stage.viewport.update(width, height, true)
    }

    private fun load() {
        manager.load("karramba.png", Texture::class.java)
        manager.load("karramba.atlas", TextureAtlas::class.java)
        manager.load("Blackmoor_Tides_Loop.ogg", Music::class.java)
    }

    override fun resume() {
        super.resume()
        Texture.setAssetManager(manager)
    }
}
