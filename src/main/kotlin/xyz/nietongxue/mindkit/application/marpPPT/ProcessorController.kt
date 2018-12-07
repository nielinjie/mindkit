package xyz.nietongxue.mindkit.application.marpPPT

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty

import tornadofx.Controller
import tornadofx.getValue
import tornadofx.setValue
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.Processor

class ProcessorController : Controller() {
    val resultStringProperty = SimpleStringProperty()
    var resultString: String by resultStringProperty


    val processorP = SimpleObjectProperty<Processor>()
    var processor: Processor? by processorP






    fun process(node: Node) {
        resultString = "running..."
        resultString = this.processor?.process(node) !!

    }
}