package xyz.nietongxue.mindkit.view

import javafx.event.Event
import javafx.event.EventDispatchChain
import javafx.event.EventDispatcher
import javafx.scene.input.KeyCode.*
import javafx.scene.input.KeyEvent
import javafx.scene.input.KeyEvent.KEY_PRESSED


internal class CellEventDispatcher(private val original: EventDispatcher) : EventDispatcher {

    override fun dispatchEvent(event: Event, tail: EventDispatchChain): Event? {
//        if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED) || event.getEventType().equals(ContextMenuEvent.ANY)) {
//            event.consume()
//        }
        if (event is KeyEvent && event.eventType == KEY_PRESSED) {
            event.takeIf {
                listOf(
                        SPACE,ENTER,TAB,BACK_SPACE
                ).contains(it.code)
            }?.consume()
        }
        return original.dispatchEvent(event, tail)
    }
}
