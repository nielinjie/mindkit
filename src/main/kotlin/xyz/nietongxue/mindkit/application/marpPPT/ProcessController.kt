package xyz.nietongxue.mindkit.application.marpPPT

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty

import tornadofx.Controller
import tornadofx.getValue
import tornadofx.setValue
import xyz.nietongxue.mindkit.model.XNode
import xyz.nietongxue.mindkit.model.Function
import xyz.nietongxue.mindkit.model.Node

class ProcessController : Controller() {
    val resultStringProperty = SimpleStringProperty()
    var resultString: String by resultStringProperty


    val processorP = SimpleObjectProperty<Function>()
    var function: Function? by processorP

    fun process(node: Node) {
        resultString = "running..."
        runAsync {
            this@ProcessController.function?.process(node)
        } ui {
            resultString = it!!
        }

    }
}