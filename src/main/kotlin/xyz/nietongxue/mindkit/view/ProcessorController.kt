package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import tornadofx.Controller
import tornadofx.getValue
import tornadofx.setValue
import xyz.nietongxue.mindkit.model.Node
import xyz.nietongxue.mindkit.model.Processor
import xyz.nietongxue.mindkit.model.TemplateProcessor

class ProcessorController : Controller() {
    val resultStringProperty = SimpleStringProperty()
    var resultString by resultStringProperty


    val processorStringProperty = SimpleStringProperty()
    var processorString by processorStringProperty

    val processorP = SimpleObjectProperty<Processor>()
    var processor: Processor by processorP

    init {
        this.processor = Processor.nonProcessor

    }




    fun process(node: Node) {
        resultString = "running..."
        resultString = this.processor?.process(node)

    }
}