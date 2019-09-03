package ru.victormalkov.karramba

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import ru.victormalkov.karramba.model.*
import ru.victormalkov.karramba.screens.LoadingScreen
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter
import com.badlogic.gdx.graphics.g2d.BitmapFont

class MyGame : Game() {
    val TAG = "MyGame"

    val assetManager: AssetManager = AssetManager()
    lateinit var batch: Batch private set
    var board: Board? = null
    val cellTextures: MutableMap<String, TextureRegion> = HashMap()
//    val tiles: MutableMap<String, TextureRegion> = HashMap()
    lateinit var font: BitmapFont
    var music: Music? = null

    var phase = Phase.USER_WAIT

    var stars: Int = 0 // TODO: store and load stars
        private set
    var levelsCompleted = 0
        private set
    var currentScore = 0
    var movesCount = 0
    var levelName = "level 1"
    var levelCount: Int = 0

    fun getWorldWidth() : Float =
        if (board == null) 0f
        else GEM_SIZE * board!!.width

    fun getWorldHeight() : Float =
        if (board == null) 0f
        else GEM_SIZE * board!!.height


    private fun loadAssets() {
//        assetManager.setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(InternalFileHandleResolver()))
        val resolver = InternalFileHandleResolver()
        assetManager.setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolver))
        assetManager.setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolver))
        assetManager.load("$ATLAS_NAME.png", Texture::class.java)
        assetManager.load("$ATLAS_NAME.atlas", TextureAtlas::class.java)
        assetManager.load(SOUND, Sound::class.java)
        //assetManager.load("$TILES_ATLAS_NAME.png", Texture::class.java)
        //assetManager.load("$TILES_ATLAS_NAME.atlas", TextureAtlas::class.java)
        assetManager.load(MUSIC, Music::class.java)

        var params = FreeTypeFontLoaderParameter()
        params.fontFileName = SCORE_FONT
        params.fontParameters.size = 48
        params.fontParameters.characters = FONT_CHARS
        params.fontParameters.color = Color.YELLOW
        params.fontParameters.borderColor = Color.BLACK
        params.fontParameters.borderWidth = 3f
        assetManager.load("font1.ttf", BitmapFont::class.java, params)

        params = FreeTypeFontLoaderParameter()
        params.fontFileName = SCORE_FONT
        params.fontParameters.size = 48
        params.fontParameters.characters = "0123456789"
        params.fontParameters.color = Color.YELLOW
        params.fontParameters.borderColor = Color.BLACK
        params.fontParameters.borderWidth = 3f
        assetManager.load("scoreOnBoard.ttf", BitmapFont::class.java, params)

        levelCount = loadLevelCount()
    }

    fun finishLoading() {
        assetManager.get("$ATLAS_NAME.atlas", TextureAtlas::class.java).regions.forEach {
            cellTextures[it.name] = it
        }
/*        assetManager.get("$TILES_ATLAS_NAME.atlas", TextureAtlas::class.java).regions.forEach {
            tiles[it.name] = it
        }*/
        //println (cellTextures["gem0"]?.regionHeight)
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
        levelName = "Level $n"
    }

    private fun loadLevelCount(): Int {
        var r = Gdx.files.internal("levels").list().size
        Gdx.app.debug(TAG, "Level count: $r")
        return r
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