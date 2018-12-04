package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import tornadofx.View
import tornadofx.combobox
import xyz.nietongxue.mindkit.application.AppDescriptor
import xyz.nietongxue.mindkit.model.Processor
import xyz.nietongxue.mindkit.model.Processors

class ProcessorsView : View() {
    override val root = VBox()
//    val processors: Processors = Processors()
    val mainController:MainController by inject()

    val appView = VBox()
    val processorToApp:Map<Processor,AppDescriptor> =
        AppDescriptor.all.flatMap { appD: AppDescriptor ->
            appD.providedProcessors.map{
                it to appD
            }
        }.toMap()
    val selectedProcessorP = SimpleObjectProperty<Processor>(processorToApp.toList()[0].first)


    init {
        with(root) {
            combobox(selectedProcessorP, processorToApp.toList().map { it.first }) {
                isEditable = false
                this.onAction = EventHandler<ActionEvent> {
                   with(this.value){
                       appView.children.clear()
                       val app = this@ProcessorsView.processorToApp[this]!!
                       appView.add(app.controller.view)
                       app.controller.processor = this
                       mainController.processorController = app.controller
                   }
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

             this .add(appView)
        }
    }
}