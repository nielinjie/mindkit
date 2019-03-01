package xyz.nietongxue.mindkit.view

import javafx.animation.PauseTransition
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.util.Duration
import tornadofx.View
import tornadofx.hgrow
import tornadofx.onChange
import xyz.nietongxue.mindkit.util.defaultPadding

class SearchView : View(){
    override val root = HBox()

    private val filterField = TextField()
    private val searchActionDebounce = setupSearchingTextEvent()
    var onChange:((String)->Unit)? = null

    init{
        root.defaultPadding()
        root.add(filterField)
        filterField.hgrow = Priority.ALWAYS
        filterField.textProperty().onChange {
            searchActionDebounce.playFromStart()
        }
    }


    private fun setupSearchingTextEvent(): PauseTransition {
        val searchActionDebounce = PauseTransition(Duration.seconds(1.0))
        searchActionDebounce.setOnFinished {
            val filterS = filterField.textProperty().value
           this.onChange?.invoke(filterS)
        }
        return searchActionDebounce
    }
}