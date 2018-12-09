package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import xyz.nietongxue.mindkit.model.Function
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.Source

class MainController : Controller() {
    val selectedNodeP = SimpleObjectProperty<Node?>(null)
    var selectedNode by selectedNodeP
    val processorControllerP = SimpleObjectProperty<xyz.nietongxue.mindkit.application.Controller>()
    var processorController: xyz.nietongxue.mindkit.application.Controller by processorControllerP
    val functionP = SimpleObjectProperty<Function>()
    var function by functionP


    val treeModel = TreeModel()


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
        
        runAsync {
            Source.append(treeModel.root.node)
        } ui {
            treeModel.root.addChildren(it)
        }
    }


}

