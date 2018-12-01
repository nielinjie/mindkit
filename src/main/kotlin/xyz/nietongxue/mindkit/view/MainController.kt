package xyz.nietongxue.mindkit.view

import tornadofx.Controller
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.application.marpPPT.ProcessorController

class MainController : Controller() {

    var processorController:xyz.nietongxue.mindkit.application.Controller?=null

    fun process(node: Node) {
        this.processorController?.process(node)
    }

    val treeModel = TreeModel()

}

