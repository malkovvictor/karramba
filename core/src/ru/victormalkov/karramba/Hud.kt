package ru.victormalkov.karramba

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import javax.swing.GroupLayout

class Hud(val game: MyGame) {
    val stage: Stage
    val viewport: Viewport
    var background: TiledDrawable
    var scoreLabel: Label
    var levelLabel: Label
    var movesLabel: Label


    init {
        viewport = ScreenViewport()
        stage = Stage(viewport, game.batch)

        val table = Table()
        table.setFillParent(true)
        table.top().left()


        //val hg = HorizontalGroup()
        //hg.debug = true
        //hg.top()

        val labelStyle = Label.LabelStyle()
        labelStyle.font = game.assetManager.get("font1.ttf", BitmapFont::class.java)


        levelLabel = Label(game.levelName, labelStyle)
        table.add(levelLabel)

        scoreLabel = Label("0", labelStyle)
        scoreLabel.setAlignment(Align.center)
        table.add(scoreLabel).expandX()

        movesLabel = Label(game.movesCount.toString(), labelStyle)
        table.add(movesLabel)

        stage.addActor(table)
        //stage.addActor(scoreLabel)



        background = TiledDrawable(game.cellTextures[TILE_BACKGROUND])
       // table.debug = true
    }

    fun dispose() {
        stage.dispose()
    }

    fun draw() {
        stage.batch.begin()
        background.draw(stage.batch, 0f, 0f, viewport.worldWidth, viewport.worldHeight)
        stage.batch.end()
        scoreLabel.setText("${game.currentScore} ☆")
        movesLabel.setText("Ходов: ${game.movesCount}")
        stage.draw()
    }
}