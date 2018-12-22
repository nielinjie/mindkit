package xyz.nietongxue.mindkit.application.marpPPT

import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller
import tornadofx.getValue
import tornadofx.setValue
import xyz.nietongxue.mindkit.util.TemplateFunction
import xyz.nietongxue.mindkit.application.xmind.Node

class ProcessController : Controller() {
    val resultStringProperty = SimpleStringProperty()
    var resultString: String by resultStringProperty


    private var function: TemplateFunction = TemplateFunction(MarpPPTAction::class.java.getResource("/marpSlide.twig").readText())

    fun process(node: Node) {
        resultString = "running..."
        runAsync {
            this@ProcessController.function.process(node)
        } ui {
            resultString = it!!
        }

    }
}