package xyz.nietongxue.mindkit.view

import javafx.scene.layout.VBox
import tornadofx.View
import xyz.nietongxue.mindkit.model.properties.PropertiesApp
import xyz.nietongxue.mindkit.util.growV

class AppView : View() {
    override val root = VBox()
    private val mainController: MainController by inject()

    private val appView = VBox()
    //NOTE 目前写死了properties。


    init {
        with(root) {
            growV()
            appView.children.clear()
            val app = PropertiesApp
            appView.add(app.appController.view)
            mainController.processorAppController = app.appController
            with(appView) {
                growV()
            }
            this.add(appView)
        }


    }

}