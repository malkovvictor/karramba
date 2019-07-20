package ru.victormalkov.karramba

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport
import ru.victormalkov.karramba.boardEvents.BoardEvent
import ru.victormalkov.karramba.boardEvents.FallEvent
import ru.victormalkov.karramba.boardEvents.RemoveEvent
import java.util.*

const val EPSILON = 0.0001

class GameStage(viewport: Viewport, val game: MyGame) : Stage(viewport) {
    private val TAG = "GameStage"
    var boardEvents = Group()
    var eventList = LinkedList<BoardEvent>()

    override fun act(delta: Float) {
        super.act(delta)

        when (game.phase) {
            Phase.REMOVE_MATCHES -> {
                Gdx.app.debug(TAG, game.phase.toString())
                var removeList = game.board!!.removeMatches()
                if (removeList.isEmpty()) {
                    game.phase = Phase.USER_WAIT
                } else {
                    for (tr in removeList) {
                        var re = RemoveEvent(game, tr.first, tr.second, tr.third)
                        re.setSize(GEM_SIZE, GEM_SIZE)
                        re.setPosition(re.xx * GEM_SIZE, re.yy * GEM_SIZE)
                        addEvent(re)
                    }
                    game.phase = Phase.FALL
                }
            }

            Phase.FALL -> {
//                Gdx.app.debug(TAG, game.phase.toString())
                if (eventList.filter{it is RemoveEvent || it is FallEvent}.isEmpty()) {
                    var fallen = game.board!!.fall()
                    if (fallen == null) {
                        game.phase = Phase.PRODUCE
                    } else {
                        var fe = FallEvent(game, fallen.first, fallen.second, fallen.third)
                        addEvent(fe)
                        Gdx.app.debug(TAG, "new fall event")
                    }
                }
            }

            Phase.PRODUCE -> {
                if (eventList.isEmpty()) {
                    Gdx.app.debug(TAG, game.phase.toString())
                    if (game.board!!.produce()) {
                        game.phase = Phase.FALL
                    } else {
                        game.phase = Phase.REMOVE_MATCHES
                    }
                }
            }
        }

        val iterator = eventList.iterator()
        for (i in iterator) {
            if (i.myTime >= i.eventTime - EPSILON) {
                Gdx.app.debug(TAG, "remove event: ${i.myTime}")
                boardEvents.removeActor(i)
                iterator.remove()
            }
        }

    }

    private fun addEvent(event: BoardEvent) {
        boardEvents.addActor(event)
        eventList.add(event)
    }
}