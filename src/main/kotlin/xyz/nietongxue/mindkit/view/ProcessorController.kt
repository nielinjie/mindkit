package xyz.nietongxue.mindkit.view

import javafx.beans.property.SimpleStringProperty
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


    var processor: Processor = Processor.nonProcessor


    init {
        val templateString = MainController::class.java.getResource("/marpSlide.twig").readText()

        processor = TemplateProcessor(templateString)
        processorString = templateString
    }


    fun process(node: Node) {
        resultString = "running..."
        resultString = this.processor!!.process(node)

    }
}