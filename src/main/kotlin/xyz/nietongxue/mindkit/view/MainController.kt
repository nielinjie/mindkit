package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import tornadofx.Controller
import tornadofx.getValue
import tornadofx.onChange
import tornadofx.setValue
import xyz.nietongxue.mindkit.model.Function
import xyz.nietongxue.mindkit.model.Node

class MainController : Controller() {
    val selectedNodeP = SimpleObjectProperty<Node?>(null)
    var selectedNode by selectedNodeP
    val processorControllerP = SimpleObjectProperty<xyz.nietongxue.mindkit.application.AppController>()
    var processorAppController: xyz.nietongxue.mindkit.application.AppController by processorControllerP
    val functionP = SimpleObjectProperty<Function>()
    var function by functionP




    init {
        selectedNodeP.onChange {
            selectedNode?.also { processorAppController?.process(it) }
        }
        processorControllerP.onChange {
            selectedNode?.also { processorAppController?.process(it) }
        }
        functionP.onChange {
            //TODO 这里有点奇怪，依赖于外部 APP_CONTROLLER.function = function
            selectedNode?.also { processorAppController?.process(it) }
        }
        

    }


}

