package ru.victormalkov.karramba

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import ru.victormalkov.karramba.model.Cell
import ru.victormalkov.karramba.model.StaticWall
import ru.victormalkov.karramba.model.TransparentWall

class CellActor(val myCell: Cell, val x: Int, val y: Int, val mainRenderer: BoardRenderer): Actor() {
    override fun draw(batch: Batch?, parentAlpha: Float) {
        var tr: TextureRegion? = null
        when (myCell.effect) {
            StaticWall -> {
                tr = mainRenderer.cellTextures["wall1"]
            }
            TransparentWall -> {}
            else -> {
                // эффект отсутствует
                //tr = cellTextures
                if (myCell.gem != null) {
                    tr = mainRenderer.cellTextures[myCell.gem!!.name]
                } else {
                    //tr = cellTextures["gem0"]
                }
            }
        }
//                println("region width = ${tr?.regionWidth}, height = ${tr?.regionHeight}")
        if (tr != null) batch?.draw(tr, GEM_SIZE * x, height - GEM_SIZE * (y + 1))
    }

}