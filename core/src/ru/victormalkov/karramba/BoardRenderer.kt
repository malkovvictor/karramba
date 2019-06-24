package ru.victormalkov.karramba

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ru.victormalkov.karramba.model.StaticWall
import ru.victormalkov.karramba.model.TransparentWall
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport


const val GEM_SIZE = 24f
const val ORDINARY_GEM_TYPES = 5

class BoardRenderer(var controller: GameController) {
    private lateinit var batch: SpriteBatch
    private lateinit var texture: Texture
    private lateinit var atlas: TextureAtlas
    private lateinit var gems: Array<TextureRegion>
    private lateinit var walls: Array<TextureRegion>
    private lateinit var shaper: ShapeRenderer
    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport

    fun create() {
        batch = SpriteBatch()
        shaper = ShapeRenderer()
        texture = Texture("karramba.png")
        atlas = TextureAtlas("karramba.atlas")
        gems = Array(ORDINARY_GEM_TYPES) {i -> atlas.findRegion("gem$i")}
        walls = Array(1) {atlas.findRegion("wall1")}
        camera = OrthographicCamera()
        viewport = FitViewport(GEM_SIZE * 8, GEM_SIZE * 10, camera)
    }

    fun render() {
        val scrw = 320f
        val scrh = 480f
        val board = controller.board
        Gdx.gl.glClearColor(0f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        if (board == null) {
            return
        }
        val height = Gdx.graphics.height
        shaper.begin(ShapeRenderer.ShapeType.Line)
        shaper.color = Color.FOREST
        shaper.rect(0f, height - (board.height - 1) * GEM_SIZE, board.width * GEM_SIZE, board.height * GEM_SIZE)
        shaper.end()
        batch.begin()
        for (x in 0 until board!!.width) {
            for (y in 0 until board!!.height) {
                var tr: TextureRegion? = null
                when (board!!.cells[x][y].effect) {
                    StaticWall -> {
                        tr = walls[0]

                    }
                    TransparentWall -> {}
                    else -> {
                        // эффект отсутствует
                        tr = gems[0]
                    }
                }
                batch.draw(tr, GEM_SIZE * x, height - GEM_SIZE * y)
            }
        }
        batch.end()
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    fun dispose() {
        batch.dispose()
        texture.dispose()
    }
}