package xyz.nietongxue.mindkit.view

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.layout.VBox
import org.controlsfx.control.action.ActionMap.action
import tornadofx.View
import tornadofx.button
import tornadofx.combobox
import xyz.nietongxue.mindkit.model.Processors

class ProcessorsView : View() {
    override val root = VBox()
    val processors: Processors = Processors()
    val processorController: ProcessorController by inject()

    init {
        with(root) {
            combobox(null, processors.all) {
                isEditable = false
                this.onAction = EventHandler<ActionEvent> {
                    processorController.processor = this@combobox.value
                }
            }
        }
    }
}