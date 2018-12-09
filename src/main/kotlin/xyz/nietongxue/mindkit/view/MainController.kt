package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import xyz.nietongxue.mindkit.model.Node

class MainController : Controller() {
    val selectedNodeP = SimpleObjectProperty<Node?>(null)
    var selectedNode by selectedNodeP
    var processorControllerP = SimpleObjectProperty<xyz.nietongxue.mindkit.application.Controller>()
    var processorController: xyz.nietongxue.mindkit.application.Controller by processorControllerP


    init {
        //TODO 到后台去运行，不能单用runAsync，因为他引起的ui的变化不在ui thread，需要在ui block
        selectedNodeP.onChange {
            selectedNode?.also { processorController?.process(it) } }
        processorControllerP.onChange {
            selectedNode?.also { processorController?.process(it) } }
    }


    val treeModel = TreeModel()

}

