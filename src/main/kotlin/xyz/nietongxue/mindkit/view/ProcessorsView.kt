package xyz.nietongxue.mindkit.view

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import org.controlsfx.control.action.ActionMap.action
import tornadofx.View
import tornadofx.button
import tornadofx.combobox
import tornadofx.stringBinding
import xyz.nietongxue.mindkit.model.Processor
import xyz.nietongxue.mindkit.model.Processors

class ProcessorsView : View() {
    override val root = VBox()
    val processors: Processors = Processors()
    val processorController: ProcessorController by inject()

    init {
        with(root) {
            combobox(processorController.processorP, processors.all) {
                isEditable = false
                this.onAction = EventHandler<ActionEvent> {
                    processorController.processor = this@combobox.value
                }
                this.converter = object : StringConverter<Processor>() {
                    override fun toString(`object`: Processor?): String {
                        return `object`?.brief!!
                    }

                    override fun fromString(string: String?): Processor {
                        throw IllegalStateException("do not run to here")
                    }

                }

            }
        }
    }
}