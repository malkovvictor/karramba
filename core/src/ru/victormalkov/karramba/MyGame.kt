package ru.victormalkov.karramba

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import ru.victormalkov.karramba.model.*
import ru.victormalkov.karramba.screens.LoadingScreen
import kotlin.math.abs

//const val GEM_SIZE = 24f
const val GEM_SIZE = 128f
const val ORDINARY_GEM_TYPES = 10
const val MAX_BOARD_GEM_COLORS = 5

const val ATLAS_NAME = "zoo"

const val SCORE3 = 1 // * gem count
const val SCORE4 = 1.5 // * gem count
const val SCORE5 = 2 // * gem count

class MyGame : Game() {
    val TAG = "MyGame"

    val assetManager: AssetManager = AssetManager()
    lateinit var batch: Batch private set
    var board: Board? = null
    val cellTextures: MutableMap<String, TextureRegion> = HashMap()
//    val tiles: MutableMap<String, TextureRegion> = HashMap()
    lateinit var font: BitmapFont

    var phase = Phase.USER_WAIT

    var stars: Int = 0 // TODO: store and load stars
        private set
    var levelsCompleted = 0
        private set
    var currentScore = 0
    var movesCount = 0

    fun getWorldWidth() : Float =
        if (board == null) 0f
        else GEM_SIZE * board!!.width

    fun getWorldHeight() : Float =
        if (board == null) 0f
        else GEM_SIZE * board!!.height


    private fun loadAssets() {
        assetManager.load("$ATLAS_NAME.png", Texture::class.java)
        assetManager.load("$ATLAS_NAME.atlas", TextureAtlas::class.java)
        //assetManager.load("$TILES_ATLAS_NAME.png", Texture::class.java)
        //assetManager.load("$TILES_ATLAS_NAME.atlas", TextureAtlas::class.java)
        assetManager.load("Blackmoor_Tides_Loop.ogg", Music::class.java)
    }

    fun finishLoading() {
        assetManager.get("$ATLAS_NAME.atlas", TextureAtlas::class.java).regions.forEach {
            cellTextures[it.name] = it
        }
/*        assetManager.get("$TILES_ATLAS_NAME.atlas", TextureAtlas::class.java).regions.forEach {
            tiles[it.name] = it
        }*/
        println (cellTextures["gem0"]?.regionHeight)
//        GEM_SIZE = cellTextures["gem0"]!!.regionHeight.toFloat()
        font = BitmapFont()
    }

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        batch = SpriteBatch()
        loadAssets()
        this.screen = LoadingScreen(this)
    }

    override fun dispose() {
        batch.dispose()
        cellTextures.clear()
        assetManager.dispose()
        font.dispose()
    }

    fun loadLevel(n: Int) {
        board = readBoardFromFile("levels/level$n")
        movesCount = 0
        currentScore = 0
    }

    override fun resume() {
        super.resume()
        Texture.setAssetManager(assetManager)
    }

    fun processMove(source: CellActor, dest: CellActor) {
        if (board!!.processMove(source.x, source.y, dest.x, dest.y)) {
            movesCount++
            phase = Phase.REMOVE_MATCHES
        }
    }
}

enum class Phase {
    USER_WAIT,
    REMOVE_MATCHES,
    FALL,
    PRODUCE
}