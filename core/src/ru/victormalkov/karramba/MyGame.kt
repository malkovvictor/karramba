package ru.victormalkov.karramba

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import ru.victormalkov.karramba.model.Board
import ru.victormalkov.karramba.model.Cell
import ru.victormalkov.karramba.model.Wall
import ru.victormalkov.karramba.screens.LoadingScreen
import kotlin.math.abs

const val GEM_SIZE = 24f
const val ORDINARY_GEM_TYPES = 5

const val SCORE3 = 1 // * gem count
const val SCORE4 = 1.5 // * gem count
const val SCORE5 = 2 // * gem count

class MyGame : Game() {
    val TAG = "MyGame"

    val assetManager: AssetManager = AssetManager()
    lateinit var batch: Batch private set
    var board: Board? = null
    val cellTextures: MutableMap<String, TextureRegion> = HashMap()
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
        assetManager.load("karramba.png", Texture::class.java)
        assetManager.load("karramba.atlas", TextureAtlas::class.java)
        assetManager.load("Blackmoor_Tides_Loop.ogg", Music::class.java)
    }

    fun finishLoading() {
        assetManager.get("karramba.atlas", TextureAtlas::class.java).regions.forEach {
            cellTextures[it.name] = it
        }
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
        board = readBoardFromFile("level$n")
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

/*            var smth: Boolean
            do {
                var removeList = board!!.removeMatches()
                smth = removeList.isNotEmpty()
                var smth2: Boolean
                do {
                    smth2 = false
                    while (board!!.fall()) {
                        smth = true
                        smth2 = true
                    }
                    if (board!!.produce()) {
                        smth = true
                        smth2 = true
                    }
                } while (smth2)
            } while (smth)   */
        }

    }
}

enum class Phase {
    USER_WAIT,
    REMOVE_MATCHES,
    FALL,
    PRODUCE
}