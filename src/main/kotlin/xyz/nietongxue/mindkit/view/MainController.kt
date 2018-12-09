package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import xyz.nietongxue.mindkit.model.Function
import xyz.nietongxue.mindkit.model.Node

class MainController : Controller() {
    val selectedNodeP = SimpleObjectProperty<Node?>(null)
    var selectedNode by selectedNodeP
    val processorControllerP = SimpleObjectProperty<xyz.nietongxue.mindkit.application.Controller>()
    var processorController: xyz.nietongxue.mindkit.application.Controller by processorControllerP
    val functionP = SimpleObjectProperty<Function>()
    var function by functionP

    init {
        selectedNodeP.onChange {
            selectedNode?.also { processorController?.process(it) }
        }
        processorControllerP.onChange {
            selectedNode?.also { processorController?.process(it) }
        }
        functionP.onChange {
            //TODO 这里有点奇怪，依赖于外部 controller.function = function
            selectedNode?.also { processorController?.process(it) }
        }

    }


    val treeModel = TreeModel()

}

