package xyz.nietongxue.mindkit.view

import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.View
import tornadofx.vboxConstraints
import xyz.nietongxue.mindkit.properties.PropertiesApp

class FunctionsView : View() {
    override val root = VBox()
    val mainController: MainController by inject()

    val appView = VBox()
    //TODO 目前写死了properties。


    init {
        with(root) {
            vboxConstraints {
                this.vGrow = Priority.ALWAYS
            }


            appView.children.clear()
            val app = PropertiesApp
            appView.add(app.appController.view)
            mainController.processorAppController = app.appController
            with(appView) {

                vboxConstraints {
                    this.vGrow = Priority.ALWAYS
                }
            }
            this.add(appView)
        }


    }

}