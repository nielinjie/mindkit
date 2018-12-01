package xyz.nietongxue.mindkit.view

import tornadofx.Controller
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.application.marpPPT.ProcessorController

class MainController : Controller() {
//TODO 这里需要持有node，而不是每次传进来。以便能在node不变、process变化的时候再运行一次process
    var processorController:xyz.nietongxue.mindkit.application.Controller?=null

    fun process(node: Node) {
        this.processorController?.process(node)
    }

    val treeModel = TreeModel()

}

