package ru.victormalkov.karramba

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import ru.victormalkov.karramba.model.Board
import ru.victormalkov.karramba.model.StaticWall
import ru.victormalkov.karramba.model.TransparentWall

class BoardActor(val board: Board): Group() {
    private var shaper: ShapeRenderer = ShapeRenderer()
    internal var cellTextures: MutableMap<String, TextureRegion>
    private var texture: Texture
    private var atlas: TextureAtlas

    init {
        texture = Texture("karramba.png")
        atlas = TextureAtlas("karramba.atlas")
        cellTextures = HashMap()
        atlas.regions.forEach {
            cellTextures[it.name] = it
        }

    }


    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        if (board == null) {
            return
        }
        batch?.end()

        shaper.projectionMatrix = stage.viewport.camera.combined
        shaper.begin(ShapeRenderer.ShapeType.Filled)
        shaper.color = Color.FOREST
        shaper.rect(0f,  0f, board.width * GEM_SIZE, board.height * GEM_SIZE)
        shaper.end()


        batch?.projectionMatrix = stage.viewport.camera.combined
        batch?.begin()
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
                if (tr != null) batch?.draw(tr, GEM_SIZE * x, height - GEM_SIZE * (y + 1))
            }
        }

    }
}