package ru.victormalkov.karramba

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import ru.victormalkov.karramba.boardEvents.FallEvent
import ru.victormalkov.karramba.model.Cell
import ru.victormalkov.karramba.model.StaticWall
import ru.victormalkov.karramba.model.TransparentWall

class CellActor(val myCell: Cell, val x: Int, val y: Int, val game: MyGame): Actor() {
    val TAG = "CellActor"
    init {
        this.addListener(object : DragListener() {
            override fun drag(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                if (game.phase != Phase.USER_WAIT) return
                Gdx.app.debug(TAG, "drag cell (${this@CellActor.x}, ${this@CellActor.y}) to position (${event!!.stageX}, ${event!!.stageY})")
                val a = this@CellActor.stage.hit(event!!.stageX, event!!.stageY, false)
                if (a is CellActor) {
                    Gdx.app.debug(TAG, "target cell: $a")
                    if (a != this@CellActor) {
                        game.processMove(this@CellActor, a)
                    }
                }
            }
        })
        updateTouchable()
    }

    private fun updateTouchable() {
        var touch = myCell.gem != null
        if (myCell.effect?.movable == false) {
            touch = false
        } else if (myCell.gem?.effect?.movable == false) {
            touch = false
        }
        if (touch) {
            this.touchable = Touchable.enabled
        } else {
            this.touchable = Touchable.disabled
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        when (myCell.effect) {
            is StaticWall -> {
                var e = myCell.effect as StaticWall
                var tr = game.cellTextures[e.tileName]
                var tr2: TextureRegion? = null
                if (e.tileName2 != null) {
                    tr2 = game.cellTextures[e.tileName2!!]
                }

                if (tr2 == null) {
                    var dx = (GEM_SIZE - (tr?.regionWidth ?: 0)) / 2
                    var dy = (GEM_SIZE - (tr?.regionHeight ?: 0)) / 2
                    batch!!.draw(tr, getX() + dx, getY() + dy)
                } else {
                    var dx = (GEM_SIZE - (tr?.regionWidth ?: 0)) / 2
                    batch!!.draw(tr, getX() + dx, getY())
                    batch!!.draw(tr2, getX() + dx, getY() + (tr?.regionHeight ?: 0))
                }
            }
            is TransparentWall -> {}
            else -> {
                // эффект отсутствует
                var tr: TextureRegion? = null
                if (myCell.gem != null) {
                    tr = game.cellTextures[myCell.gem!!.name]
                } else {
                    //tr = cellTextures["gem0"]
                }
                if (tr != null) {
                    var s = stage as GameStage
                    if (s.eventList.find { it is FallEvent && it.dest.first == x && it.dest.second == y} == null) {
                        batch!!.draw(tr, getX(), getY())
                    }
                }
            }
        }
    }

    override fun toString(): String {
        return "CellActor ($x, $y)"
    }
}