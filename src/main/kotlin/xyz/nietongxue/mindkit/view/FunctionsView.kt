package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import tornadofx.View
import tornadofx.combobox
import xyz.nietongxue.mindkit.application.AppDescriptor
import xyz.nietongxue.mindkit.model.Function

class FunctionsView : View() {
    override val root = VBox()
//    val processors: Processors = Processors()
    val mainController:MainController by inject()

    val appView = VBox()
    val functionToApp:Map<Function,AppDescriptor> =
        AppDescriptor.all.flatMap { appD: AppDescriptor ->
            appD.providedFunctions.map{
                it to appD
            }
        }.toMap()
    val selectedProcessorP = SimpleObjectProperty<Function>(functionToApp.toList()[0].first)


    init {
        with(root) {
            combobox(selectedProcessorP, functionToApp.toList().map { it.first }) {
                isEditable = false
                this.onAction = EventHandler<ActionEvent> {
                   with(this.value){
                       appView.children.clear()
                       val app = this@FunctionsView.functionToApp[this]!!
                       appView.add(app.controller.view)
                       app.controller.function = this
                       mainController.processorController = app.controller
                   }
                }
                this.converter = object : StringConverter<Function>() {
                    override fun toString(`object`: Function?): String {
                        return `object`?.brief!!
                    }

                    override fun fromString(string: String?): Function {
                        throw IllegalStateException("do not run to here")
                    }

                }

            }

             this .add(appView)
        }
    }
}