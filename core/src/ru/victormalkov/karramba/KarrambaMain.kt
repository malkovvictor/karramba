package ru.victormalkov.karramba

import com.badlogic.gdx.ApplicationAdapter
import ru.victormalkov.karramba.model.Board

class KarrambaMain : ApplicationAdapter() {
    private lateinit var renderer: BoardRenderer
    private lateinit var controller: GameController

    override fun create() {
        controller = GameController()
        renderer = BoardRenderer(controller)

        controller.create()
        renderer.create()
    }

    override fun render() {
        renderer.render()
    }

    override fun dispose() {
        renderer.dispose()
    }

    override fun resize(width: Int, height: Int) {
        renderer.resize(width, height)
    }
}
