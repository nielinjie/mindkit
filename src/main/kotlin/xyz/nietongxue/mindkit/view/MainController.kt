package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import tornadofx.Controller
import tornadofx.getValue
import tornadofx.onChange
import tornadofx.setValue
import xyz.nietongxue.mindkit.application.AppController
import xyz.nietongxue.mindkit.application.xmind.Node

class MainController : Controller() {
    val selectedNodeP = SimpleObjectProperty<Node?>(null)
    var selectedNode by selectedNodeP
    val processorControllerP = SimpleObjectProperty<xyz.nietongxue.mindkit.application.AppController>()
    var processorAppController: AppController by processorControllerP


    init {
        selectedNodeP.onChange {
            selectedNode?.also { processorAppController?.process(it) }
        }
    }


}

