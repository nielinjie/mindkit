package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.View
import tornadofx.vboxConstraints
import xyz.nietongxue.mindkit.application.Function
import xyz.nietongxue.mindkit.properties.PropertiesApp

class FunctionsView : View() {
    override val root = VBox()
    val mainController: MainController by inject()

    val appView = VBox()
    //TODO 目前写死了properties。
    val selectedProcessorP = SimpleObjectProperty<Function>(PropertiesApp.providedFunctions.first())


    init {
        with(root) {
            vboxConstraints {
                this.vGrow = Priority.ALWAYS
            }


            selectedProcessorP.value.apply {
                appView.children.clear()
                val app = PropertiesApp
                appView.add(app.appController.view)
                app.appController.function = this
                mainController.processorAppController = app.appController
            }
//
//            }
            with(appView) {

                vboxConstraints {
                    this.vGrow = Priority.ALWAYS
                }
            }
            this.add(appView)
        }


    }

}