package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.util.StringConverter
import tornadofx.View
import tornadofx.combobox
import tornadofx.vboxConstraints
import xyz.nietongxue.mindkit.application.AppDescriptor
import xyz.nietongxue.mindkit.application.properties.StatisticsApp
import xyz.nietongxue.mindkit.model.Function

class FunctionsView : View() {
    override val root = VBox()
    //    val processors: Processors = Processors()
    val mainController: MainController by inject()

    val appView = VBox()
    val functionToApp: Map<Function, AppDescriptor> =
            AppDescriptor.all.flatMap { appD: AppDescriptor ->
                appD.providedFunctions.map {
                    it to appD
                }
            }.toMap()
    val selectedProcessorP = SimpleObjectProperty<Function>(functionToApp.toList()[0].first)


    init {
        with(root) {
            vboxConstraints {
                this.vGrow = Priority.ALWAYS
            }
            combobox(selectedProcessorP, functionToApp.toList().map { it.first }) {
                isEditable = false
                this.onAction = EventHandler<ActionEvent> {
                    with(this.value) {
                        val app = setupView(this)
                        app.controller.function = this
                        mainController.processorController = app.controller
                        mainController.function = this
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
                with(this.value) {
                    val app = setupView(this)
                    app.controller.function = this
                    mainController.processorController = app.controller
                    mainController.function = this
                }

            }
            with(appView){

                    vboxConstraints {
                        this.vGrow = Priority.ALWAYS
                    }
            }
            this.add(appView)
        }



    }

    private fun setupView(function: Function): AppDescriptor {
        appView.children.clear()
        val app = this.functionToApp[function]!!
        appView.add(app.controller.view)
        //TODO 这个不是setupView的职责
        return app
    }
}