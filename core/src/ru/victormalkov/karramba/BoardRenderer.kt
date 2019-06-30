package ru.victormalkov.karramba

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import ru.victormalkov.karramba.model.StaticWall
import ru.victormalkov.karramba.model.TransparentWall
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.badlogic.gdx.utils.viewport.Viewport


const val GEM_SIZE = 24f
const val WORLD_WIDTH = GEM_SIZE * 8
const val WORLD_HEIGHT = GEM_SIZE * 10


const val ORDINARY_GEM_TYPES = 5

class BoardRenderer(var controller: GameController) {
    private lateinit var batch: SpriteBatch
    private lateinit var texture: Texture
    private lateinit var atlas: TextureAtlas
    private lateinit var viewport: Viewport

    internal lateinit var cellTextures: MutableMap<String, TextureRegion>

    fun create() {
        batch = SpriteBatch()
//        shaper = ShapeRenderer()
        texture = Texture("karramba.png")
        atlas = TextureAtlas("karramba.atlas")
        cellTextures = HashMap()
        atlas.regions.forEach {
            cellTextures[it.name] = it
        }
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
        //viewport = StretchViewport(WORLD_WIDTH, WORLD_HEIGHT)

        viewport.apply(true)
    }

    fun render() {
        val board = controller.board
        if (board == null) {
            return
        }
        val height = viewport.camera.viewportHeight
//        shaper.projectionMatrix = viewport.camera.combined
//        shaper.begin(ShapeRenderer.ShapeType.Filled)
//        shaper.color = Color.FOREST
//        shaper.rect(0f,  0f, board.width * GEM_SIZE, board.height * GEM_SIZE)
//        shaper.end()
        batch.projectionMatrix = viewport.camera.combined
        batch.begin()
        for (x in 0 until board!!.width) {
            for (y in 0 until board!!.height) {
                var tr: TextureRegion? = null
                when (board!!.cells[x][y].effect) {
                    StaticWall -> {
                        tr = cellTextures["wall1"]
                    }
                    TransparentWall -> {}
                    else -> {
                        // эффект отсутствует
                        //tr = cellTextures
                        if (board!!.cells[x][y].gem != null) {
                            tr = cellTextures[board!!.cells[x][y].gem!!.name]
                        } else {
                            //tr = cellTextures["gem0"]
                        }
                    }
                }
//                println("region width = ${tr?.regionWidth}, height = ${tr?.regionHeight}")
                if (tr != null) batch.draw(tr, GEM_SIZE * x, height - GEM_SIZE * (y + 1))
            }
        }
        batch.end()
    }

/*    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }
*/
    fun dispose() {
        batch.dispose()
        texture.dispose()
        atlas.dispose()
    }
}