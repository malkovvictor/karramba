package ru.victormalkov.karramba

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

class Hud(batch: Batch) {
    val stage: Stage
    val viewport: Viewport

    init {
        viewport = FitViewport(1280f, 800f)
        stage = Stage(viewport, batch)

        val table = Table()
        table.setFillParent(true)
        //stage.addActor(table)




        table.debug = true
    }




    fun dispose() {
        stage.dispose()
    }
}