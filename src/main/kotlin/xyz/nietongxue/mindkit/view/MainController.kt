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
        selectedNodeP.onChange {
            selectedNode?.also { processorController?.process(it) } }
        processorControllerP.onChange {
            selectedNode?.also { processorController?.process(it) } }
    }
//
//    fun process(node: Node) {
//        this.processorController?.process(node)
//    }

    val treeModel = TreeModel()

}

