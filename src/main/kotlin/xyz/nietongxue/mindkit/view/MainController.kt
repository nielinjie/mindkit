package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleStringProperty
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import tornadofx.Controller
import xyz.nietongxue.mindkit.model.Node
import tornadofx.getValue
import tornadofx.setValue
import xyz.nietongxue.mindkit.model.TemplateProcessor

class MainController : Controller() {
    fun process(node: Node) {
        this.processorController.process(node)
    }

    val treeModel = TreeModel()
    val processorController: ProcessorController by inject()

}

